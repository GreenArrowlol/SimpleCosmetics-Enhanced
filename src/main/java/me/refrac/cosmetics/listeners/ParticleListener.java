/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.cosmetics.listeners;

import me.refrac.cosmetics.utils.Utils;
import org.bukkit.Material;
import me.refrac.cosmetics.utils.Particles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Zachary Baldwin / Refrac
 */
public class ParticleListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        if (Utils.flame.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.FLAME, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.heart.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.HEART, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.slime.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.ITEM_CRACK, player.getLocation(), 1, 0, 0.1, 0, 0, new ItemStack(Material.SLIME_BALL));
        }
        if (Utils.smoke.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.SMOKE_NORMAL, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.critical.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.CRIT, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.notes.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.NOTE, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.lava.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.DRIP_LAVA, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.water.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.DRIP_WATER, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
        if (Utils.cloud.contains(player.getName())) {
            player.getWorld().spawnParticle(Particles.CLOUD, player.getLocation(), 1, 0, 0.1, 0, 0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Utils.removeTrails(player);
    }
}