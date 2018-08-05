package valerate.simpleoresamples.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import valerate.simpleoresamples.blocks.SampleBlockGem;
import valerate.simpleoresamples.blocks.SampleBlockOre;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.util.BaseReferances;

public class ClientProxy extends CommonProxy {
	
	
	@Override
	public void registerItemRenderer(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(BaseReferances.MODID+":sampleblockgem",  "normal"));
	}
	
	@Override
	public void registerBlockRenderer(Block block, String file) {
		
		StateMapperBase ignoreState = new StateMapperBase() {
		      @Override
		      protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
		        return new ModelResourceLocation(BaseReferances.MODID+":"+file,  "normal") ;
		      }
		    };
		
		
		ModelLoader.setCustomStateMapper(block, ignoreState);
	}
	
	
	////////////////////////////////////////
	
	@Override
	public void registerRenderers() {
		BlockInit.SAMPLEBLOCKORE.values().forEach(block ->{
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SampleBlockOre.ColorHandler(), block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SampleBlockOre.ColorHandler(), block);
		});
		
		BlockInit.SAMPLEBLOCKGEM.values().forEach(block ->{
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SampleBlockGem.ColorHandler(), block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SampleBlockGem.ColorHandler(), block);
		});
		
	}
}
