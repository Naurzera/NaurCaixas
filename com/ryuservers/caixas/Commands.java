package com.ryuservers.caixas;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("darcaixa")) {
            Player p = (Player) sender;
            if (p.hasPermission(Main.getInstance().getConfig().getString("permissao-admin"))) {
                if (args.length > 1) {
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if (p2 == null) {
                        p.sendMessage(ChatColor.RED + "Este jogador não está online.");
                        return true;
                    }
                    if (p2.isOnline()) {
                        ItemStack caixa = new ItemStack(Material.ENDER_CHEST);
                        ItemMeta meta = caixa.getItemMeta();
                        String nomecaixa = Main.getInstance().getConfig().getString("Caixas." + args[1] + ".nome");
                        nomecaixa = StringUtils.replace(nomecaixa, "&", "§");
                        meta.setDisplayName(nomecaixa);
                        ArrayList lore = (ArrayList) Main.getInstance().getConfig().getStringList("Caixas." + args[1] + ".desc");
                        if (!(lore == null)) {

                            for (int l = 0; l < lore.size(); l++) {
                                if (lore.get(l).toString().contains("&"))
                                    lore.set(l, lore.get(l).toString().replaceAll("&", "§"));
                            }
                            meta.setLore(lore);

                        }
                        caixa.setItemMeta(meta);

                        p2.getPlayer().sendMessage(ChatColor.GREEN + "Voce recebeu uma " + nomecaixa + ", tente a sorte abrindo ela!");
                        p.sendMessage(ChatColor.GREEN + "Você enviou uma caixa para " + args[0]);
                        p2.getPlayer().getInventory().addItem(caixa);

                    } else {
                        p.sendMessage(ChatColor.RED + "O jogador está offline!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Utilize /darcaixa (nick) (id)");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Desculpe, você não tem permissão para usar isso!");
            }

        }

        if (cmd.getName().equalsIgnoreCase("darcaixaall")) {
            if (!(sender instanceof Player)){return true;}
            Player p = (Player) sender;
            if (args.length > 1) {
                if (p.hasPermission(Main.getInstance().getConfig().getString("permissao-admin"))) {
                    ItemStack caixa = new ItemStack(Material.ENDER_CHEST, Integer.parseInt(args[1]));
                    ItemMeta meta = caixa.getItemMeta();
                    String nomecaixa = Main.getInstance().getConfig().getString("Caixas." + args[0] + ".nome");
                    nomecaixa = StringUtils.replace(nomecaixa, "&", "§");
                    meta.setDisplayName(nomecaixa);
                    ArrayList lore = (ArrayList) Main.getInstance().getConfig().getStringList("Caixas." + args[0] + ".desc");
                    Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
                    caixa.setItemMeta(meta);
                    for (Player player : players) {

                        player.getInventory().addItem(caixa);
                        player.sendMessage(ChatColor.GREEN + "Obaa, todos os jogadores online receberam uma " + ChatColor.YELLOW + nomecaixa);continue;
                    }
                }
            } else {
                p.sendMessage(ChatColor.RED + "Utilize /darcaixa (id) (quantidade)");
            }
        }return false;
    }
}