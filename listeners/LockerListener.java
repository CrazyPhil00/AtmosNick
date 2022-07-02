package it.sieben.phil.me.Phil.listeners;

import com.mojang.authlib.GameProfile;
import it.sieben.phil.me.Phil.Me;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class LockerListener implements Listener {

    List<String> TextureSignature = Me.getInstance().NickNamesCfg.getStringList("Skins.TextureSignature");
    List<String> TextureData = Me.getInstance().NickNamesCfg.getStringList("Skins.TextureData");
    List<String> TextureName = Me.getInstance().NickNamesCfg.getStringList("Skins.Name");

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equalsIgnoreCase("Skin Locker")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            String clickedSkin = event.getCurrentItem().getItemMeta().getLocalizedName();
            int skinIndex = 0;
            int i = 0;
            for (String s : TextureName) {
                if (s.equalsIgnoreCase(clickedSkin)) {
                    skinIndex = i;

                    String signature = TextureSignature.get(i);
                    String data = TextureData.get(i);

                    player.sendMessage("Signature: " + signature);
                    player.sendMessage("Data: " + data);

                    CraftPlayer cp = (CraftPlayer) player;
                    GameProfile profile = cp.getProfile();
                    GameProfileUtils.changeSkin(player, signature, data);

                }
                i++;
            }

        }
    }
}
