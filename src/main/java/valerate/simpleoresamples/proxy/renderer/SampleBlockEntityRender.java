package valerate.simpleoresamples.proxy.renderer;

import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import valerate.simpleoresamples.blocks.entities.SampleBlockEntity;
import valerate.simpleoresamples.util.BaseReferances;

@SideOnly(Side.CLIENT)
public class SampleBlockEntityRender<T extends SampleBlockEntity> extends TileEntitySpecialRenderer<T> {
	
	public static HashMap<String, IBakedModel> CACHE = new HashMap<>();
	
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (!CACHE.containsKey(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(te.getWorld().getBlockState(new BlockPos(te.getPos().getX(), te.getPos().getY()-1, te.getPos().getZ()))).toString())){
		
			ResourceLocation RES = new ResourceLocation( Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(te.getWorld().getBlockState(new BlockPos(te.getPos().getX(), te.getPos().getY()-1, te.getPos().getZ()))).getParticleTexture().toString());
			
			
			IModel modelReTex = null;
			try {
				modelReTex = ModelLoaderRegistry.getModel(new ModelResourceLocation(BaseReferances.MODID+":"+"sampleblockgem",  "normal") );
			} catch (Exception e) {

			}
			
			modelReTex.retexture(ImmutableMap.of("1",RES.toString()));
			//modelReTex.bake(te.getBlockType().getDefaultState(), format, ModelLoader.defaultTextureGetter())
			
			//CACHE.put(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(te.getWorld().getBlockState(new BlockPos(te.getPos().getX(), te.getPos().getY()-1, te.getPos().getZ()))).toString(), value)
		}
		
	}
}
