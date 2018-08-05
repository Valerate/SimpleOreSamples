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
		oreCounterEnabled = config.getBoolean("Count ores in chunk and control the amount of samples thereafter" , category, true , " Will not exceed oresPerSample config");
		samplesPerOrePerChunk = config.getInt("Amount of samples per chunk", category, 3, 0, 16, "Amount of samples to generate in a chunk per ore type");
		oresPerSample = config.getInt("Amount of ores to create a surface sample", category, 15, 0, 100, "Amount of ores required to generate a sample of that type");
		String[] oreSamples = config.get(category, "Ores samples", new String[] {		
						"Iron:11571064:ore", "Coal:3617582:ore", "Gold:16435247:ore", "Copper:15888396:ore", "Tin:13421772:ore", "Silver:14744063:ore", "Lead:4745077:ore",
						"Uranium:7985693:ore","Zinc:8219498:ore","Aluminium:13028044:ore","Tungsten:1315860:ore","Nickel:14271132:ore",
						"Diamond:11529714:gem","Emerald:130081:gem","Lapis:797555:gem","Redstone:16711680:gem","Ruby:16399460:gem","Sapphire:2325166:gem",
						"Platinum:8966898:gem","Iridium:14745598:gem","CertusQuartz:16777215:gem"
				},"Ores to create and spawn samples of in the form 'name:colortint:type'").getStringList();
		for (String s : oreSamples) {
			oresToMakeSampleOf.add(s);
		}
		
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
