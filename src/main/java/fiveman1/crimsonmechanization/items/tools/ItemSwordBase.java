package fiveman1.crimsonmechanization.items.tools;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.EnumToolMaterial;
import fiveman1.crimsonmechanization.util.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemSwordBase extends ItemSword implements IInitializeable {
    private EnumToolMaterial mat;

    public ItemSwordBase(EnumToolMaterial material){
        super(ToolMaterial.IRON);
        setMaxDamage(material.getMaxUses());
        setMaxStackSize(1);

        RegistryHandler.INITIALIZEABLES.add(this);
        setRegistryName(CrimsonMechanization.MODID, "sword_" + material.getName());
        setUnlocalizedName(CrimsonMechanization.MODID + ".sword" + material.getUnlocalizedName());

        mat = material;
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(CrimsonMechanization.MODID + ":sword",
                "material=" + mat.getName()));
    }

    @Override
    public float getAttackDamage() {
        return mat.getDamageVsEntity() + 3.0f;
    }
}
