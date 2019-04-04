package valerate.simpleoresamples.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.blocks.SampleBlock;
import valerate.simpleoresamples.blocks.SampleBlockGem;
import valerate.simpleoresamples.blocks.SampleBlockOre;


public class BlockInit {
	public static final HashMap<String, SampleBlock> SAMPLEBLOCKS = new HashMap<>();

	public static void readConfig() {
		ArrayList<String> failed = new ArrayList<>();
		
		Config.oresToMakeSampleOf.forEach(str ->{
			String[] txt = str.split("\\|");
			if (!(txt.length >= 2)) {
				failed.add(str);
				return;
			}
			if (txt[2].equals("ore")) {
				try {
					SAMPLEBLOCKS.put(txt[0], new SampleBlockOre("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]), txt.length == 4 ? txt[3] : "empty"));
					//SAMPLEBLOCKORE.put(txt[0], new SampleBlockOre("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]), txt.length == 4 ? txt[3] : "empty"));
				} catch (Exception e) {
					failed.add(str);
				}
				
			}else if (txt[2].equals("gem")) {
				try {
					SAMPLEBLOCKS.put(txt[0], new SampleBlockGem("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]), txt.length == 4 ? txt[3] : "empty"));
					//SAMPLEBLOCKGEM.put(txt[0],new SampleBlockGem("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1]),txt.length == 4 ? txt[3] : "empty"));
				} catch (Exception e) {
					failed.add(str);
				}
			} else {
				failed.add(str);
			}
		});
		
		SimpleOreSamples.LOGGER.warn("Sucsesfully made sample block of {}/{} ore samples", SAMPLEBLOCKS.size(), Config.oresToMakeSampleOf.size());
		
		if (!failed.isEmpty()) {
			SimpleOreSamples.LOGGER.warn("Failed to create a sample blocks for: ");
			failed.forEach( str ->{
				SimpleOreSamples.LOGGER.warn(" <{}>", str);
			});
		}
	}
	
	private static final String[][] dropAliases = {
			{"Aluminium", "Aluminum", "Bauxite"},
			{"Uranium", "Yellorite"},
			{"Titanium", "Rutile"}
	};
	
	private static final HashMap<String, String[]> dropRemap = new HashMap<>();
	
	static {
		for (String[] aliasList : dropAliases)
			for (String alias : aliasList)
				dropRemap.put(alias.toLowerCase(), aliasList);
	}
	
	public static Map<String, String[]> getDropRemap() {
		return dropRemap;
	}
	
	private static ItemStack getOredictOrEmpty(String oredictEntry) {
		List<ItemStack> ores = OreDictionary.getOres(oredictEntry, false);
		
		if (!ores.isEmpty())
			return ores.get(0);
		
		return ItemStack.EMPTY;
	}
	
	public static void fixDrops() {
		SAMPLEBLOCKS.values().forEach(sample -> {
			String dropType = sample.getDropType();
			ItemStack drop = ItemStack.EMPTY;
			if (dropType.equals("empty") ) {
				SimpleOreSamples.LOGGER.info("No drop configured for {}", sample.getOredict());
			} else {
				String[] aliases = dropRemap.get(sample.getOredictBase().toLowerCase());
				
				if (aliases == null)
					drop = getOredictOrEmpty(dropType + sample.getOredictBase());
				else
					for (String alias : aliases) {
						drop = getOredictOrEmpty(dropType + alias);
						if (drop != ItemStack.EMPTY)
							break;
					}
			
				if(drop == ItemStack.EMPTY) {
					String[] str = dropType.split(":", 3);
					int end = str.length - 1;
					
					short damage = 0;
					
					try {
						damage = Short.parseShort(str[end]);
						--end;
					}
					catch (NumberFormatException e) {}
					
					String mod = end < 1 ? "minecraft" : str[0];
					String item = str[end];

					// Credits: GoryMoon https://github.com/GoryMoon/HorsePower/blob/1.12/src/main/java/se/gory_moon/horsepower/util/Utils.java#L117
					NBTTagCompound compound = new NBTTagCompound();
		            compound.setString("id", mod + ":" + item);
		            compound.setByte("Count", (byte) 1);
		            compound.setShort("Damage", damage);
		            drop = new ItemStack(compound);
				}
			}
			if (drop == ItemStack.EMPTY) SimpleOreSamples.LOGGER.info("No drop found for {} , with type <{}>", sample.getOredict(), dropType);
			sample.setDrop(drop);
			
		});
	}
}
