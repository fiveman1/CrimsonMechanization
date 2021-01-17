package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.items.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class BlockBase extends Block implements IInitializeable {

    private final String NAME;

    public BlockBase(String name) {
        this(name, Material.ROCK);
    }

    public BlockBase(String name, Material material) {
        super(material);
        RegistryHandler.initBlock(this, name);
        RegistryHandler.INITIALIZEABLES.add(this);

        setHarvestLevel("pickaxe", 0);
        setHardness(3.0f);
        setResistance(5.0f);

        NAME = name;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public void initBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(this);
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(this).setRegistryName(getRegistryName()));
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
