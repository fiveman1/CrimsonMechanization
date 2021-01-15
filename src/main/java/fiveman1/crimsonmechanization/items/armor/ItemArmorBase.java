package fiveman1.crimsonmechanization.items.armor;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemArmorBase extends ItemArmor {

    protected String armor_material;

    public ItemArmorBase(String name, EntityEquipmentSlot equipmentSlotIn) {
        super(ArmorMaterial.DIAMOND, 1, equipmentSlotIn);
        RegistryHandler.initItem(this, name);
        setMaxStackSize(1);

        int index = name.indexOf("_");
        armor_material = name.substring(0, index);

    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == EntityEquipmentSlot.LEGS){
            return CrimsonMechanization.MODID + ":textures/models/armor/" + armor_material + "_model_layer_2.png";
        }
        return CrimsonMechanization.MODID + ":textures/models/armor/" + armor_material + "_model_layer_1.png";
    }

}
