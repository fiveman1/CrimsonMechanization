package fiveman1.crimsonmechanization.items.armor;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

import javax.annotation.Nullable;

public class ItemArmorBase extends ItemArmor implements IInitializeable {

    protected String armor_material;

    public ItemArmorBase(String name, EntityEquipmentSlot equipmentSlotIn) {
        super(ArmorMaterial.DIAMOND, 1, equipmentSlotIn);
        RegistryHandler.initItem(this, name);
        setMaxStackSize(1);

        int index = name.indexOf("_");
        armor_material = name.substring(0, index);
        RegistryHandler.INITIALIZEABLES.add(this);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == EntityEquipmentSlot.LEGS){
            return CrimsonMechanization.MODID + ":textures/models/armor/" + armor_material + "_model_layer_2.png";
        }
        return CrimsonMechanization.MODID + ":textures/models/armor/" + armor_material + "_model_layer_1.png";
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0 , new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
