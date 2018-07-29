package valerate.simpleoresamples.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import valerate.simpleoresamples.blocks.SampleBlockShiny;
import valerate.simpleoresamples.blocks.SampleBlockVanilla;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.util.BaseReferances;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(BaseReferances.MODID, filename), id));
	}
	
	
	////////////////////////////////////////
	
	@Override
	public void registerRenderers() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SampleBlockVanilla.ColorHandler(), BlockInit.SAMPLE_BLOCK_VANILLA);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SampleBlockVanilla.ColorHandler(), BlockInit.SAMPLE_BLOCK_VANILLA);
		
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SampleBlockShiny.ColorHandler(), BlockInit.SAMPLE_BLOCK_SHINY);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SampleBlockShiny.ColorHandler(), BlockInit.SAMPLE_BLOCK_SHINY);
		
	}
}
