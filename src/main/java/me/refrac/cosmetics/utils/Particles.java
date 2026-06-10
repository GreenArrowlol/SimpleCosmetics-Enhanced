package me.refrac.cosmetics.utils;

import org.bukkit.Particle;

/**
 * Version-resilient particle lookups.
 *
 * Several particle enum constants were renamed in 1.20.5 (SMOKE_NORMAL -> SMOKE,
 * DRIP_LAVA -> DRIPPING_LAVA, DRIP_WATER -> DRIPPING_WATER, ITEM_CRACK -> ITEM).
 * The plugin is compiled against spigot-api 1.20.4 where the old names exist,
 * but should also run on 1.20.5+ where only the new names exist. Direct
 * references to renamed enum constants would trip NoSuchFieldError at class
 * load. Resolving by name via Particle.valueOf at static init avoids that.
 */
public final class Particles {

    public static final Particle FLAME = first("FLAME");
    public static final Particle HEART = first("HEART");
    public static final Particle CRIT = first("CRIT");
    public static final Particle NOTE = first("NOTE");
    public static final Particle CLOUD = first("CLOUD");
    public static final Particle SMOKE_NORMAL = first("SMOKE_NORMAL", "SMOKE");
    public static final Particle DRIP_LAVA = first("DRIP_LAVA", "DRIPPING_LAVA");
    public static final Particle DRIP_WATER = first("DRIP_WATER", "DRIPPING_WATER");
    public static final Particle ITEM_CRACK = first("ITEM_CRACK", "ITEM");

    private Particles() {}

    private static Particle first(String... candidates) {
        for (String name : candidates) {
            try {
                return Particle.valueOf(name);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return null;
    }
}
