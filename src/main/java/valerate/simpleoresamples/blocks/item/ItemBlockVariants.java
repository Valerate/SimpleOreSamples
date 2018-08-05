package valerate.simpleoresamples.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import valerate.simpleoresamples.world.OreDict;

public class ItemBlockVariants extends ItemBlock {

	private String type;
	public ItemBlockVariants(Block block, String type) {
		super(block);
		this.type=type;
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	
	@Override
	public int getMetadata(int damage) {
		// TODO Auto-generated method stub
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "oresample";
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        return "Ore Sample (" + this.type + ")";
	}
}
