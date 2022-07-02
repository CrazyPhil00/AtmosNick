package it.sieben.phil.me.Phil;

import it.sieben.phil.me.Phil.commands.AtmosCommands;
import it.sieben.phil.me.Phil.commands.NickCommand;
import it.sieben.phil.me.Phil.listeners.JoinListener;
import it.sieben.phil.me.Phil.listeners.LockerListener;
import it.sieben.phil.me.Phil.listeners.NickListener;
import it.sieben.phil.me.Phil.metrics.Metrics;
import it.sieben.phil.me.Phil.util.GameProfileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Me extends JavaPlugin {

    static Me instance;

    public static boolean loggWarnings = true;

    public static String AtmosPrefix;


    public FileConfiguration NickNamesCfg;
    public File NickNames;




    @Override
    public void onEnable() {
        //METRICS
        int pluginId = 15623;
        Metrics metrics = new Metrics(this, pluginId);

        //OTHER
        instance = this;

        System.out.println("\n  _  ___       _   _           _ \n" +
                " /_\\  |  |\\/| | | |_   |\\ | | |  |/\n" +
                "/   \\ |  |  | |_|  _|  | \\| | |_ |\\\n" +
                "\n" +
                "\t\t v1.0  by CrazyPhil ");

        //CONFIGS

        saveDefaultConfig();

        NickNames = new File("plugins/AtmosNick", "NickNames.yml");
        NickNamesCfg = YamlConfiguration.loadConfiguration(NickNames);

        if (!NickNames.exists()) {

            NickNamesCfg.options().header("BlackList: You can Blacklist NickNames.\n" +
                    "NickNames: You need these for the random Nicknames.\n" +
                    "Skins: https://mineskin.org/gallery");
            ArrayList<String> NamesList = new ArrayList<>();
            ArrayList<String> BlackList = new ArrayList<>();
            ArrayList<String> TextureSignature = new ArrayList<>();
            ArrayList<String> TextureData = new ArrayList<>();
            NamesList.add("exampleName");
            BlackList.add("exampleName");
            TextureSignature.add("VZvpN9KC3+0FHRETOfWAHZbJDtvgE/CSjf9EzlS7tiCoSaT9ukSgtJuVwCTEzjtYJHk1hJGvRZHsOCCHQmjdnbToQe9Bs8gvvl+iqCaFmauyR1oTXo/+NPzaDgbtOa9B1foG1agazBsW46GJRLOJVwPqcx7XoJKJlV7pSuu0wo40GOJ+zU1eXpsw9grsY2+PYXLdr0rusTAOzPalC47fpWiK+iBQeTMAvieniTUzIMplm15dpB1L6vZI5cX+PEAjDY75clZp9TvZKKf1RXqSf5ARCrl2YpP1VintS60csgejgK3GglmpaxuRk0t34hw46t7Ur65/+NrNpvFLlsBVuOhodaKSxTHxhN27sqsThRaQmYp7FgKWDpfQFqKQaAZ51GyZYdsdsaSXa6fVk+qFMqzy+N1r1Ig9mmtsKb2heXt/Nx/476Kn4q+6mVuBi7TmFLUHNMd26guGC0JXimRUaAJcuNFH8jm8cY1/ViRtchjOFtlCc+QsWaGMoKtVsuQGAl9qTpyj5WTv9RZJgx97Mtez9PEUdimPptubouI/OQUj+H56wHgD4J5EJQMe14wZHyj6s5v2aXeoB1olf/5Gr67PDCxC4OotqmO+DwoxEsN+fn5RnKx9w/bjLPyY/A7G3iwPK5sLEfICFO5q/Yf6UlhE1ahmpT2Kvf/ZeHn7jfE=");
            TextureData.add("ewogICJ0aW1lc3RhbXAiIDogMTY1NjU5NDAwNjIwNiwKICAicHJvZmlsZUlkIiA6ICI5NmI1MTQxZTg0NzU0MjhjOTIzMmUxYjUyMmMzZmI1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlQjB5MiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mZjJlZjQzNDkwZTI1ZWM5YjAxZGYyODVmYTJkMWM3ZDJmNzRiNTc1YTIzYjU1YTFkZWMyYWVkMTBkMzE0M2ZhIgogICAgfQogIH0KfQ==");

            NickNamesCfg.set("BlackList", BlackList);



            NickNamesCfg.set("NickNames", NamesList);


            NickNamesCfg.set("Skins.TextureSignature", TextureSignature);

            NickNamesCfg.set("Skins.TextureData", TextureData);


            try {
                NickNamesCfg.save(NickNames);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        AtmosPrefix = getConfig().getString("Prefix.Atmos-msg").replaceAll("&", "§");
        loggWarnings = getConfig().getBoolean("Nick.Log-Nick-Warnings");




        //COMMANDS

        getCommand("nick").setExecutor(new NickCommand());
        getCommand("atmos").setExecutor(new AtmosCommands());

        //LISTENERS

        Bukkit.getPluginManager().registerEvents(new NickListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new LockerListener(), this);






    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    public static Me getInstance() {
        return instance;
    }
}
