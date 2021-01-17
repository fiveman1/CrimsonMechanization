package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.items.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemHoeBase extends ItemHoe implements IInitializeable {
    public ItemHoeBase(ToolMaterial material, String name){
        super(material);
        RegistryHandler.initItem(this, name);
        RegistryHandler.INITIALIZEABLES.add(this);
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
