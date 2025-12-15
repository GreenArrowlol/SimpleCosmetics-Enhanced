/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.cosmetics.listeners;

import me.refrac.cosmetics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Zachary Baldwin / Refrac
 */
public class Particlev1_13PlusListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        if (Utils.flame.contains(player.getName())) {
            player.spawnParticle(Particle.FLAME, player.getLocation(), 0);
        }
        if (Utils.heart.contains(player.getName())) {
            player.spawnParticle(Particle.HEART, player.getLocation(), 0);
        }
        if (Utils.slime.contains(player.getName())) {
            player.spawnParticle(Particle.ITEM, player.getLocation().add(0, 0.4, 0), 5, 0.2, 0.2, 0.2, new ItemStack(Material.SLIME_BALL));
        }
        if (Utils.smoke.contains(player.getName())) {
            player.spawnParticle(Particle.SMOKE, player.getLocation(), 0);
        }
        if (Utils.critical.contains(player.getName())) {
            player.spawnParticle(Particle.CRIT, player.getLocation().add(0, 0.4, 0), 5);
        }
        if (Utils.notes.contains(player.getName())) {
            player.spawnParticle(Particle.NOTE, player.getLocation().add(0, 0.4, 0), 3);
        }
        if (Utils.lava.contains(player.getName())) {
            player.spawnParticle(Particle.DRIPPING_LAVA, player.getLocation(), 5);
        }
        if (Utils.water.contains(player.getName())) {
            player.spawnParticle(Particle.DRIPPING_WATER, player.getLocation(), 5);
        }
        if (Utils.cloud.contains(player.getName())) {
            player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0.4, 0), 5, 0.2, 0.2, 0.2, 0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Utils.removeTrails(player);
    }
}