package valerate.simpleoresamples.world;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.init.BlockInit;
public class WorldGen implements IWorldGenerator {

	public static HashMap<IBlockState, String> SAMPLES = new HashMap<IBlockState, String>();
	public static HashMap<String, Integer> COUNTER;
	
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		if (!Config.dimension_whitelist.contains(world.provider.getDimension())) {
			return;
		}
		
		COUNTER = scanChunk(chunkX, chunkZ, world);
		
		for (String ore: COUNTER.keySet()) {
			int oreSamplesToCreate = Config.oreCounterEnabled ? COUNTER.get(ore)/Config.oresPerSample : Config.samplesPerOrePerChunk;

			if (BlockInit.SAMPLEBLOCKORE.containsKey(ore)) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					BlockInit.SAMPLEBLOCKORE.get(ore).placeSample(world, x, z);
				}

			}else if (BlockInit.SAMPLEBLOCKGEM.containsKey(ore)) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					BlockInit.SAMPLEBLOCKGEM.get(ore).placeSample(world, x, z);
				}
			}
			
		}
	}
	
	public HashMap<String, Integer> scanChunk(int cx, int cz, World world) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		IBlockState bs;
		Block b;
		int x = cx << 4;
		int z = cz << 4;
		
		for (int i = 1; i <= 256; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {

					bs = world.getBlockState((new BlockPos(x + j, i, z+ k)));
					b = bs.getBlock();

					if (b == Blocks.AIR || b == Blocks.STONE || b == Blocks.DIRT || b == Blocks.GRAVEL) {
						continue;
					}

					String name = SAMPLES.get(bs);
					
					if (name != null) {
						if (counter.containsKey(name)) {
							if (Config.oreCounterEnabled) counter.replace(name, counter.get(name), counter.get(name)+1);
						}else {
							counter.put(name, 1);
						}
					}
				}
			}

		}
		return counter;
	}

}
