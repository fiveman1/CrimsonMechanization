package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item {

    public ItemBase(String name) {
        setRegistryName(CrimsonMechanization.MODID, name);
        setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        RegistryHandler.addItemToRegistry(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
