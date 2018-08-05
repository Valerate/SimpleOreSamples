package valerate.simpleoresamples.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
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
			String[] txt = str.split(":");
			if (txt.length != 3) {
				FAILED.add(str);
				return;
			}
			if (txt[2].equals("ore")) {
				try {
					SAMPLEBLOCKORE.put(txt[0], new SampleBlockOre("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1])));
				} catch (Exception e) {
					FAILED.add(str);
				}
				
			}else if (txt[2].equals("gem")) {
				try {
					SAMPLEBLOCKGEM.put(txt[0],new SampleBlockGem("sampleblock" + txt[0], txt[0], Integer.parseInt(txt[1])));
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
}
