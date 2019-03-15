package valerate.simpleoresamples.world;

import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.blocks.SampleBlock;
import valerate.simpleoresamples.init.BlockInit;

public class OreDict {
	
	public static void init() {
		Map<String, String[]> dropRemap = BlockInit.getDropRemap();
		
		for (SampleBlock sample : BlockInit.SAMPLEBLOCKS.values()) {
			String[] aliases = dropRemap.get(sample.getOredictBase().toLowerCase());
			
			if (aliases == null) {
				registerOre(sample.getOredict(), sample);
			}
			else {
				for (String alias : aliases) {
					registerOre(sample.getOredictPrefix() + alias, sample);
				}
			}
		}
	}
	
	public static void registerOre(String name, SampleBlock sample) {			
		for (ItemStack stack : OreDictionary.getOres(name, false)) {
			Block b = Block.getBlockFromItem(stack.getItem());
			
			if (b != Blocks.AIR) {
				int meta = stack.getItemDamage();
				ImmutableList<IBlockState> states = b.getBlockState().getValidStates();
				if (meta >= states.size()) {
					meta = 0;
				}
				IBlockState bs = states.get(meta);
				WorldGen.SAMPLES.put(bs, sample);
			}
		}
	}
}
