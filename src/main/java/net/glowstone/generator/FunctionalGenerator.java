package net.glowstone.generator;

import java.util.ArrayList;
import java.util.Random;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class FunctionalGenerator extends GlowChunkGenerator {

	private ArrayList<String> functions;
	private ArrayList<Material> materials;
	private int resolution;
	
	// No populators
	public FunctionalGenerator() {
		super();
		functions = new ArrayList<>();
		materials = new ArrayList<>();
		resolution = 8;
	}

	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		chunkX <<= 4;
		chunkZ <<= 4;
		
		byte[] buf = start(Material.AIR);
		
        double result;
		double totalX;
		double totalZ;
		
		for (int i = 0; i < functions.size(); i++) {
	        Expression e = new ExpressionBuilder(functions.get(i))
	        .variables("x", "z")
	        .build();
	        
			for (int x = 0; x < 16 * resolution; x++) {
				for (int z = 0; z < 16 * resolution; z++) {
					totalX = chunkX + x / (double) resolution;
					totalZ = chunkZ + z / (double) resolution;
					
					e.setVariable("x", totalX).setVariable("z", totalZ);
					result = e.evaluate();
					if (!Double.isNaN(result)) {
						int res = (int) result;
						set(buf, x / resolution, res, z / resolution, getMaterial(i));
					}
				}
			}
		}
		
		return buf;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 256, 0);
	}
	
	public void addFunction(String func) {
		functions.add(func);
	}
	
	public void addMaterial(Material material) {
		if (material == null) {
			materials.add(Material.GRASS);
		} else {
			materials.add(material);
		}
	}
	
	private Material getMaterial(int index) {
		if (index > materials.size() - 1) {
			return Material.GRASS;
		} else {
			return materials.get(index);
		}
	}
	
	public void setResolution(int res) {
		resolution = res;
	}
}
