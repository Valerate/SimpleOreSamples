package valerate.simpleoresamples.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.blocks.Types.EnumTypeShiny;
import valerate.simpleoresamples.blocks.item.ItemBlockVariants;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.init.ItemInit;
import valerate.simpleoresamples.util.IHasModel;
import valerate.simpleoresamples.util.IMetaName;

public class SampleBlockShiny extends Block implements IMetaName,IHasModel {

	public static final PropertyEnum<Types.EnumTypeShiny> VARIANT = PropertyEnum.<Types.EnumTypeShiny>create("variant", Types.EnumTypeShiny.class);
	public static final AxisAlignedBB SampleBlockAABB = new AxisAlignedBB(0.25D, 0, 0.25D, 0.75D, 0.125D, 0.75D);
	
	public static SampleBlockShiny INSTANCE;
	
	public SampleBlockShiny(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT,  Types.EnumTypeShiny.DIAMOND));
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	public void placeSample(World world, int x, int z, Types.EnumTypeShiny sample) {
		BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 64, z));

		if (surface.getY() > 1 && surface.getY() < 255) {
			if (world.isAirBlock(surface) && BlockInit.SAMPLE_BLOCK_VANILLA.canPlaceBlockAt(world, surface)) {
				world.setBlockState(surface, this.getDefaultState().withProperty(VARIANT, sample));
			}
		}
	}
	

	@Override
	public String getSpecialName(ItemStack stack) {
		
		return EnumTypeShiny.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		return ((Types.EnumTypeShiny)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return ((Types.EnumTypeShiny)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, Types.EnumTypeShiny.byMetadata(meta));
	}
	
	@Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)  {
		
		drops.clear();
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		
		return new ItemStack(state.getBlock(), 1, this.getMetaFromState(state));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {

		for(EnumTypeShiny variant: Types.EnumTypeShiny.values()) {
			items.add(new ItemStack(this,1,variant.getMeta()));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
			return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	
	@Override
	public void registerModels() {
		for (int i = 0; i < Types.EnumTypeShiny.values().length; i++) {
			SimpleOreSamples.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "sample_" + EnumTypeShiny.values()[0].getName(), "inventory");
		}
		
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }
	
	
	@Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	 
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
	    return this.canPlaceBlockOnSide(world, pos, EnumFacing.UP);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
	    if (side != EnumFacing.UP) return false;
	    final BlockPos offset = pos.offset(side.getOpposite());
	    final IBlockState state = world.getBlockState(offset);
	    final BlockFaceShape shape = state.getBlockFaceShape(world, offset, side);
	    return shape == BlockFaceShape.SOLID;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SampleBlockAABB;
	}

	
	////////////////////////////////////////
	
	public static class ColorHandler implements IBlockColor, IItemColor {

		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			return (tintIndex == 0 ? -1 : Types.EnumTypeShiny.values()[stack.getItemDamage()].getColor());
		}

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			return Types.EnumTypeShiny.values()[stack.getItemDamage()].getColor();
		}

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
			int color = (tintIndex == 0 ? -1 :  Types.EnumTypeShiny.values()[state.getBlock().getMetaFromState(state)].getColor()  );
			return color;
		}
	}


}
