package me.refrac.cosmetics;

import me.refrac.cosmetics.commands.CosmeticsCommand;
import me.refrac.cosmetics.commands.CosmeticsReloadCommand;
import me.refrac.cosmetics.listeners.JoinListener;
import me.refrac.cosmetics.listeners.ParticleListener;
import me.refrac.cosmetics.listeners.Particlev1_13PlusListener;
import me.refrac.cosmetics.menu.TrailsMenu;
import me.refrac.cosmetics.utils.ColorUtil;
import me.refrac.cosmetics.utils.Logger;
import me.refrac.cosmetics.utils.UpdateChecker;
import me.refrac.cosmetics.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics instance;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        getCommand("cosmetics").setExecutor(new CosmeticsCommand());
        getCommand("cosmeticsreload").setExecutor(new CosmeticsReloadCommand());
        if (isLegacyVersion()) {
            Bukkit.getPluginManager().registerEvents(new ParticleListener(), this);
        } else {
            Bukkit.getPluginManager().registerEvents(new Particlev1_13PlusListener(), this);
        }
        Bukkit.getPluginManager().registerEvents(new TrailsMenu(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Logger.NONE.out(ColorUtil.translate("&8&m==&c&m=====&f&m======================&c&m=====&8&m=="));
        Logger.NONE.out(ColorUtil.translate("&e" + Utils.getName + " has been enabled."));
        Logger.NONE.out(ColorUtil.translate(" &f[*] &6Version&f: &b" + Utils.getVersion));
        Logger.NONE.out(ColorUtil.translate(" &f[*] &6Name&f: &b" + Utils.getName));
        Logger.NONE.out(ColorUtil.translate(" &f[*] &6Author&f: &b" + Utils.getDeveloper));
        Logger.NONE.out(ColorUtil.translate("&8&m==&c&m=====&f&m======================&c&m=====&8&m=="));
        Logger.INFO.out("Checking for updates...");
        new UpdateChecker(Cosmetics.instance, 127149).getLatestVersion(version -> {
            Bukkit.getScheduler().runTask(this, () -> {
                if (!Cosmetics.instance.getDescription().getVersion().equalsIgnoreCase(version)) {
                    Logger.NONE.out(ColorUtil.translate("&7&m-----------------------------------------"));
                    Logger.NONE.out(ColorUtil.translate("&bA new version of SimpleCosmetics&7(SimpleCosmetics " + version + ") &bhas been released!"));
                    Logger.NONE.out(ColorUtil.translate("&bPlease update here: " + Utils.getPluginURL));
                    Logger.NONE.out(ColorUtil.translate("&7&m-----------------------------------------"));
                } else {
                    Logger.SUCCESS.out("SimpleCosmetics is up to date!");
                }
            });
        });

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private boolean isLegacyVersion() {
        String version = Bukkit.getBukkitVersion().split("-")[0];
        switch (version) {
            case "1.8":
            case "1.8.3":
            case "1.8.4":
            case "1.8.5":
            case "1.8.6":
            case "1.8.7":
            case "1.8.8":
            case "1.8.9":
            case "1.9":
            case "1.9.2":
            case "1.9.4":
            case "1.10":
            case "1.10.2":
            case "1.11":
            case "1.11.2":
            case "1.12":
            case "1.12.1":
            case "1.12.2":
                return true;
            default:
                return false;
        }
    }

    public static Cosmetics getInstance() {
        return instance;
    }
}