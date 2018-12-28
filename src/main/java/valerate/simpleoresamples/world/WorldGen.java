package valerate.simpleoresamples.world;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.blocks.SampleBlockGem;
import valerate.simpleoresamples.blocks.SampleBlockOre;
import valerate.simpleoresamples.init.BlockInit;
public class WorldGen implements IWorldGenerator {

	public static HashMap<IBlockState, String> SAMPLES = new HashMap<IBlockState, String>();
	public static HashMap<String, Integer> COUNTER;
	
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		if (!Config.dimension_whitelist.contains(world.provider.getDimension()) || Config.samplesPerOrePerChunk == 0) {
			return;
		}
		
		COUNTER = scanChunk(world.getChunkFromChunkCoords(chunkX, chunkZ));
		
		for (String ore: COUNTER.keySet()) {
			int oreSamplesToCreate = Config.oreCounterEnabled ? COUNTER.get(ore)/Config.oresPerSample : Config.samplesPerOrePerChunk;

			SampleBlockOre sampleOre = BlockInit.SAMPLEBLOCKORE.get(ore);
			SampleBlockGem sampleGem = BlockInit.SAMPLEBLOCKGEM.get(ore);
			
			if (sampleOre != null) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					sampleOre.placeSample(world, x, z);
				}

			}else if (sampleGem != null) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					sampleGem.placeSample(world, x, z);
				}
			}
			
		}
	}
	
	public HashMap<String, Integer> scanChunk(Chunk chunk) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		
		for (int i = 1; i <= 256; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {
					IBlockState bs = chunk.getBlockState(j, i, k);
					Block b = bs.getBlock();

					if (b == Blocks.AIR || b == Blocks.STONE || b == Blocks.DIRT || b == Blocks.GRAVEL) {
						continue;
					}

					String name = SAMPLES.get(bs);
					
					if (name != null) {
						Integer count = counter.get(name);
						
						if (count != null) {
							if (Config.oreCounterEnabled) {
								counter.put(name, count + 1);
							}
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
