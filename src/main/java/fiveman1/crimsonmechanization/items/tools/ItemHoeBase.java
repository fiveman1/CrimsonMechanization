package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.ItemHoe;

public class ItemHoeBase extends ItemHoe {
    public ItemHoeBase(ToolMaterial material, String name){
        super(material);
        RegistryHandler.initItem(this, name);
    }
}
