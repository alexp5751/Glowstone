package net.glowstone.generator;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class FunctionalGenerator extends GlowChunkGenerator {

    // No populators
    public FunctionalGenerator() {
        super();
    }
    
    @Override
    public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
        chunkX <<= 4;
        chunkZ <<= 4;
        

        boolean nether = world.getEnvironment() == Environment.NETHER;
        Material matMain = nether ? Material.NETHERRACK : Material.NETHERRACK;


        byte[] buf = start(Material.AIR);
        
        double totalX;
        double totalZ;

        for (int x = 0; x < 128; x++) {
            for (int z = 0; z < 128; z++) {
                totalX = chunkX + x / 8.0;
                totalZ = chunkZ + z / 8.0;
                
                set(buf, x / 8, (int) (64 + 32 * Math.sin(Math.cos(Math.tan(totalX / 32.0))) + 32 * Math.sin(Math.cos(Math.tan(totalZ / 32.0)))), z / 8, matMain); // change to whatever we need.
            }
        }

        return buf;
    }
    
    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 50, 0);
    }
}
