package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.ItemPickaxe;

public class ItemPickaxeBase extends ItemPickaxe {

    public ItemPickaxeBase(ToolMaterial material, String name) {
        super(material);
        RegistryHandler.initItem(this, name);
    }
}
