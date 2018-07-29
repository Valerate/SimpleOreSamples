package valerate.simpleoresamples.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import valerate.simpleoresamples.blocks.SampleBlockShiny;
import valerate.simpleoresamples.blocks.SampleBlockVanilla;

public class BlockInit {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final SampleBlockVanilla SAMPLE_BLOCK_VANILLA = new SampleBlockVanilla("sampleblockvanilla");
	public static final SampleBlockShiny SAMPLE_BLOCK_SHINY = new SampleBlockShiny("sampleblockshiny");
}
