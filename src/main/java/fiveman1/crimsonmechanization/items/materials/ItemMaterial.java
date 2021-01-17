package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.items.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ItemMaterial extends Item implements IInitializeable {

    protected final ArrayList<EnumMaterial> values = new ArrayList<>();

    private final String oreDictTitle;

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
        Collections.addAll(values, EnumMaterial.values);
        RegistryHandler.INITIALIZEABLES.add(this);
        //RegistryHandler.ITEM_MATERIALS.add(this);
    }

    protected void removeMaterials(EnumMaterial... enumMaterials) {
        values.removeAll(Arrays.asList(enumMaterials));
    }

    protected void addMaterials(EnumMaterial... enumMaterials) {
        values.addAll(Arrays.asList(enumMaterials));
    }

    protected void clearMaterials() {
        values.clear();
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

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
        for (EnumMaterial enumMaterial : values) {
            OreDictionary.registerOre(oreDictTitle + enumMaterial.getUnlocalizedName(), new ItemStack(this, 1, enumMaterial.getMetadata()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel(ModelRegistryEvent event) {
        for (EnumMaterial enumMaterial : values) {
            ModelLoader.setCustomModelResourceLocation(this, enumMaterial.getMetadata() , new ModelResourceLocation(getRegistryName() + enumMaterial.getName(), "inventory"));
        }
    }
}
