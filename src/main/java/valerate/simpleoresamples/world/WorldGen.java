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
import valerate.simpleoresamples.blocks.Types;
import valerate.simpleoresamples.blocks.Types.EnumTypeBasic;
import valerate.simpleoresamples.blocks.Types.EnumTypeShiny;
import valerate.simpleoresamples.init.BlockInit;
public class WorldGen implements IWorldGenerator {

	public static HashMap<IBlockState, String> SAMPLES = new HashMap<IBlockState, String>();
	public static HashMap<String, Types.EnumTypeBasic> SAMPLEBLOCKVANILLA = new HashMap<String, Types.EnumTypeBasic>();
	public static HashMap<String, Types.EnumTypeShiny> SAMPLEBLOCKSHINY = new HashMap<String, Types.EnumTypeShiny>();
	public static HashMap<String, Integer> COUNTER;
	
	
	public static void registerSample(String ore, EnumTypeBasic sampleType) {
		SAMPLEBLOCKVANILLA.put(ore, sampleType);
	}
	
	public static void registerSample(String ore, EnumTypeShiny sampleType) {
		SAMPLEBLOCKSHINY.put(ore, sampleType);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		
		COUNTER = scanChunk(chunkX, chunkZ, world);
		
		EnumTypeBasic sampleVanilla;
		EnumTypeShiny sampleShiny;
		
		if (!Config.dimension_whitelist.contains(world.provider.getDimension())) {
			return;
		}
		
		for (String ore: COUNTER.keySet()) {
			int oreSamplesToCreate = Config.oreCounterEnabled ? COUNTER.get(ore)/Config.oresPerSample : Config.samplesPerOrePerChunk;
			sampleVanilla = SAMPLEBLOCKVANILLA.get(ore);
			sampleShiny = SAMPLEBLOCKSHINY.get(ore);
			if (sampleVanilla != null) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					BlockInit.SAMPLE_BLOCK_VANILLA.placeSample(world, x, z, sampleVanilla);
				}

			}else if (sampleShiny != null) {
				for (int i = 0;  i < Math.random()*Math.min(Config.samplesPerOrePerChunk,oreSamplesToCreate)+1 ;i++) {
					int x = (chunkX << 4) + 8 +  random.nextInt(16);
					int z = (chunkZ << 4) + 8 + random.nextInt(16);
					BlockInit.SAMPLE_BLOCK_SHINY.placeSample(world, x, z, sampleShiny);
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
