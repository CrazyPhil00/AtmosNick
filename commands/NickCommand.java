package it.sieben.phil.me.Phil.commands;

import com.mojang.authlib.GameProfile;
import it.sieben.phil.me.Phil.Me;
import it.sieben.phil.me.Phil.listeners.JoinListener;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import it.sieben.phil.me.Phil.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static it.sieben.phil.me.Phil.listeners.NickListener.playerNameList;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player)
        {

            Player player = (Player) sender;
            if (!player.hasPermission("nick.use")) player.sendMessage(Me.AtmosPrefix + ChatColor.RED + " You don't have permission to use this command!");
            else
            {
                if (args.length < 1) randomNick(player);
                else if (args[0].equalsIgnoreCase("unnick")) unNick(player);
                else if (args[0].equalsIgnoreCase("gui")) openGui(player);

            }

        }else sender.sendMessage(Me.AtmosPrefix + ChatColor.RED + " Only Player can use this command!");

        return false;
    }


    public static void unNick(Player player) {
        if (playerNameList.containsKey(player.getUniqueId())) {
            GameProfileUtils.changeName(playerNameList.get(player.getUniqueId()), player);
            playerNameList.remove(player.getUniqueId());

        }else player.sendMessage(Me.AtmosPrefix + "§4 You are not nicked!");
    }


    public static void openGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST.getDefaultSize(), "               §5§kk§r§6§l Nick §r§5§kk");

        String signature = JoinListener.PlayerSkins.get(player)[0];
        String data = JoinListener.PlayerSkins.get(player)[1];


        inventory.setItem(4, GameProfileUtils.createCustomHead(signature, data, player.getName()));
        inventory.setItem(11, new ItemBuilder(Material.BARRIER).setDisplayname("§4Decline").build());
        inventory.setItem(13, new ItemBuilder(Material.NAME_TAG).setDisplayname("Nickname").build());

        ItemStack acceptStack = new ItemStack(Material.WOOL, 1, (byte) 5);
        ItemMeta acceptMeta = acceptStack.getItemMeta();
        acceptMeta.setDisplayName("§2Accept");
        acceptStack.setItemMeta(acceptMeta);

        inventory.setItem(15, acceptStack);

        player.openInventory(inventory);

    }

    public static void randomNick(Player player) {
        List<String> nickList = Me.getInstance().NickNamesCfg.getStringList("NickNames");
        String name = nickList.get(new Random().nextInt(nickList.size()));

        if (Bukkit.getServer().getOnlinePlayers().contains(name)) {
            player.sendMessage(Me.AtmosPrefix + " §4Player name exists already");
        }else {
            if (playerNameList.containsKey(player.getUniqueId())) unNick(player);
            GameProfileUtils.changeName(name, player);
        }
    }

}
