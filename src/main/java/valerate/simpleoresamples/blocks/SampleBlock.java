package valerate.simpleoresamples.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.blocks.item.ItemBlockVariants;
import valerate.simpleoresamples.init.ItemInit;
import valerate.simpleoresamples.util.IHasModel;

public abstract class SampleBlock extends Block implements IHasModel {

	public static final AxisAlignedBB SampleBlockAABB = new AxisAlignedBB(0.25D, 0, 0.25D, 0.75D, 0.125D, 0.75D);
	
	private int color;
	private String oredictPrefix;
	private String oredictBase;
	private ItemStack drop;
	private String dropType;
	private String blockRenderFile;
	
	public SampleBlock(String name, String oredictPrefix, String oredictBase, int color, String dropType, String blockRenderFile) {
		super(Material.GROUND);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setHardness(2F);
		
		this.color = color;
		this.oredictPrefix = oredictPrefix;
		this.oredictBase = oredictBase;
		this.dropType = dropType;
		this.blockRenderFile = blockRenderFile;
		
		ItemInit.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}
	
	public String getDropType() {
		return dropType;
	}
	
	public String getOredict() {
		return oredictPrefix + oredictBase;
	}
	
	public String getOredictPrefix() {
		return oredictPrefix;
	}
	
	public String getOredictBase() {
		return oredictBase;
	}
	
	public ItemStack getOreItemStack() {
		List<ItemStack> items = OreDictionary.getOres(getOredict(), false);
		
		if (!items.isEmpty()) {
			return items.get(0);
		}
		else {
			return ItemStack.EMPTY;
		}
	}
	
	public int getColor() {
		return color;
	}
	
	public void placeSample(World world, int x, int z) {
		BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 64, z));
		if (surface.getY() > 1 && surface.getY() < 255) {
			Block surfaceBlock = world.getBlockState(surface).getBlock();
			
			// Don't place in fluids, but do replace grass and junk on the surface
			if (!(surfaceBlock instanceof BlockLiquid)
					&& surfaceBlock.isReplaceable(world, surface)
					&& this.canPlaceBlockAt(world, surface)) {
				world.setBlockState(surface, this.getDefaultState());
			}
		}
	}
	
	@Override
	public void registerModels() {
		SimpleOreSamples.proxy.registerBlockRenderer(this, blockRenderFile);
	}
	
	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	// Remove entity collision from block
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SampleBlockAABB;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		// "Stolen" from net.minecraft.block.BlockRailBase
		return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
	}
	
	// Break when the block underneath is broken
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt(world, pos)) {
			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return drop.getItem();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return drop.getItemDamage();
	}
	
	// Break when shoved by piston
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	// Otherwise it tries to use damageDropped, which is only for our drop, not our block!
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	public static class ColorHandler implements IBlockColor, IItemColor {
		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			return ((ItemBlockVariants)stack.getItem()).getBlock().getColor();
		}

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
			// This prevents block break particles from being colored, somehow
			return tintIndex == 0 ? -1 : ((SampleBlock) state.getBlock()).getColor();
		}
	}
}
