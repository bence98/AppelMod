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
    public static final String MODID = "AppelMod";
    public static final String VERSION = "1.1.1";
    
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
		appelArmor = EnumHelper.addArmorMaterial("APPEL", "appelmod:appel", 15, new int[]{3, 8, 6, 3}, 10, SoundEvents.item_armor_equip_diamond);
		
		appelChunk = new Item().setUnlocalizedName("appelChunk").setCreativeTab(CreativeTabs.tabMaterials);
		appelBlade = new ItemSword(appelTool).setUnlocalizedName("appelBlade").setCreativeTab(CreativeTabs.tabCombat);
	    
	    appelHelm = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.HEAD).setUnlocalizedName("appelHelm").setCreativeTab(CreativeTabs.tabCombat);
	    appelChest = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.CHEST).setUnlocalizedName("appelChest").setCreativeTab(CreativeTabs.tabCombat);
	    appelLegs = new ItemArmor(appelArmor, 2, EntityEquipmentSlot.LEGS).setUnlocalizedName("appelLegs").setCreativeTab(CreativeTabs.tabCombat);
	    appelBoots = new ItemArmor(appelArmor, 1, EntityEquipmentSlot.FEET).setUnlocalizedName("appelBoots").setCreativeTab(CreativeTabs.tabCombat);
		
		GameRegistry.registerItem(appelChunk, "appelChunk");
		GameRegistry.registerItem(appelBlade, "appelBlade");
		
		GameRegistry.registerItem(appelHelm, "appelHelm");
		GameRegistry.registerItem(appelChest, "appelChest");
		GameRegistry.registerItem(appelLegs, "appelLegs");
		GameRegistry.registerItem(appelBoots, "appelBoots");
		
		appelBlock = new Block(Material.iron).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("appelBlock").setResistance(1000).setHardness(5);
		appelBlock.setHarvestLevel("pickaxe", 2);
		appelOre = new Block(Material.iron).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("appelOre").setResistance(15).setHardness(3);
		appelOre.setHarvestLevel("pickaxe", 2);
		
		GameRegistry.registerBlock(appelBlock, "appelBlock");
		GameRegistry.registerBlock(appelOre, "appelOre");
		
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
				'*', Items.quartz,
				'a', Items.apple);
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
