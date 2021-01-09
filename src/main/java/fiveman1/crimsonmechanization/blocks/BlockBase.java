package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

    private final String NAME;

    public BlockBase(String name) {
        this(name, Material.ROCK);
    }

    public BlockBase(String name, Material material) {
        super(material);
        RegistryHandler.initBlock(this, name);

        setHarvestLevel("pickaxe", 0);
        setHardness(3.0f);
        setResistance(5.0f);

        NAME = name;
    }

    public String getName() {
        return NAME;
    }
}
