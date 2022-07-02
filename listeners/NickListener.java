package it.sieben.phil.me.Phil.listeners;

import it.sieben.phil.me.Phil.Me;
import it.sieben.phil.me.Phil.commands.NickCommand;
import it.sieben.phil.me.Phil.invs.LockerInv;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class NickListener implements Listener {

    public HashMap<UUID, Boolean> nickChatLock = new HashMap<>();
    public static HashMap<UUID, String> playerNameList = new HashMap<>();
    private HashMap<UUID, Integer> nickWarnings = new HashMap<>();

    LockerInv lockerInv = new LockerInv();


     @EventHandler
    public void onInvClick(InventoryClickEvent event) {
         Player player = (Player) event.getWhoClicked();

         if (event.getInventory().getName().equalsIgnoreCase("               §5§kk§r§6§l Nick §r§5§kk")) {
             event.setCancelled(true);
             if (event.getCurrentItem() == null) return;

             if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                 //Close inv
                 player.closeInventory();
             }

             if (event.getCurrentItem().getType().equals(Material.WOOL)) {
                 //Change Skin and name
                 player.closeInventory();
             }

             if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                 //Open Skin locker
                 lockerInv.openLocker(player);
             }

             if (event.getCurrentItem().getType().equals(Material.NAME_TAG)) {
                 //Write name in chat
                 if (playerNameList.containsKey(player.getUniqueId())) NickCommand.unNick(player);
                 player.closeInventory();
                 player.sendMessage(Me.AtmosPrefix + " §fPlease Write your name in the chat");

                 nickChatLock.put(player.getUniqueId(), true);

                  new BukkitRunnable() {
                     int t = Me.getInstance().getConfig().getInt("Nick.Nick-Timeout");
                     @Override
                     public void run() {
                         t --;

                         if (!nickChatLock.containsKey(player.getUniqueId())) this.cancel();

                         if(t <= 0) {
                             if (!nickChatLock.containsKey(player.getUniqueId())) return;

                             player.sendMessage(Me.AtmosPrefix + "§4 Nick Timeout");
                             nickChatLock.remove(player.getUniqueId());
                             this.cancel();
                         }
                     }
                 }.runTaskTimer(Me.getInstance(), 20, 20);
             }


         }else if (event.getInventory().getName().equalsIgnoreCase(player.getInventory().getName())) {
             if (event.getCurrentItem().getType() == Material.NAME_TAG) {
                 event.setCancelled(true);
             }
         }
    }

    @EventHandler
    public void onItemInteraction(PlayerInteractEvent event)
    {
         Player player = event.getPlayer();

         if (event.getItem() == null) return;

         if (event.getItem().getType() == Material.NAME_TAG)
         {
             if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) NickCommand.randomNick(player);
             else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) NickCommand.openGui(player);
         }
    }

     @EventHandler
    public void onChatEvent(PlayerChatEvent event) {
         Player player = event.getPlayer();
         if (!nickChatLock.containsKey(player.getUniqueId())) return;

         if (nickChatLock.get(player.getUniqueId())) {
             event.setCancelled(true);


             String oldName = event.getPlayer().getName();
             String newName = event.getMessage();
             List<String> blackList = Me.getInstance().NickNamesCfg.getStringList("BlackList");

             for (String s : blackList) {
                 if (newName.contains(s)) {
                     player.sendMessage(Me.AtmosPrefix + " §4Invalid Name. §o(BlackListed name, Contains spaces, name is to short or to long)");
                     nickChatLock.remove(player.getUniqueId());


                     int i = 0;
                     if (nickWarnings.containsKey(player.getUniqueId())) {
                         if (nickWarnings.get(player.getUniqueId()) >= 2) {
                             player.kickPlayer("§falltagalltag.tiel24.de\n\n" +
                                     "§4Kicked for §f§o'Nick abuse'\n" +
                                     "§6Support: §f%Support%");
                             Logger.getLogger("Atmos Nick").warning("Kicked "  + player.getName() + " (" + player.getUniqueId() + ") for Nick abuse.");
                             i = 3;
                             nickWarnings.remove(player.getUniqueId());
                         }else {
                             i = nickWarnings.get(player.getUniqueId());
                             i ++;
                             nickWarnings.put(player.getUniqueId(), i);
                         }
                     }else {
                         nickWarnings.put(player.getUniqueId(), 1);
                         i = 1;
                     }


                     if (Me.loggWarnings)
                         Logger.getLogger("Atmos Nick").warning("The player: " + player.getName() + " (" + player.getUniqueId() + ") tried to NickName the name :'" + newName + "'");
                     Logger.getLogger("Atmos Nick").warning("It got blocked by the BlackList. (" + i + ". Warning)");
                 return;
                 }
             }

             if (!newName.contains(" ") && newName.length() > 2 && newName.length() < 16 && !newName.contains("#")) {
                 playerNameList.put(player.getUniqueId(), oldName);

                 player.setDisplayName(newName);
                 player.setPlayerListName(newName);


                 player.sendMessage(Me.AtmosPrefix + " You are now Nicked as §f§o'" + event.getMessage() + "'");
                 nickChatLock.remove(player.getUniqueId());


                 GameProfileUtils.changeName(newName, player);
             }else {
                 player.sendMessage(Me.AtmosPrefix + " §4Invalid Name. §o(BlackListed name, Contains spaces, name is to short or to long)");
                 nickChatLock.remove(player.getUniqueId());


                 int i = 0;
                 if (nickWarnings.containsKey(player.getUniqueId())) {
                     if (nickWarnings.get(player.getUniqueId()) >= 2) {
                         player.kickPlayer("§falltagalltag.tiel24.de\n\n" +
                                 "§4Kicked for §f§o'Nick abuse'\n" +
                                 "§6Support: §f%Support%");
                         Logger.getLogger("Atmos Nick").warning("Kicked "  + player.getName() + " (" + player.getUniqueId() + ") for Nick abuse.");
                         i = 3;
                         nickWarnings.remove(player.getUniqueId());
                     }else {
                         i = nickWarnings.get(player.getUniqueId());
                         i ++;
                         nickWarnings.put(player.getUniqueId(), i);
                     }
                 }else {
                     nickWarnings.put(player.getUniqueId(), 1);
                     i = 1;
                 }


                 if (Me.loggWarnings)
                 Logger.getLogger("Atmos Nick").warning("The player: " + player.getName() + " (" + player.getUniqueId() + ") tried to NickName the name :'" + newName + "'");
                 Logger.getLogger("Atmos Nick").warning("It got blocked by the BlackList. (" + i + ". Warning)");
             }

         }
     }
}
