package valerate.simpleoresamples.world;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.blocks.SampleBlockGem;
import valerate.simpleoresamples.blocks.SampleBlockOre;
import valerate.simpleoresamples.init.BlockInit;

public class OreDict {
	
	public static void init() {
		
		for (SampleBlockGem sample: BlockInit.SAMPLEBLOCKGEM.values()) {
			if (OreDictionary.doesOreNameExist("ore"+sample.getOre())) {
				registerOre("ore" + sample.getOre(), sample.getOre());
			}
		}
		
		for (SampleBlockOre sample: BlockInit.SAMPLEBLOCKORE.values()) {
			if (sample.getOre().equals("Aluminium") || sample.getOre().equals("Aluminum") || sample.getOre().equals("Bauxite")) {
				
				if (OreDictionary.doesOreNameExist("oreAluminium"))	{ registerOre("oreAluminium", sample.getOre());}
				if (OreDictionary.doesOreNameExist("oreAluminum"))  { registerOre("oreAluminum", sample.getOre());}
				if (OreDictionary.doesOreNameExist("oreBauxite")) 	{ registerOre("oreBauxite", sample.getOre());}
				
			}else if (sample.getOre().equals("Uranium") || sample.getOre().equals("Yellorium")) {
				
				if (OreDictionary.doesOreNameExist("oreUranium")) 	{ registerOre("oreUranium", sample.getOre());}
				if (OreDictionary.doesOreNameExist("oreYellorium")) { registerOre("oreYellorium", sample.getOre());}
				
			}else if (sample.getOre().equals("Titanium") || sample.getOre().equals("Rutile")) {
				
				if (OreDictionary.doesOreNameExist("oreTitanium")) 	{ registerOre("oreTitanium", sample.getOre());}
				if (OreDictionary.doesOreNameExist("oreRutile")) 	{ registerOre("oreRutile", sample.getOre());}
				
			}else if (OreDictionary.doesOreNameExist("ore"+sample.getOre())) {
				registerOre("ore" + sample.getOre(), sample.getOre());
			}
		}
		
		
	}
	

	public static void registerOre(String name, String base) {			
		for (ItemStack stack : OreDictionary.getOres(name)) {
			Block b = Block.getBlockFromItem(stack.getItem());

			int meta = stack.getItemDamage();
			ImmutableList<IBlockState> states = b.getBlockState().getValidStates();
			if (meta >= states.size()) {
				meta = 0;
			}
			IBlockState bs = states.get(meta);
			WorldGen.SAMPLES.put(bs, base);
			
		}
		
	}
	
}
