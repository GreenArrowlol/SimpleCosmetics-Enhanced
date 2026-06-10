/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.cosmetics.commands;

import me.refrac.cosmetics.Cosmetics;
import me.refrac.cosmetics.menu.TrailsMenu;
import me.refrac.cosmetics.utils.UpdateChecker;
import me.refrac.cosmetics.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Zachary Baldwin / Refrac
 */
public class CosmeticsCommand implements CommandExecutor, TabCompleter {

    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "menu", "clear", "reload", "update", "version", "help"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String sub = args.length == 0 ? "menu" : args[0].toLowerCase();

        switch (sub) {
            case "menu":
                return openMenu(sender);
            case "clear":
                return clearTrails(sender);
            case "reload":
                return reload(sender);
            case "update":
                return checkUpdate(sender);
            case "version":
            case "ver":
                return showVersion(sender);
            case "help":
            case "?":
                return showHelp(sender, label);
            case "dev":
                return devInfo(sender);
            default:
                sender.sendMessage(Utils.getColor("&cUnknown subcommand. Try &f/" + label + " help&c."));
                return false;
        }
    }

    private boolean openMenu(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.getColor("&cYou must be a player to open the menu."));
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("simplecosmetics.use")) {
            player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            return false;
        }
        TrailsMenu.openMenu(player);
        return true;
    }

    private boolean clearTrails(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.getColor("&cOnly a player can clear their own trail."));
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("simplecosmetics.clear")) {
            player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            return false;
        }
        Utils.removeTrails(player);
        player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-ALL-TRAILS")));
        return true;
    }

    private boolean reload(CommandSender sender) {
        if (!sender.hasPermission("simplecosmetics.admin")) {
            sender.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            return false;
        }
        Cosmetics.getInstance().reloadConfig();
        sender.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.RELOAD")));
        return true;
    }

    private boolean checkUpdate(CommandSender sender) {
        if (!sender.hasPermission("simplecosmetics.admin")) {
            sender.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            return false;
        }
        sender.sendMessage(ChatColor.YELLOW + "Checking for updates...");
        new UpdateChecker(Cosmetics.getInstance(), 127149).getLatestVersion(version -> {
            if (!Cosmetics.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                sender.sendMessage(Utils.getColor("&7&m-----------------------------------------"));
                sender.sendMessage(Utils.getColor("&bA new version of SimpleCosmetics&7(SimpleCosmetics " + version + ") &bhas been released!"));
                sender.sendMessage(Utils.getColor("&bPlease update here: " + Utils.getPluginURL));
                sender.sendMessage(Utils.getColor("&7&m-----------------------------------------"));
            } else {
                sender.sendMessage(ChatColor.GREEN + "SimpleCosmetics is up to date!");
            }
        });
        return true;
    }

    private boolean showVersion(CommandSender sender) {
        sender.sendMessage(Utils.getColor("&8&m=============================="));
        sender.sendMessage(Utils.getColor(" &b" + Utils.getName));
        sender.sendMessage(Utils.getColor(" &7Version: &f" + Utils.getVersion));
        sender.sendMessage(Utils.getColor(" &7Author: &f" + Utils.getDeveloper));
        sender.sendMessage(Utils.getColor("&8&m=============================="));
        return true;
    }

    private boolean showHelp(CommandSender sender, String label) {
        sender.sendMessage(Utils.getColor("&8&m=============================="));
        sender.sendMessage(Utils.getColor(" &b" + Utils.getName + " &7- commands"));
        sender.sendMessage(Utils.getColor(" &f/" + label + " &7- open the trails menu"));
        sender.sendMessage(Utils.getColor(" &f/" + label + " menu &7- open the trails menu"));
        sender.sendMessage(Utils.getColor(" &f/" + label + " clear &7- remove your active trail"));
        if (sender.hasPermission("simplecosmetics.admin")) {
            sender.sendMessage(Utils.getColor(" &f/" + label + " reload &7- reload config.yml"));
            sender.sendMessage(Utils.getColor(" &f/" + label + " update &7- check for plugin updates"));
        }
        sender.sendMessage(Utils.getColor(" &f/" + label + " version &7- show plugin version"));
        sender.sendMessage(Utils.getColor(" &f/" + label + " help &7- show this list"));
        sender.sendMessage(Utils.getColor("&8&m=============================="));
        return true;
    }

    private boolean devInfo(CommandSender sender) {
        if (!(sender instanceof Player) || !sender.getName().equalsIgnoreCase("Refrac")) {
            sender.sendMessage(Utils.getDevMessage);
            sender.sendMessage(Utils.getDevMessage2);
            sender.sendMessage(Utils.getDevMessage3);
            return false;
        }
        sender.sendMessage(ChatColor.WHITE + "Hello Refrac!");
        sender.sendMessage(ChatColor.GRAY + "Plugin Version - " + ChatColor.WHITE + Utils.getVersion);
        sender.sendMessage(ChatColor.GRAY + "Config Version - " + ChatColor.WHITE + String.valueOf(Cosmetics.getInstance().getConfig().get("configVersion")));
        sender.sendMessage(ChatColor.GRAY + "End of log.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return Collections.emptyList();
        String partial = args[0].toLowerCase();
        List<String> out = new ArrayList<>();
        for (String s : SUBCOMMANDS) {
            if (s.equals("reload") || s.equals("update")) {
                if (!sender.hasPermission("simplecosmetics.admin")) continue;
            }
            if (s.startsWith(partial)) out.add(s);
        }
        return out;
    }
}
