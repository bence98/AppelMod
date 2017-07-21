package csokicraft.forge18.appelmod;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.*;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.*;

@Mod(modid = AppelMod.MODID, version = AppelMod.VERSION)
public class AppelMod
{
    public static final String MODID = "appelmod";
    public static final String VERSION = "1.1.2";
    
    @SidedProxy(clientSide="csokicraft.forge18.appelmod.ClientProxy", serverSide="csokicraft.forge18.appelmod.CommonProxy")
    public static CommonProxy proxy;
    
    public static ToolMaterial appelTool;
    public static ArmorMaterial appelArmor;
    
    public static Item appelChunk;
    public static Item appelBlade;
    
    public static Item appelHelm;
    public static Item appelChest;
    public static Item appelLegs;
    public static Item appelBoots;
    
    public static Block appelBlock;
    public static Block appelOre;
    
    public static Item appelBlockItem;
    public static Item appelOreItem;
    
    public static boolean genAppelOre;
    
    protected static Map<Item, ModelResourceLocation> modelLoc;
    
    private Configuration config;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent evt){
    	config = new Configuration(evt.getSuggestedConfigurationFile());
    	config.load();
    	genAppelOre = config.getBoolean("genAppelOre", "world", true, "Should Appel Ore be generated?");
    	config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
		initItems();
		proxy.registerModels();
		addCrafting();
		if(genAppelOre) GameRegistry.registerWorldGenerator(new AppelOreGenerator(10, 0, 16, 1), 0);
    }
    
	private void initItems(){
		appelTool = EnumHelper.addToolMaterial("APPEL", 3, 1561, 8, 3, 10);
		appelArmor = EnumHelper.addArmorMaterial("APPEL", "appelmod:appel", 15, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1);
		
		appelChunk = new Item().setUnlocalizedName("appelChunk").setRegistryName("appelChunk").setCreativeTab(CreativeTabs.MATERIALS);
		appelBlade = new ItemSword(appelTool).setUnlocalizedName("appelBlade").setRegistryName("appelBlade").setCreativeTab(CreativeTabs.COMBAT);
	    
	    appelHelm = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.HEAD).setUnlocalizedName("appelHelm").setRegistryName("appelHelm").setCreativeTab(CreativeTabs.COMBAT);
	    appelChest = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.CHEST).setUnlocalizedName("appelChest").setRegistryName("appelChest").setCreativeTab(CreativeTabs.COMBAT);
	    appelLegs = new ItemArmor(appelArmor, 2, EntityEquipmentSlot.LEGS).setUnlocalizedName("appelLegs").setRegistryName("appelLegs").setCreativeTab(CreativeTabs.COMBAT);
	    appelBoots = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.FEET).setUnlocalizedName("appelBoots").setRegistryName("appelBoots").setCreativeTab(CreativeTabs.COMBAT);
		
		GameRegistry.register(appelChunk);
		GameRegistry.register(appelBlade);
		
		GameRegistry.register(appelHelm);
		GameRegistry.register(appelChest);
		GameRegistry.register(appelLegs);
		GameRegistry.register(appelBoots);
		
		appelBlock = new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setUnlocalizedName("appelBlock").setRegistryName("appelBlock").setResistance(1000).setHardness(5);
		appelBlock.setHarvestLevel("pickaxe", 2);
		appelOre = new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setUnlocalizedName("appelOre").setRegistryName("appelOre").setResistance(15).setHardness(3);
		appelOre.setHarvestLevel("pickaxe", 2);
		
		GameRegistry.register(appelBlock);
		GameRegistry.register(appelOre);
		
		appelBlockItem=new ItemBlock(appelBlock).setRegistryName("appelBlock");
		appelOreItem=new ItemBlock(appelOre).setRegistryName("appelOre");
		
		GameRegistry.register(appelBlockItem);
		GameRegistry.register(appelOreItem);
		
		OreDictionary.registerOre("oreAppel", appelOre);
		OreDictionary.registerOre("gemAppel", appelChunk);
		OreDictionary.registerOre("blockAppel", appelBlock);
		
		modelLoc = new HashMap<Item, ModelResourceLocation>();
		modelLoc.put(appelChunk, new ModelResourceLocation(AppelMod.MODID+":appelChunk", "inventory"));
		modelLoc.put(appelBlade, new ModelResourceLocation(AppelMod.MODID+":appelBlade", "inventory"));
		modelLoc.put(appelHelm, new ModelResourceLocation(AppelMod.MODID+":appelHelm", "inventory"));
		modelLoc.put(appelChest, new ModelResourceLocation(AppelMod.MODID+":appelChest", "inventory"));
		modelLoc.put(appelLegs, new ModelResourceLocation(AppelMod.MODID+":appelLegs", "inventory"));
		modelLoc.put(appelBoots, new ModelResourceLocation(AppelMod.MODID+":appelBoots", "inventory"));
		modelLoc.put(ItemBlock.getItemFromBlock(appelBlock), new ModelResourceLocation(AppelMod.MODID+":appelBlock", "inventory"));
		modelLoc.put(ItemBlock.getItemFromBlock(appelOre), new ModelResourceLocation(AppelMod.MODID+":appelOre", "inventory"));
	}

	private void addCrafting(){
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelBlock),
				"***",
				"***",
				"***",
				'*', "gemAppel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(appelChunk, 9), "blockAppel"));
		GameRegistry.addShapedRecipe(new ItemStack(appelChunk),
				"***",
				"*a*",
				"***",
				'*', Items.QUARTZ,
				'a', Items.APPLE);
		GameRegistry.addSmelting(appelOre, new ItemStack(appelChunk), 1);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelBlade),
				"*",
				"*",
				"i",
				'*', "gemAppel",
				'i', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelHelm),
				"***",
				"* *",
				'*', "gemAppel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelChest),
				"* *",
				"***",
				"***",
				'*', "gemAppel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelLegs),
				"***",
				"* *",
				"* *",
				'*', "gemAppel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appelBoots),
				"* *",
				"* *",
				'*', "gemAppel"));
	}
}
