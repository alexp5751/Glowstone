package net.glowstone.command;

import java.util.Arrays;

import net.glowstone.GlowWorld;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.ChatMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SmiteCommand extends BukkitCommand {

    public SmiteCommand(String name) {
        super(name, "Smites a player.", "/smite", Arrays.<String>asList());
        usageMessage = "/smite <Player>";
    }
    
	@Override
	public boolean execute(CommandSender sender, String commandLabel,String[] args) {
		
		if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[0]);

        if (player != null) {
        	GlowWorld world = (GlowWorld) player.getWorld();
        	world.strikeLightning(player.getLocation());
        	world.createExplosion(player.getLocation(), 10f);
        } 

        return true;
	}

}
