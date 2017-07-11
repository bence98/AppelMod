package csokicraft.forge18.appelmod;

import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerModels(){
		ItemModelMesher imm=Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for(Entry<Item, ModelResourceLocation> e:AppelMod.modelLoc.entrySet()){
			imm.register(e.getKey(), 0, e.getValue());
			ModelBakery.registerItemVariants(e.getKey(), e.getValue());
		}
	}
}
