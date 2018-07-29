package valerate.simpleoresamples.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import valerate.simpleoresamples.SimpleOreSamples;
import valerate.simpleoresamples.init.ItemInit;
import valerate.simpleoresamples.util.IHasModel;

public class ItemBase extends Item implements IHasModel {
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		SimpleOreSamples.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
}
