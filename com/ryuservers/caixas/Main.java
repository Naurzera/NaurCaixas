package com.ryuservers.caixas;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){

        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"NaurCaixas Habilitado");
        getServer().getConsoleSender().sendMessage("");

        getServer().getPluginManager().registerEvents(new Listener(), this);
        getCommand("darcaixa").setExecutor(new Commands());
        getCommand("darcaixaall").setExecutor(new Commands());
        instance = this;
        saveDefaultConfig();
    }

    private static Main instance;
    public static Main getInstance(){return instance;}

}
