package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.ItemAxe;

public class ItemAxeBase extends ItemAxe {
    protected ItemAxeBase(ToolMaterial material, String name) {
        super(material);
        RegistryHandler.initItem(this, name);
    }
}
