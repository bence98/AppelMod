package csokicraft.forge18.appelmod;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.common.IWorldGenerator;

public class AppelOreGenerator implements IWorldGenerator{
	protected WorldGenerator oreGen;
	protected int minY, maxY, spawnPasses;
	
	public AppelOreGenerator(int veinSize, int minY, int maxY, int spawnPasses){
		if(minY>maxY || minY<0 || maxY>256) throw new IllegalArgumentException("Please set the Y bounds so that 0<=minY<=maxY<=256");
		oreGen = new WorldGenMinable(AppelMod.appelOre.getDefaultState(), veinSize);
		this.minY = minY;
		this.maxY = maxY;
		this.spawnPasses = spawnPasses;
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if(world.provider.getDimensionId() == 0) //if Overworld
			generateVein(world, chunkX, chunkZ, rand);
	}

	protected void generateVein(World w, int chunkX, int chunkZ, Random rand){
		int baseX = chunkX * 16,
			boundY = maxY - minY,
			baseZ = chunkZ * 16;
		for(int i=0;i<spawnPasses;i++){
			int x = baseX + rand.nextInt(16);
			int y = minY + rand.nextInt(boundY);
			int z = baseZ + rand.nextInt(16);
			oreGen.generate(w, rand, new BlockPos(x, y, z));
		}
	}
}
