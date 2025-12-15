package me.refrac.cosmetics.menu;

import me.refrac.cosmetics.Cosmetics;
import me.refrac.cosmetics.menu.items.Trails;
import me.refrac.cosmetics.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class TrailsMenu implements Listener {

    public static Component getTitleComponent() {
        return Component.text(Utils.getColor(Cosmetics.getInstance().getConfig().getString("TrailsMenu.TITLE")));
    }

    public static int getSize() {
        return 36;
    }

    public static void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, getSize(), getTitleComponent());

        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.FLAME.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.FLAME.SLOT"), Trails.flameTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.HEART.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.HEART.SLOT"), Trails.heartTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.SLIME.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.SLIME.SLOT"), Trails.slimeTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.SMOKE.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.SMOKE.SLOT"), Trails.smokeTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.CRITICAL.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.CRITICAL.SLOT"), Trails.criticalTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.NOTES.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.NOTES.SLOT"), Trails.notesTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.LAVA.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.LAVA.SLOT"), Trails.lavaTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.WATER.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.WATER.SLOT"), Trails.waterTrail());
        }
        if (Cosmetics.getInstance().getConfig().getBoolean("TrailsMenu.CLOUD.ENABLED")) {
            inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.CLOUD.SLOT"), Trails.cloudTrail());
        }

        inv.setItem(Cosmetics.getInstance().getConfig().getInt("TrailsMenu.REMOVE-TRAIL.SLOT"), Trails.removeTrailsItem());

        for (int i = 0; i < 36; ++i) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, Glass());
            }
        }

        p.openInventory(inv);
    }

    private static ItemStack Glass() {
        ItemStack stone = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta stonem = stone.getItemMeta();
        stonem.displayName(Component.text(" "));
        stone.setItemMeta(stonem);
        return stone;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView() == null) return;

        Component viewTitle = event.getView().title();
        String plainTitle = PlainTextComponentSerializer.plainText().serialize(viewTitle);
        String expectedTitle = Utils.getColor(Cosmetics.getInstance().getConfig().getString("TrailsMenu.TITLE"));

        if (!plainTitle.equals(expectedTitle)) return;

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        event.setCancelled(true);
        if (event.getCurrentItem().isSimilar(Trails.flameTrail())) {
            if (player.hasPermission("simplecosmetics.trails.flame")) {
                if (!Utils.flame.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.flame.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "flame")));
                } else {
                    Utils.flame.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "flame")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.heartTrail())) {
            if (player.hasPermission("simplecosmetics.trails.heart")) {
                if (!Utils.heart.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.heart.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "heart")));
                } else {
                    Utils.heart.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "heart")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.slimeTrail())) {
            if (player.hasPermission("simplecosmetics.trails.slime")) {
                if (!Utils.slime.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.slime.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "slime")));
                } else {
                    Utils.slime.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "slime")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.smokeTrail())) {
            if (player.hasPermission("simplecosmetics.trails.smoke")) {
                if (!Utils.smoke.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.smoke.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "smoke")));
                } else {
                    Utils.smoke.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "smoke")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.criticalTrail())) {
            if (player.hasPermission("simplecosmetics.trails.critical")) {
                if (!Utils.critical.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.critical.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "critical")));
                } else {
                    Utils.critical.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "critical")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.notesTrail())) {
            if (player.hasPermission("simplecosmetics.trails.notes")) {
                if (!Utils.notes.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.notes.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "notes")));
                } else {
                    Utils.notes.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "notes")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.lavaTrail())) {
            if (player.hasPermission("simplecosmetics.trails.lava")) {
                if (!Utils.lava.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.lava.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "lava")));
                } else {
                    Utils.lava.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "lava")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.waterTrail())) {
            if (player.hasPermission("simplecosmetics.trails.water")) {
                if (!Utils.water.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.water.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "water")));
                } else {
                    Utils.water.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "water")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.cloudTrail())) {
            if (player.hasPermission("simplecosmetics.trails.cloud")) {
                if (!Utils.cloud.contains(player.getName())) {
                    Utils.removeTrails(player);
                    Utils.cloud.add(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.APPLY-TRAIL").replace("{trail}", "cloud")));
                } else {
                    Utils.cloud.remove(player.getName());
                    player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-TRAIL").replace("{trail}", "cloud")));
                }
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-PERMISSION")));
            }
        } else if (event.getCurrentItem().isSimilar(Trails.removeTrailsItem())) {
            if (Utils.flame.contains(player.getName()) || Utils.heart.contains(player.getName()) || Utils.slime.contains(player.getName()) ||
                    Utils.smoke.contains(player.getName()) || Utils.critical.contains(player.getName()) || Utils.notes.contains(player.getName()) ||
                    Utils.lava.contains(player.getName()) || Utils.water.contains(player.getName()) || Utils.cloud.contains(player.getName())) {
                Utils.removeTrails(player);
                player.sendMessage(Utils.PREFIX + Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.REMOVE-ALL-TRAILS")));
            } else {
                player.sendMessage(Utils.getColor(Cosmetics.getInstance().getConfig().getString("Messages.NO-ACTIVE-TRAIL")));
            }
        }
    }
}