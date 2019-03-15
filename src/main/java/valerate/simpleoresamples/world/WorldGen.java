package valerate.simpleoresamples.world;

import java.util.HashMap;
import java.util.Map;
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
import valerate.simpleoresamples.blocks.SampleBlock;

public class WorldGen implements IWorldGenerator {

	public static HashMap<IBlockState, SampleBlock> SAMPLES = new HashMap<>();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (!Config.dimension_whitelist.contains(world.provider.getDimension()) || Config.samplesPerOrePerChunk == 0) {
			return;
		}
		
		HashMap<SampleBlock, Integer> counter = scanChunk(world.getChunkFromChunkCoords(chunkX, chunkZ));
		
		for (Map.Entry<SampleBlock, Integer> entry : counter.entrySet()) {
			int samples;
			
			if (Config.oreCounterEnabled) {
				samples = Math.min(entry.getValue() / Config.oresPerSample, Config.samplesPerOrePerChunk);
			}
			else {
				samples = Config.samplesPerOrePerChunk;
			}

			for (int i = 0; i < samples; i++) {
				if (random.nextFloat() < Config.oreSampleChance) {
					int x = (chunkX << 4) + random.nextInt(16);
					int z = (chunkZ << 4) + random.nextInt(16);
					entry.getKey().placeSample(world, x, z);
				}
			}
		}
	}
	
	public HashMap<SampleBlock, Integer> scanChunk(Chunk chunk) {
		HashMap<SampleBlock, Integer> counter = new HashMap<>();
		
		for (int i = 1; i <= 256; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {
					IBlockState bs = chunk.getBlockState(j, i, k);
					Block b = bs.getBlock();

					if (b == Blocks.AIR || b == Blocks.STONE || b == Blocks.DIRT || b == Blocks.GRAVEL) {
						continue;
					}

					SampleBlock sample = SAMPLES.get(bs);
					
					if (sample != null) {
						Integer count = counter.get(sample);
						
						if (count != null) {
							if (Config.oreCounterEnabled) {
								counter.put(sample, count + 1);
							}
						}else {
							counter.put(sample, 1);
						}
					}
				}
			}
		}
		
		return counter;
	}
}
