package valerate.simpleoresamples.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import valerate.simpleoresamples.Config;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.init.ItemInit;
import valerate.simpleoresamples.world.OreDict;
import valerate.simpleoresamples.world.WorldGen;

@EventBusSubscriber
public class RegistryHandler {
		
	
	static WorldGen generator = new WorldGen();
	
	@SubscribeEvent
	public static void onBiomeDecoPost(DecorateBiomeEvent.Post event) {
		generator.generate(event.getRand(), event.getChunkPos().x, event.getChunkPos().z , event.getWorld(), null, null);
	}	
	
	
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		BlockInit.readConfig();
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		
		for (Item item: ItemInit.ITEMS) {
			SimpleOreSamples.proxy.registerItemRenderer(item);

		}
		
		for (Block block: BlockInit.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void PreInitRegistries(FMLPreInitializationEvent e) {
		
		Config.registerConfig(e);
		//OreDict.scanConfig();
	}
	
	public static void InitRegistries(FMLInitializationEvent e) {
		//GameRegistry.registerWorldGenerator(new WorldGen(), 999);
	}
	
	public static void PostInitRegistries(FMLPostInitializationEvent e) {
		SimpleOreSamples.proxy.registerRenderers();
		OreDict.init();
	}
	
}
