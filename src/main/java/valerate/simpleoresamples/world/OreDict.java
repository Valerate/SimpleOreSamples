package valerate.simpleoresamples.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.blocks.SampleBlockVanilla;
import valerate.simpleoresamples.blocks.Types.EnumTypeBasic;
import valerate.simpleoresamples.blocks.Types.EnumTypeShiny;
import net.minecraftforge.common.util.EnumHelper;

public class OreDict {
	
	public static EnumTypeBasic oreTypesVanilla; 
	
	public static void scanConfig() { // Testing testing 1-2-3
		int counter = 0;
		for (String string: Config.ores) {
			String[] split = string.split(":");
			oreTypesVanilla = EnumHelper.addEnum(EnumTypeBasic.class, split[0], new Class<?>[] {int.class,String.class,int.class}, counter, "ore"+counter,Integer.parseInt(split[1]));
			
			counter++;
		}
		
		for (EnumTypeBasic enumer: oreTypesVanilla.values()){
			System.out.println(enumer.getMeta()+":"+enumer.name() +":" + enumer.getName() + ":" + enumer.getColor());
		
		}
		SampleBlockVanilla.VARIANT = PropertyEnum.create("variant", EnumTypeBasic.class, oreTypesVanilla.values());
		System.out.println(SampleBlockVanilla.VARIANT);
		
	}
	
	public static void init() {
		
		
		for (EnumTypeBasic type: EnumTypeBasic.values()) {
			if (type.name() == "ALUMINIUM") {
				System.out.println("Found " + type.name());
				if (OreDictionary.doesOreNameExist("ore"+capitalizeFirstLetter(type.name().toLowerCase())))	{
					registerOre("ore" + capitalizeFirstLetter(type.name().toLowerCase()), type.name().toLowerCase());
					
				}else if ( OreDictionary.doesOreNameExist("oreAluminum")) {
					registerOre("oreAluminum", type.name().toLowerCase());
					
				}else if (OreDictionary.doesOreNameExist("oreBauxite")) {
					registerOre("oreBauxite", type.name().toLowerCase());
					
				}
				
			}else if (type.name() == "URANIUM") {
				if (OreDictionary.doesOreNameExist("ore"+capitalizeFirstLetter(type.name().toLowerCase())))	{
					registerOre("ore" + capitalizeFirstLetter(type.name().toLowerCase()), type.name().toLowerCase());
					
				}else if ( OreDictionary.doesOreNameExist("oreYellorium")) {
					registerOre("oreYellorium", type.name().toLowerCase());
					
				}
			}else if (OreDictionary.doesOreNameExist("ore"+capitalizeFirstLetter(type.name().toLowerCase()))) {
				registerOre("ore" + capitalizeFirstLetter(type.name().toLowerCase()), type.name().toLowerCase());
			}
		}
		
		for (EnumTypeShiny type: EnumTypeShiny.values()) {
			
			if (OreDictionary.doesOreNameExist("ore"+capitalizeFirstLetter(type.name().toLowerCase()))) {
				registerOre("ore" + capitalizeFirstLetter(type.name().toLowerCase()), type.name().toLowerCase());
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
	
	public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
}
