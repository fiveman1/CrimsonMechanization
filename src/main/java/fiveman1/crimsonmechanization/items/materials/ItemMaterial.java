package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ItemMaterial extends Item {

    private final String oreDictTitle;
    protected static final ArrayList<EnumMaterial> ignoreList = new ArrayList<>();
    private static final ArrayList<EnumMaterial> values = new ArrayList<>();


    public ItemMaterial(String name) {
        // registry name ex: dust_material
        // unlocalized name ex (used for oredict): dustMaterial
        // https://mcforge.readthedocs.io/en/1.12.x/utilities/oredictionary/#oredictionary-name-convention (follow this)
        setRegistryName(CrimsonMechanization.MODID, name);
        setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.MATERIALS);
        oreDictTitle = name;
        for (EnumMaterial enumMaterial : EnumMaterial.values) {
            values.add(enumMaterial);
        }
    }

    protected void addMaterialsToIgnoreList(EnumMaterial[] enumMaterials) {
        for (EnumMaterial enumMaterial : enumMaterials) {
            ignoreList.add(enumMaterial);
        }
    }

    // make sure to call this after you use addMaterialsToIgnoreList
    protected void rebuildValues() {
        values.clear();
        for (EnumMaterial enumMaterial : EnumMaterial.values) {
            if (!ignoreList.contains(enumMaterial)) {
                values.add(enumMaterial);
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + EnumMaterial.byMetadata(i).getUnlocalizedName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (EnumMaterial enumMaterial : values) {
                items.add(new ItemStack(this, 1, enumMaterial.getMetadata()));
            }
        }

    }

    public void registerOreDictEntries() {
        for (EnumMaterial enumMaterial : values) {
            OreDictionary.registerOre(oreDictTitle + enumMaterial.getUnlocalizedName(), new ItemStack(this, 1, enumMaterial.getMetadata()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModels() {
        for (EnumMaterial enumMaterial : values) {
            ModelLoader.setCustomModelResourceLocation(this, enumMaterial.getMetadata() , new ModelResourceLocation(getRegistryName() + enumMaterial.getName(), "inventory"));
        }
    }
}
