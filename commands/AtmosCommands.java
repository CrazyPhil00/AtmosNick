package it.sieben.phil.me.Phil.commands;

import it.sieben.phil.me.Phil.Me;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public class AtmosCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args[0].equalsIgnoreCase("config reload")) {
            Me.getInstance().reloadConfig();
            try {
                Me.getInstance().NickNamesCfg.load(Me.getInstance().NickNames);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
