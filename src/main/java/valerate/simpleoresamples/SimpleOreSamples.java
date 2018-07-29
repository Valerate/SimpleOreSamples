package valerate.simpleoresamples;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import valerate.simpleoresamples.proxy.CommonProxy;
import valerate.simpleoresamples.util.*;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = BaseReferances.MODID, name = BaseReferances.NAME, version = BaseReferances.VERSION)
public class SimpleOreSamples {
	
	public static File config;
	
	@Instance
	public static SimpleOreSamples instance;
	
	@SidedProxy(clientSide = BaseReferances.CLIENT, serverSide = BaseReferances.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {RegistryHandler.PreInitRegistries(event);}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {RegistryHandler.InitRegistries(event);}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {RegistryHandler.PostInitRegistries(event);}
}
