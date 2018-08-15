package valerate.simpleoresamples.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.blocks.SampleBlockGem;
import valerate.simpleoresamples.blocks.SampleBlockOre;


public class BlockInit {

	public static final HashMap<String, SampleBlockOre> SAMPLEBLOCKORE = new HashMap<String, SampleBlockOre>();
	public static final HashMap<String, SampleBlockGem> SAMPLEBLOCKGEM = new HashMap<String, SampleBlockGem>();
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	private static ArrayList<String> FAILED = new ArrayList<>();
	
	public static void readConfig() {
		Config.oresToMakeSampleOf.forEach(str ->{
			String[] txt = str.split("\\|");
			if (!(txt.length >= 2)) {
				FAILED.add(str);
				return;
			}
			if (txt[2].equals("ore")) {
				try {
					SAMPLEBLOCKORE.put(txt[0], new SampleBlockOre("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]), txt.length == 4 ? txt[3] : "empty"));
				} catch (Exception e) {
					FAILED.add(str);
				}
				
			}else if (txt[2].equals("gem")) {
				try {
					SAMPLEBLOCKGEM.put(txt[0],new SampleBlockGem("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]),txt.length == 4 ? txt[3] : "empty"));
				} catch (Exception e) {
					FAILED.add(str);
				}
			} else {
				FAILED.add(str);
			}
		});
		
		
		
		SimpleOreSamples.LOGGER.warn("Sucsesfully made sample block of {}/{} ore samples", (SAMPLEBLOCKORE.size()+SAMPLEBLOCKGEM.size()), Config.oresToMakeSampleOf.size());
		
		if (!FAILED.isEmpty()) {
			SimpleOreSamples.LOGGER.warn("Failed to create a sample blocks for: ");
			FAILED.forEach( str ->{
				SimpleOreSamples.LOGGER.warn(" <{}>", str);
			});
		}
	}
	
	public static void fixDrops() {
		SAMPLEBLOCKORE.values().forEach(sample -> {
			String dropType = sample.getDropType();
			ItemStack drop = ItemStack.EMPTY;
			if (dropType.equals("empty") ) {
				SimpleOreSamples.LOGGER.info("No drop configured for {}", sample.getOre());
			} else {
				if (dropType.equals("nugget") || dropType.equals("bar") || dropType.equals("dust") || dropType.equals("plate") || dropType.equals("tinyPile") || dropType.equals("block")|| dropType.equals("gem") || dropType.equals("crystal") ) {
					
					if (sample.getOre().equals("Aluminium") || sample.getOre().equals("Aluminum") || sample.getOre().equals("Bauxite")) {
						if (OreDictionary.doesOreNameExist(dropType+"Aluminium")) 
							drop = OreDictionary.getOres(dropType+"Aluminium").get(0);
						else if (OreDictionary.doesOreNameExist(dropType+"Aluminum")) 
							drop = OreDictionary.getOres(dropType+"Aluminum").get(0);
					} else
					if (sample.getOre().equals("Uranium") || sample.getOre().equals("Yellorium")) {
						if (OreDictionary.doesOreNameExist(dropType+"Uranium")) 
							drop = OreDictionary.getOres(dropType+"Uranium").get(0);
						if (OreDictionary.doesOreNameExist(dropType+"Yellorium")) 
							drop = OreDictionary.getOres(dropType+"Yellorium").get(0);
					} else
					if (OreDictionary.doesOreNameExist(dropType+sample.getOre())) 
							drop = OreDictionary.getOres(dropType+sample.getOre()).get(0);
				}else {
					
					// Credits: GoryMoon https://github.com/GoryMoon/HorsePower/blob/1.12/src/main/java/se/gory_moon/horsepower/util/Utils.java#L117
					
					String[] str = dropType.split(":");
					int meta = str.length == 2 ? 0 : "*".equals(str[2]) ? OreDictionary.WILDCARD_VALUE: Integer.parseInt(str[2]);
					
					NBTTagCompound compound = new NBTTagCompound();
		            compound.setString("id", str[0] + ":" + str[1]);
		            compound.setByte("Count", (byte) 1);
		            compound.setShort("Damage", (short) meta);
		            drop = new ItemStack(compound);
				}
			}
			if (drop == ItemStack.EMPTY) SimpleOreSamples.LOGGER.info("No drop found for {} , with type <{}>", sample.getOre(), dropType);
			sample.setDrop(drop);
			
		});
		
		SAMPLEBLOCKGEM.values().forEach(sample -> {
			String dropType = sample.getDropType();
			ItemStack drop = ItemStack.EMPTY;
			if (dropType.equals("empty") ) {
				SimpleOreSamples.LOGGER.info("No drop configured for {}", sample.getOre());
			} else {
				if (dropType.equals("nugget") || dropType.equals("bar") || dropType.equals("dust") || dropType.equals("plate") || dropType.equals("tinyPile") || dropType.equals("block")|| dropType.equals("gem") || dropType.equals("crystal") ) {
					if (OreDictionary.doesOreNameExist(dropType+sample.getOre())) 
							drop = OreDictionary.getOres(dropType+sample.getOre()).get(0);
				}else {
					
					// Credits: GoryMoon https://github.com/GoryMoon/HorsePower/blob/1.12/src/main/java/se/gory_moon/horsepower/util/Utils.java#L117
					
					String[] str = dropType.split(":");
					int meta = str.length == 2 ? 0 : "*".equals(str[2]) ? OreDictionary.WILDCARD_VALUE: Integer.parseInt(str[2]);
					
					NBTTagCompound compound = new NBTTagCompound();
		            compound.setString("id", str[0] + ":" + str[1]);
		            compound.setByte("Count", (byte) 1);
		            compound.setShort("Damage", (short) meta);
		            drop = new ItemStack(compound);
				}
			}
			if (drop == ItemStack.EMPTY) SimpleOreSamples.LOGGER.info("No drop found for {} , with type <{}>", sample.getOre(), dropType);
			sample.setDrop(drop);
			
		});
	}
}
