package valerate.simpleoresamples.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class SampleBlockGem extends SampleBlock {
	public SampleBlockGem INSTANCE;
	
	public SampleBlockGem(String name, String gemType, int color, String dropType) {
		super(name, "ore", gemType, color, dropType, "sampleblockgem");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SampleBlockAABB;
	}
}
