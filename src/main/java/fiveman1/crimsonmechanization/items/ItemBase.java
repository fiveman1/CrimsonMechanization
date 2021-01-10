package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        RegistryHandler.initItem(this, name);
    }

    public ItemBase(String registryName, String unlocalizedName) {
        RegistryHandler.initItem(this, registryName, unlocalizedName);
    }
}
