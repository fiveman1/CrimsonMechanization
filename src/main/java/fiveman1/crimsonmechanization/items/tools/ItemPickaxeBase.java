package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.EnumToolMaterial;
import fiveman1.crimsonmechanization.util.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemPickaxeBase extends ItemPickaxe implements IInitializeable {

    private String name;

    public ItemPickaxeBase(ToolMaterial material, String name) {
        super(material);
        RegistryHandler.initItem(this, name);
        RegistryHandler.INITIALIZEABLES.add(this);
    }

    public ItemPickaxeBase(EnumToolMaterial material){
        super(ToolMaterial.IRON);
        setMaxDamage(material.getMaxUses());
        setMaxStackSize(1);
        efficiency = material.getEfficiency();
        attackDamage = material.getDamageVsEntity();


        RegistryHandler.INITIALIZEABLES.add(this);
        setRegistryName(CrimsonMechanization.MODID, "pickaxe_" + material.getName());
        setUnlocalizedName(CrimsonMechanization.MODID + ".pickaxe" + material.getUnlocalizedName());

        name = material.getName();
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(CrimsonMechanization.MODID + ":pickaxe",
                "material:" + name));
    }
}
