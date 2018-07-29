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
	private static Configuration config;
	public static String[] ores;
	

	public static void init(File file) {
		
		config = new Configuration(file);
		
		String category;
		
		category = "Config";
		oreCounterEnabled = config.getBoolean("Count ores in chunk and control the amount of samples thereafter" , category, true , " Will not exceed oresPerSample config");
		samplesPerOrePerChunk = config.getInt("Amount of samples per chunk", category, 3, 0, 16, "Amount of samples to generate in a chunk per ore type");
		oresPerSample = config.getInt("Amount of ores to create a surface sample", category, 15, 0, 100, "Amount of ores required to generate a sample of that type");
		
		//ores = config.getStringList("Ores to add samples for", category, new String[] {"COAL:3617582","IRON:11571064","GOLD:gold:16435247" }, "Add an ore with BASENAMEINCAPS:desiredtintinint");
		int[] dimensions = config.get(category, "Dimension whitelist", new int[]{0}, "Whitelist of the dimensions to generate samples in").getIntList();
		for (int d : dimensions) {
			dimension_whitelist.add(d);
		}
		
		config.save();
		
	}
	
	public static void registerConfig(FMLPreInitializationEvent e) {
		SimpleOreSamples.config = new File(e.getModConfigurationDirectory()+ "/" + BaseReferances.MODID);
		SimpleOreSamples.config.mkdirs();
		init(new File(SimpleOreSamples.config.getPath(),BaseReferances.MODID + ".cfg" ));
	}
	
}
