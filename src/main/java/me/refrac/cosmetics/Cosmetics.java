package me.refrac.cosmetics;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.refrac.cosmetics.commands.CosmeticsCommand;
import me.refrac.cosmetics.listeners.JoinListener;
import me.refrac.cosmetics.listeners.ParticleListener;
import me.refrac.cosmetics.listeners.Particlev1_13PlusListener;
import me.refrac.cosmetics.menu.TrailsMenu;
import me.refrac.cosmetics.utils.ColorUtil;
import me.refrac.cosmetics.utils.Logger;
import me.refrac.cosmetics.utils.UpdateChecker;
import me.refrac.cosmetics.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics instance;

    @Override
    public void onEnable() {
        instance = this;

        setupConfig();
        extractStaticResource("config.yml.example");
        extractStaticResource("readme.txt");

        if (getConfig().getBoolean("BStats.Enabled", true)) {
            new Metrics(this, 31908);
        }

        PluginCommand cosmeticsCmd = getCommand("cosmetics");
        cosmeticsCmd.setExecutor(new CosmeticsCommand());
        cosmeticsCmd.setTabCompleter(new CosmeticsCommand());
        applyCommandAliases(cosmeticsCmd);

        if (isLegacyVersion()) {
            Bukkit.getPluginManager().registerEvents(new ParticleListener(), this);
        } else {
            Bukkit.getPluginManager().registerEvents(new Particlev1_13PlusListener(), this);
        }
        Bukkit.getPluginManager().registerEvents(new TrailsMenu(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        printStartupBanner();

        if (getConfig().getBoolean("Update.Enabled", true)) {
            Logger.INFO.out("Checking for updates...");
            new UpdateChecker(this, 127149).getLatestVersion(version -> {
                Bukkit.getScheduler().runTask(this, () -> {
                    if (!getDescription().getVersion().equalsIgnoreCase(version)) {
                        Logger.NONE.out(ColorUtil.translate("&7&m-----------------------------------------"));
                        Logger.NONE.out(ColorUtil.translate("&bA new version of SimpleCosmetics Enhanced &7(v" + version + ") &bhas been released!"));
                        Logger.NONE.out(ColorUtil.translate("&bDownload: " + Utils.getPluginURL));
                        Logger.NONE.out(ColorUtil.translate("&7&m-----------------------------------------"));
                    } else {
                        Logger.SUCCESS.out("SimpleCosmetics Enhanced is up to date!");
                    }
                });
            });
        }
    }

    @Override
    public void onDisable() {
        printShutdownBanner();
        instance = null;
    }

    private void printStartupBanner() {
        String version = getDescription().getVersion();
        String[] lines = {
                "&8&m=================================================",
                "&b   ███████╗ ██████╗  &fSimpleCosmetics Enhanced",
                "&b   ██╔════╝██╔════╝  &7Version: &fv" + version,
                "&b   ███████╗██║       &7Author:  &fzArrowTan",
                "&b   ╚════██║██║       &7Fork of: &fSimpleCosmetics &7by &fRefrac",
                "&b   ███████║╚██████╗  &7Status:  &aEnabled",
                "&b   ╚══════╝ ╚═════╝",
                "&8&m================================================="
        };
        for (String l : lines) Logger.NONE.out(ColorUtil.translate(l));
    }

    private void printShutdownBanner() {
        String version = getDescription().getVersion();
        String[] lines = {
                "&8&m=================================================",
                "&c   ███████╗ ██████╗  &fSimpleCosmetics Enhanced",
                "&c   ██╔════╝██╔════╝  &7v" + version + " &7- shutting down",
                "&c   ███████╗██║       &7Thanks for using!",
                "&c   ╚════██║██║       &7- &fzArrowTan",
                "&c   ███████║╚██████╗",
                "&c   ╚══════╝ ╚═════╝",
                "&8&m================================================="
        };
        for (String l : lines) Logger.NONE.out(ColorUtil.translate(l));
    }

    private void setupConfig() {
        try {
            YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder()
                            .setVersioning(new BasicVersioning("configVersion"))
                            .build()
            );
        } catch (Exception e) {
            Logger.WARNING.out("Failed to auto-update config.yml: " + e.getMessage());
            saveDefaultConfig();
        }
        reloadConfig();
    }

    private void extractStaticResource(String name) {
        if (!new File(getDataFolder(), name).exists()) {
            saveResource(name, false);
        }
    }

    private void applyCommandAliases(PluginCommand cmd) {
        if (cmd == null) return;
        List<String> aliases = getConfig().getStringList("CommandAliases");
        if (aliases == null || aliases.isEmpty()) return;
        cmd.setAliases(aliases);
        try {
            Field f = Bukkit.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap map = (CommandMap) f.get(Bukkit.getServer().getPluginManager());
            map.register(getDescription().getName().toLowerCase(), cmd);
        } catch (Exception e) {
            Logger.WARNING.out("Could not register command aliases dynamically: " + e.getMessage());
        }
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
