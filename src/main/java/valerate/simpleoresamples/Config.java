package valerate.simpleoresamples;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import valerate.simpleoresamples.util.BaseReferances;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Config {
	
	
	public static int samplesPerOrePerChunk;
	public static int oresPerSample;
	public static boolean oreCounterEnabled;
	public static Set<Integer> dimension_whitelist = new HashSet<>();
	public static Set<String> oresToMakeSampleOf = new HashSet<>();
	private static Configuration config;
	public static String[] ores;
	

	public static void init(File file) {
		
		config = new Configuration(file);
		
		String category;
		
		category = "Config";
		oreCounterEnabled = config.getBoolean("OreInChunkCounter" , category, true , "Count ores in chunk and control the amount of samples thereafter Will not exceed oresPerSample config");
		samplesPerOrePerChunk = config.getInt("MaxSamplesPerOrePerChunk", category, 3, 0, 16, "Amount of samples per chunk to try to generate per entry in the ore samples category");
		oresPerSample = config.getInt("OreNeededForSample", category, 15, 0, 100, "Amount of ores in a chunk required to create a surface sample of said ore");
		String[] oreSamples = config.get(category, "Ores samples", new String[] {		
						"Iron|11571064|ore|nugget", "Coal|3617582|ore|minecraft:coal:0", "Gold|16435247|ore|nugget", "Copper|15888396|ore|nugget",
						"Tin|13421772|ore|nugget", "Silver|14744063|ore|nugget", "Lead|4745077|ore|nugget","Uranium|7985693|ore|nugget",
						"Zinc|8219498|ore|nugget","Aluminium|13028044|ore|nugget","Tungsten|1315860|ore|nugget","Nickel|14271132|ore|nugget",
						"Diamond|11529714|gem|minecraft:diamond:0","Emerald|130081|gem|minecraft:emerald:0","Lapis|797555|gem|minecraft:dye:4","Redstone|16711680|gem|minecraft:redstone:0",
						"Ruby|16399460|gem|gem","Sapphire|2325166|gem|gem","Platinum|8966898|gem|nugget","Iridium|14745598|gem|nugget","CertusQuartz|16777215|gem|crystal"
				},"Ores to create and spawn samples of in the form 'name|colortint|type|drop', name is the oredict entry without the 'ore' infront ( oreIron -> Iron), colortint is in int-color value, type is either gem or ore (look of the sample, see cursepage for image examples), drop can either be in oreDict form of 'nugget,dust,bar,plate, gem or block' of the material you defined in the start or definied personally with 'Mod:item:meta'").getStringList();
		for (String s : oreSamples) {
			oresToMakeSampleOf.add(s);
		}
		int[] dimensions = config.get(category, "Dimension whitelist", new int[]{0}, "Whitelist of the dimensions to generate samples in").getIntList();
		for (int d : dimensions) {
			dimension_whitelist.add(d);
		}
		
		config.save();
		
	}
	
	public static void registerConfig(FMLPreInitializationEvent e) {
		init(new File(e.getModConfigurationDirectory(),BaseReferances.MODID + ".cfg" ));
	}
	
}
