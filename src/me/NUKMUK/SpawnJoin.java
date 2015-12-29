package me.NUKMUK;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by rasmu on 22.10.2015.
 */
public class SpawnJoin extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Location l = Bukkit.getWorld(getConfig().getString("world")).getSpawnLocation().add(0.5,0,0.5);
        l.setPitch(0);
        l.setYaw(getConfig().getInt("yaw"));
        p.teleport(l);
    }
}
