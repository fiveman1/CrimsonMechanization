package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.materials.EnumMaterial;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemPlate extends Item {

    private final String oreDictTitle;

    public ItemPlate(String registryName, String unlocalizedName) {
        setRegistryName(CrimsonMechanization.MODID, registryName);
        setUnlocalizedName(CrimsonMechanization.MODID + "." + unlocalizedName);
        setHasSubtypes(true);
        setMaxDamage(0);
        oreDictTitle = unlocalizedName;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + EnumMaterial.byMetadata(i).getUnlocalizedName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < EnumMaterial.getLength(); i++) {
                items.add(new ItemStack(this, 1, EnumMaterial.byMetadata(i).getMetadata()));
            }
        }

    }

    public void registerOreDictEntries() {
        for (int i = 0; i < EnumMaterial.getLength(); i++) {
            EnumMaterial material = EnumMaterial.byMetadata(i);
            CrimsonMechanization.logger.warn(oreDictTitle + material.getUnlocalizedName());
            OreDictionary.registerOre(oreDictTitle + material.getUnlocalizedName(), new ItemStack(this, 1, material.getMetadata()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModels() {
        for (int i = 0; i < EnumMaterial.getLength(); i++) {
            ModelLoader.setCustomModelResourceLocation(this, i , new ModelResourceLocation(getRegistryName() + EnumMaterial.byMetadata(i).getName(), "inventory"));
        }
    }
}
