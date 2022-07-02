package it.sieben.phil.me.Phil.listeners;

import it.sieben.phil.me.Phil.Me;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;


public class JoinListener implements Listener {

    public static HashMap<Player, String[]> PlayerSkins = new HashMap<>();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String[] data = GameProfileUtils.getSkinTexture(player.getName());
        PlayerSkins.put(player, data);


        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6§oNick");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Press left click to get a Random Nickname.");
        lore.add("Press right click to open the gui.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        player.getInventory().setItem(1, itemStack);
    }

    @EventHandler
    public void  onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerSkins.remove(player);
    }
}
