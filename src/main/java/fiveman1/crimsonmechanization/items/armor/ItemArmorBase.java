package fiveman1.crimsonmechanization.items.armor;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemArmorBase extends ItemArmor {

    public ItemArmorBase(String name, EntityEquipmentSlot equipmentSlotIn) {
        super(ArmorMaterial.DIAMOND, 1, equipmentSlotIn);
        RegistryHandler.initItem(this, name);
        setMaxStackSize(1);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == EntityEquipmentSlot.LEGS){
            return CrimsonMechanization.MODID + ":textures/models/armor/crimson_model_layer_2.png";
        }
        return CrimsonMechanization.MODID + ":textures/models/armor/crimson_model_layer_1.png";
    }

}
