package fiveman1.crimsonmechanization.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IInitializeable {

    default void initBlock(RegistryEvent.Register<Block> event) {

    }

    default void initItem(RegistryEvent.Register<Item> event) {
    }

    @SideOnly(Side.CLIENT)
    default void initModel(ModelRegistryEvent event) {

    }
}
