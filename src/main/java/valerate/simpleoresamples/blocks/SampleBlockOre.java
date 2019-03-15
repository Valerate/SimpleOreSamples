package valerate.simpleoresamples.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class SampleBlockOre extends SampleBlock {
	public static SampleBlockOre INSTANCE;
	
	public SampleBlockOre(String name, String oreType, int color, String dropType) {
		super(name, "ore", oreType, color, dropType, "sampleblockore");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SampleBlockAABB;
	}
}
