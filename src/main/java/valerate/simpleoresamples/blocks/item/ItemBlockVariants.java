package valerate.simpleoresamples.blocks.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import valerate.simpleoresamples.blocks.SampleBlock;

public class ItemBlockVariants extends ItemBlock {
	public ItemBlockVariants(SampleBlock block) {
		super(block);
	}
	
	@Override
	public SampleBlock getBlock() {
		return (SampleBlock) block;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
		ItemStack oreItemStack = getBlock().getOreItemStack();
		String localizedOreName;

		if (oreItemStack != ItemStack.EMPTY) {
			localizedOreName = oreItemStack.getDisplayName();
		}
		else {
			localizedOreName = I18n.format("oresample.unknown_ore", getBlock().getOredict());
		}
		
		return I18n.format("oresample.generic", localizedOreName);
	}
}
