package net.glowstone.command;

import net.glowstone.ChunkManager;
import net.glowstone.GlowChunk;
import net.glowstone.GlowWorld;
import net.glowstone.generator.FunctionalGenerator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * A built-in command to demonstrate all chat colors.
 */
public class FunctionCommand extends BukkitCommand {

	public FunctionCommand(String name) {
		super(name, "Plots a function! :D", "/function", Arrays
				.<String> asList());
		usageMessage = "/function \"<function1>; <function2>; <function3>\" <material1> <material2> <material3>";
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel,
			String[] args) {
		if (args.length > 0) {
			String command = "";

			for (int i = 0; i < args.length; i++) {
				command += args[i] + ((i == args.length - 1) ? "" : " ");
			}

			String functions = "";
			String materials = "";

			for (int i = command.length() - 1; i > 0; i--) {
				if (command.charAt(i) == '"') {
					functions = command.substring(1, i);
					materials = command.substring(i + 1);
					i = 0;
				}
			}

			FunctionalGenerator generator = new FunctionalGenerator();

			int j = 0;
			for (int i = 0; i < functions.length(); i++) {
				if (functions.charAt(i) == ';' || functions.length() - 1 == i) {
					generator.addFunction(functions.substring(j, i));
					j = i + 1;
				}
			}

			j = 0;
			for (int i = 0; i < materials.length(); i++) {
				if (materials.charAt(i) == ' ') {
					generator.addMaterial(Material.matchMaterial(materials
							.substring(j, i)));
					j = i + 1;
				}
			}

			Player p = (Player) sender;
			GlowWorld world = (GlowWorld) p.getWorld();
			ChunkManager chunks = world.getChunkManager();
			chunks.setGenerator(generator);

			for (GlowChunk chunk : chunks.getLoadedChunks()) {
				world.regenerateChunk(chunk.getX(), chunk.getZ());
			}

			return true;
		}

		sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
		return false;

	}

}
