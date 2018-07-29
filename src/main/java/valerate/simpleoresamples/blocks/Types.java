package valerate.simpleoresamples.blocks;

import net.minecraft.util.IStringSerializable;
import valerate.simpleoresamples.world.WorldGen;

public class Types {
	
	public enum EnumTypeBasic implements IStringSerializable{
		
		COAL(0, "coal", 3617582),
		IRON(1, "iron", 11571064),
		GOLD(2, "gold", 16435247),
		COPPER(3, "copper", 15888396),
		TIN(4, "tin", 13421772),
		SILVER(5, "silver", 14744063),
		LEAD(6, "lead", 4745077),
		URANIUM(7, "uranium", 7985693),
		ZINZ(8, "zinc", 8219498),
		ALUMINIUM(9, "aluminium", 13028044),
		TUNGSTEN(10, "tungsten", 1315860),
		NICKEL(11, "nickel", 14271132);
		
		private static final EnumTypeBasic[] META_LOOKUP = new EnumTypeBasic[values().length];
		private final int meta;
		private final String name, unlocalizedName;
		private final int color;
		
		private EnumTypeBasic(int meta, String name, int color) {
			this(meta,name,name,color);
		}

		private EnumTypeBasic(int meta, String name,String unlocalizeName, int color) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizeName;
			this.color = color;
		}
		
		@Override
		public String getName(){return this.name;}
		
		public int getMeta(){return this.meta;}
		
		public int getColor() {return this.color;}
		
		public String getUnlocalizedName(){return this.unlocalizedName;}
		
		@Override
		public String toString(){
			return this.name;
		}
		
		public static EnumTypeBasic byMetadata(int meta) {
			return META_LOOKUP[meta];
		}
		
		static {
			for(EnumTypeBasic enumtype: values()) {
				META_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}
		static {
			for (EnumTypeBasic t : values()) {
				META_LOOKUP[t.meta] = t;
				WorldGen.registerSample(t.getName(), t);
			}
		}
	}
	
	public static enum EnumTypeShiny implements IStringSerializable{
		
		DIAMOND(0, "diamond", 11529714),
		EMERALD(1, "emerald", 130081),
		LAPIS(2, "lapis", 797555),
		REDSTONE(3, "redstone", 16711680),
		RUBY(4, "ruby", 16399460),
		SAPPHIRE(5, "sapphire", 2325166),
		PLATINUM(6, "platinum", 8966898),
		IRIDIUM(7, "iridium", 14745598);
		
		private static final EnumTypeShiny[] META_LOOKUP = new EnumTypeShiny[values().length];
		private final int meta;
		private final String name, unlocalizedName;
		private final int color;
		
		private EnumTypeShiny(int meta, String name, int color) {
			this(meta,name,name,color);
		}

		private EnumTypeShiny(int meta, String name,String unlocalizeName, int color) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizeName;
			this.color = color;
		}
		
		@Override
		public String getName(){return this.name;}
		
		public int getMeta(){return this.meta;}
		
		public int getColor() {return this.color;}
		
		public String getUnlocalizedName(){return this.unlocalizedName;}
		
		@Override
		public String toString(){
			return this.name;
		}
		
		public static EnumTypeShiny byMetadata(int meta) {
			return META_LOOKUP[meta];
		}
		
		static {
			for(EnumTypeShiny enumtype: values()) {
				META_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}
		static {
			for (EnumTypeShiny t : values()) {
				META_LOOKUP[t.meta] = t;
				WorldGen.registerSample(t.getName(), t);
			}
		}
	}
}
