package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.EnumToolMaterial;
import fiveman1.crimsonmechanization.util.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemAxeBase extends ItemAxe implements IInitializeable {
    private String name;

    public ItemAxeBase(EnumToolMaterial material){
        super(ToolMaterial.IRON);
        setMaxDamage(material.getMaxUses());
        setMaxStackSize(1);
        setHarvestLevel("axe", material.getHarvestLevel());
        efficiency = material.getEfficiency();
        attackDamage = material.getDamageVsEntity();


        RegistryHandler.INITIALIZEABLES.add(this);
        setRegistryName(CrimsonMechanization.MODID, "axe_" + material.getName());
        setUnlocalizedName(CrimsonMechanization.MODID + ".axe" + material.getUnlocalizedName());

        name = material.getName();
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(CrimsonMechanization.MODID + ":axe",
                "material=" + name));
    }
}
