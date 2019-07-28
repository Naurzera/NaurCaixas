package com.ryuservers.caixas;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if (p.getItemInHand().toString().contains("ENDER_CHEST") && p.getItemInHand().hasItemMeta()) {
                int novo = 0;
                do {
                    novo++;

                    String nomecaixa = Main.getInstance().getConfig().getString("Caixas."+novo+".nome");
                    nomecaixa= StringUtils.replace(nomecaixa, "&", "§");
                    if (p.getItemInHand().getItemMeta().getDisplayName().equals(nomecaixa))
                    {
                        e.setCancelled(true);
                        {
                            int v = -1;
                            do {
                                v++;
                                if (p.getInventory().getItem(v) == null || p.getInventory().getItem(v).equals(Material.AIR)) {
                                    break;
                                }
                                if (v == 35) {
                                    p.sendMessage(ChatColor.RED + "Seu inventário está cheio!");
                                    return;
                                }
                            } while (v <= 34);
                        }

                        ItemStack clone = new ItemStack(p.getItemInHand());
                        clone.setAmount(1);
                        p.getInventory().removeItem(clone);

                        List<String> list = Main.getInstance().getConfig().getStringList("Caixas."+novo+".comandos");
                        Random random = new Random();
                        int value = random.nextInt(list.size());
                        String str = list.get(value);
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), str.replaceAll("&","§").replaceAll("%player%", p.getName()));

                        String mensagem = Main.getInstance().getConfig().getString("Caixas."+novo+".anuncio");

                        if (mensagem.equals("false") || mensagem.isEmpty())return;

                        mensagem = StringUtils.replace(mensagem, "&", "§");
                        mensagem = StringUtils.replace(mensagem, "%player%", p.getName());
                        mensagem = StringUtils.replace(mensagem, "%caixa%", nomecaixa);
                        Bukkit.getServer().broadcastMessage(mensagem);

                        break;
                    }
                } while (novo <= Main.getInstance().getConfig().getIntegerList("Caixas").size()-1);
            }
        }
    }
}