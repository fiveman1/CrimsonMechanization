package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.EnumBlockMaterial;
import fiveman1.crimsonmechanization.items.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BlockMaterial extends Block implements IInitializeable {

    protected final ArrayList<EnumBlockMaterial> values = new ArrayList<>();
    protected static final PropertyEnum<EnumBlockMaterial> MATERIAL = PropertyEnum.create("material", EnumBlockMaterial.class);

    private final String oreDictTitle;

    public BlockMaterial(String name) {
        super(Material.ROCK);
        oreDictTitle = name;
        setRegistryName(CrimsonMechanization.MODID, name);
        setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        setCreativeTab(CreativeTabs.MATERIALS);

        Collections.addAll(values, EnumBlockMaterial.values);

        RegistryHandler.INITIALIZEABLES.add(this);

        setDefaultState(blockState.getBaseState().withProperty(MATERIAL, EnumBlockMaterial.CRIMSON));

        setHardness(3.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
        setHarvestLevel("pickaxe", 1, getStateFromMeta(EnumBlockMaterial.COPPER.getMetadata()));
        setHarvestLevel("pickaxe", 1, getStateFromMeta(EnumBlockMaterial.TIN.getMetadata()));
        setHarvestLevel("pickaxe", 1, getStateFromMeta(EnumBlockMaterial.CRIMSON.getMetadata()));
        setHarvestLevel("pickaxe", 1, getStateFromMeta(EnumBlockMaterial.CRIMSON_IRON.getMetadata()));
        setHarvestLevel("pickaxe", 2, getStateFromMeta(EnumBlockMaterial.CRIMSON_STEEL.getMetadata()));
    }

    protected void removeMaterials(EnumBlockMaterial... enumBlockMaterials) {
        values.removeAll(Arrays.asList(enumBlockMaterials));
    }

    protected void addMaterials(EnumBlockMaterial... enumBlockMaterials) {
        values.addAll(Arrays.asList(enumBlockMaterials));
    }

    protected void clearMaterials() {
        values.clear();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumBlockMaterial enumBlockMaterial : values) {
            items.add(new ItemStack(this, 1, enumBlockMaterial.getMetadata()));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MATERIAL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(MATERIAL, EnumBlockMaterial.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MATERIAL).getMetadata();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(MATERIAL).getMetadata();
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName() + EnumBlockMaterial.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
    }

    @Override
    public void initBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlockMaterial(this).setRegistryName(getRegistryName()));
        for (EnumBlockMaterial enumBlockMaterial : values) {
            OreDictionary.registerOre(oreDictTitle + enumBlockMaterial.getUnlocalizedName(), new ItemStack(this, 1, enumBlockMaterial.getMetadata()));
        }
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        for (EnumBlockMaterial enumBlockMaterial : values) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), enumBlockMaterial.getMetadata(),
                    new ModelResourceLocation(getRegistryName(), "material=" + enumBlockMaterial.getName()));
        }
    }

    static class ItemBlockMaterial extends ItemBlock {

        private final BlockMaterial blockMaterialBlock;

        public ItemBlockMaterial(BlockMaterial block) {
            super(block);
            this.blockMaterialBlock = block;
            setHasSubtypes(true);
            setMaxDamage(0);
            setNoRepair();
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return blockMaterialBlock.getUnlocalizedName(stack);
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }
}
