package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.item.ItemSpade;

public class ItemSpadeBase extends ItemSpade {
    public ItemSpadeBase(ToolMaterial material, String name) {
        super(material);
        RegistryHandler.initItem(this, name);
    }
}
