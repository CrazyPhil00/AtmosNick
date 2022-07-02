package it.sieben.phil.me.Phil.invs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import it.sieben.phil.me.Phil.Me;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import it.sieben.phil.me.Phil.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.UUID;

import static it.sieben.phil.me.Phil.util.GameProfileUtils.createCustomHead;

public class LockerInv {


    public void openLocker(Player player) {
        Inventory locker = Bukkit.createInventory(null, 54, "Skin Locker");

        List<String> TextureSignature = Me.getInstance().NickNamesCfg.getStringList("Skins.TextureSignature");
        List<String> TextureData = Me.getInstance().NickNamesCfg.getStringList("Skins.TextureData");
        List<String> TextureName = Me.getInstance().NickNamesCfg.getStringList("Skins.Name");

        String signature,data,name;

        for (int i = 0; i < TextureSignature.size(); i++) {
            signature = TextureSignature.get(i);
            data = TextureData.get(i);
            name = TextureName.get(i);

            locker.setItem(i, GameProfileUtils.createCustomHead(signature, data, name));
        }




        player.openInventory(locker);
    }

}
