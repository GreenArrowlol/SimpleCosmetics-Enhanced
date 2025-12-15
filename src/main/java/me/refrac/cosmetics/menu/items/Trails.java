/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.cosmetics.menu.items;

import me.refrac.cosmetics.Cosmetics;
import me.refrac.cosmetics.utils.ItemBuilder;
import me.refrac.cosmetics.utils.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Zachary Baldwin / Refrac
 */
public class Trails {

    private static Material getMaterial(String path, Material defaultMaterial) {
        String materialString = Cosmetics.getInstance().getConfig().getString(path);
        Material material = Material.getMaterial(materialString.toUpperCase());
        if (material == null) {
            Logger.WARNING.out("Invalid material '" + materialString + "' found at '" + path + "'. Defaulting to " + defaultMaterial.name() + ".");
            return defaultMaterial;
        }
        return material;
    }

    public static ItemStack removeTrailsItem() {
        return new ItemBuilder(getMaterial("TrailsMenu.REMOVE-TRAIL.MATERIAL", Material.BARRIER))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.REMOVE-TRAIL.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.REMOVE-TRAIL.LORE"))
                .toItemStack();
    }

    public static ItemStack flameTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.FLAME.MATERIAL", Material.BLAZE_POWDER))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.FLAME.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.FLAME.LORE"))
                .toItemStack();
    }

    public static ItemStack heartTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.HEART.MATERIAL", Material.REDSTONE))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.HEART.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.HEART.LORE"))
                .toItemStack();
    }

    public static ItemStack slimeTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.SLIME.MATERIAL", Material.SLIME_BALL))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.SLIME.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.SLIME.LORE"))
                .toItemStack();
    }

    public static ItemStack smokeTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.SMOKE.MATERIAL", Material.STRING))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.SMOKE.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.SMOKE.LORE"))
                .toItemStack();
    }

    public static ItemStack criticalTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.CRITICAL.MATERIAL", Material.FLINT))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.CRITICAL.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.CRITICAL.LORE"))
                .toItemStack();
    }

    public static ItemStack notesTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.NOTES.MATERIAL", Material.NOTE_BLOCK))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.NOTES.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.NOTES.LORE"))
                .toItemStack();
    }

    public static ItemStack lavaTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.LAVA.MATERIAL", Material.LAVA_BUCKET))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.LAVA.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.LAVA.LORE"))
                .toItemStack();
    }
    public static ItemStack waterTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.WATER.MATERIAL", Material.WATER_BUCKET))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.WATER.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.WATER.LORE"))
                .toItemStack();
    }
    public static ItemStack cloudTrail() {
        return new ItemBuilder(getMaterial("TrailsMenu.CLOUD.MATERIAL", Material.BEACON))
                .setName(Cosmetics.getInstance().getConfig().getString("TrailsMenu.CLOUD.NAME"))
                .setLore(Cosmetics.getInstance().getConfig().getStringList("TrailsMenu.CLOUD.LORE"))
                .toItemStack();
    }
}