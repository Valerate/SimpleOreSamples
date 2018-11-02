package valerate.simpleoresamples.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
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
import valerate.simpleoresamples.blocks.item.ItemBlockVariants;
import valerate.simpleoresamples.init.BlockInit;
import valerate.simpleoresamples.init.ItemInit;
import valerate.simpleoresamples.util.IHasModel;

public class SampleBlockOre extends Block implements IHasModel {

	public static final AxisAlignedBB SampleBlockAABB = new AxisAlignedBB(0.25D, 0, 0.25D, 0.75D, 0.125D, 0.75D);
	
	public static SampleBlockOre INSTANCE;
	private int color;
	private String ore;
	private ItemStack drop;
	private String dropType;
	
	public SampleBlockOre(String name, String ore, int color, String dropType) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setHardness(2F);
		this.color = color;
		this.ore = ore;
		this.dropType = dropType;
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlockVariants(this,this.ore).setRegistryName(this.getRegistryName()));
	}
	
	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}
	
	public String getDropType() {
		return dropType;
	}
	
	public String getOre() {
		return ore;
	}
	
	public int getColor() {
		return color;
	}
	
	public void placeSample(World world, int x, int z) {
		BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 64, z));

		if (surface.getY() > 1 && surface.getY() < 255) {
			if (world.isAirBlock(surface) && this.canPlaceBlockAt(world, surface)) {
				world.setBlockState(surface, this.getDefaultState());
			}
		}
	}
	


	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return drop.getItem();
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(drop);
	}
	
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		
		return new ItemStack(state.getBlock(), 1, this.getMetaFromState(state));
	}
	
	
	
	
	@Override
	public void registerModels() {
		SimpleOreSamples.proxy.registerBlockRenderer(this, "sampleblockore");
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
	
	/*////////////////////////////////////////
		
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
	// TODO Auto-generated method stub
	return super.getExtendedState(state, world, pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
	// TODO Auto-generated method stub
	return super.hasTileEntity(state);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
	// TODO Auto-generated method stub
	return super.createTileEntity(world, state);
	}

	@Override
    protected BlockStateContainer createBlockState() {
    	Collection < IProperty<? >> properties = super.createBlockState().getProperties();
    	return new ExtendedBlockState(this, properties.toArray(new IProperty[properties.size()]), new IUnlistedProperty[] {RENDER_PROPERTY});
    }
	
	////////////////////////////////////////*/
	
	public static class ColorHandler implements IBlockColor, IItemColor {

		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			if (Block.getBlockFromItem(stack.getItem()) instanceof SampleBlockOre) {
				return (int)((SampleBlockOre) Block.getBlockFromItem(stack.getItem())).getColor();
			}
			return 0;
		}

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (Block.getBlockFromItem(stack.getItem()) instanceof SampleBlockOre) {
				return (int)((SampleBlockOre) Block.getBlockFromItem(stack.getItem())).getColor();
			}
			return 0;
		}

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
			if (state.getBlock() instanceof SampleBlockOre) {
				return (tintIndex == 0 ? -1 : (int)((SampleBlockOre) state.getBlock()).getColor());
			}
			return 0;
		}
	}

}
