package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.ItemSword;

public class ItemSwordBase extends ItemSword {
    public ItemSwordBase(ToolMaterial material, String name) {
        super(material);
        RegistryHandler.initItem(this, name);
    }
}
