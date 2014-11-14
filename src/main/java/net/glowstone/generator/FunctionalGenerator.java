package net.glowstone.generator;

import java.util.Random;

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
        Material matMain = nether ? Material.NETHERRACK : Material.GRASS;


        byte[] buf = start(Material.AIR);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                set(buf, x, 1, z, matMain); //This 1 would change to whatever we need.
                set(buf, x, 0, z, Material.BEDROCK);
            }
        }

        return buf;
    }
}
