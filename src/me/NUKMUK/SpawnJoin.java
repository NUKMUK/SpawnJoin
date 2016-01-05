package me.NUKMUK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnJoin extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("sj")) {

            Player p = (Player) sender;

            if (args.length == 0) {
                if (p.hasPermission("spawnjoin.help")) {

                    p.sendMessage(ChatColor.YELLOW + "---------- " + ChatColor.GOLD + "SpawnJoin" + ChatColor.YELLOW + " ----------");
                    p.sendMessage(ChatColor.GOLD + "/sj set <x> <y> <z> [yaw] [world]");
                    p.sendMessage(ChatColor.GOLD + "/sj get");
                    p.sendMessage(ChatColor.GOLD + "/sj spawn");
                    p.sendMessage(ChatColor.GOLD + "/sj reload");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (p.hasPermission("spawnjoin.set")) {

                    // < 4
                    if (args.length < 4) {
                        p.sendMessage(ChatColor.RED + "Usage: /sj set <x> <y> <z> [yaw]");
                    }

                    //normal
                    if (args.length == 4) {
                        try {
                            int arg1 = Integer.parseInt(args[1]);
                            int arg2 = Integer.parseInt(args[2]);
                            int arg3 = Integer.parseInt(args[3]);
                            p.getWorld().setSpawnLocation(arg1, arg2, arg3);
                            getConfig().set("world", p.getWorld().getName());
                            saveConfig();
                            p.sendMessage(ChatColor.GREEN + "Spawn set to: " + ChatColor.DARK_GREEN + arg1 + " " + arg2 + " " + arg3);
                        } catch (Exception ex) {
                            p.sendMessage(ChatColor.DARK_RED + ex.toString());
                            p.sendMessage(ChatColor.RED + "Usage: /sj set <x> <y> <z> [yaw]");
                        }
                    }

                    //yaw
                    if (args.length == 5) {
                        try {
                            int arg1 = Integer.parseInt(args[1]);
                            int arg2 = Integer.parseInt(args[2]);
                            int arg3 = Integer.parseInt(args[3]);
                            p.getWorld().setSpawnLocation(arg1, arg2, arg3);
                            getConfig().set("world", p.getWorld().getName());
                            saveConfig();
                            getConfig().set("yaw", Integer.parseInt(args[4]));
                            saveConfig();
                            p.sendMessage(ChatColor.GREEN + "Spawn set to: " + ChatColor.DARK_GREEN + arg1 + " " + arg2 + " " + arg3 + " " + Integer.parseInt(args[4]));
                        } catch (Exception ex) {
                            p.sendMessage(ChatColor.DARK_RED + ex.toString());
                            p.sendMessage(ChatColor.RED + "Usage: /sj set <x> <y> <z> [yaw]");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            } else if (args[0].equalsIgnoreCase("get")) {
                if (p.hasPermission("spawnjoin.get")) {

                    p.sendMessage(ChatColor.DARK_GREEN + "" +
                            Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation().getX() + " " +
                            Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation().getY() + " " +
                            Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation().getZ() + " " +
                            getConfig().getInt("yaw") + " " +
                            getConfig().getString("world"));
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (p.hasPermission("spawnjoin.spawn")) {
                    Location l = Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation();
                    l.setYaw(getConfig().getInt("yaw"));
                    p.teleport(l);
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("spawnjoin.reload")) {
                    reloadConfig();
                    p.sendMessage(ChatColor.GREEN + "Config reloaded");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            } else {
                if (p.hasPermission("spawnjoin.help")) {
                    p.sendMessage(ChatColor.YELLOW + "---------- " + ChatColor.GOLD + "SpawnJoin" + ChatColor.YELLOW + " ----------");
                    p.sendMessage(ChatColor.RED + "/sj set <x> <y> <z> [yaw] [world]");
                    p.sendMessage(ChatColor.RED + "/sj get");
                    p.sendMessage(ChatColor.RED + "/sj spawn");
                    p.sendMessage(ChatColor.RED + "/sj reload");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
                Location l = Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation().add(0.5, 0, 0.5);
            l.setPitch(0);
            try {
                l.setYaw(getConfig().getInt("yaw"));
            } catch(Exception ex){
                p.sendMessage(ChatColor.RED + "Yaw: " + ChatColor.DARK_RED + ex.toString());
                getConfig().set("yaw", 0);
                saveConfig();
            }
            p.teleport(l);
        } catch (Exception ex) {
            p.sendMessage(ChatColor.RED + "World: " + ChatColor.DARK_RED + ex.toString());
            getConfig().set("world", p.getWorld().getName());
            saveConfig();
        }
    }
}