package valerate.simpleoresamples.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import valerate.simpleoresamples.blocks.SampleBlock;
import valerate.simpleoresamples.blocks.SampleBlockOre;
import valerate.simpleoresamples.blocks.item.ItemBlockVariants;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.util.BaseReferances;

public class ClientProxy extends CommonProxy {

	private static final ModelResourceLocation gemResourceLocation = new ModelResourceLocation(BaseReferances.MODID+":sampleblockgem", "normal");
	private static final ModelResourceLocation oreResourceLocation = new ModelResourceLocation(BaseReferances.MODID+":sampleblockore", "normal");
	
	@Override
	public void registerItemRenderer(Item item) {
		boolean isOre = ((ItemBlockVariants)item).getBlock() instanceof SampleBlockOre;
		ModelLoader.setCustomModelResourceLocation(item, 0, isOre ? oreResourceLocation : gemResourceLocation);
	}
	
	private static final StateMapperBase ignoreState = new StateMapperBase() {
		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
			return iBlockState.getBlock() instanceof SampleBlockOre ? oreResourceLocation : gemResourceLocation;
		}
	};
	
	@Override
	public void registerBlockRenderer(Block block, String file) {
		ModelLoader.setCustomStateMapper(block, ignoreState);
	}
	
	
	////////////////////////////////////////
	
	@Override
	public void registerRenderers() {
		SampleBlock.ColorHandler handler = new SampleBlock.ColorHandler();
		
		BlockInit.SAMPLEBLOCKS.values().forEach(block -> {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(handler, block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(handler, block);
		});
	}
}
