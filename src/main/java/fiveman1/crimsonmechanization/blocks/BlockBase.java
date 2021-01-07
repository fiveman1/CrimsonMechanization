package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase extends Block {

    private final String NAME;

    public BlockBase(String name) {
        super(Material.ROCK);
        setRegistryName(CrimsonMechanization.MODID, name);
        setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        RegistryHandler.addBlockToRegistry(this);

        setHarvestLevel("pickaxe", 0);
        setHardness(3.0f);
        setResistance(5.0f);

        NAME = name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public String getName() {
        return NAME;
    }
}
