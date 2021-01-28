package fiveman1.crimsonmechanization.inventory.container.providers;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class CustomContainerProvider<T extends Container> implements INamedContainerProvider {

    private final String displayName;
    private final IContainerFactory<T> factory;

    public CustomContainerProvider(String displayName, IContainerFactory<T> factory) {
        this.displayName = displayName;
        this.factory = factory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + CrimsonMechanization.MODID + "." + displayName);
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity player) {
        return factory.create(windowID, playerInventory);
    }

    public interface IContainerFactory<T> {
        T create(int windowID, PlayerInventory playerInventory);
    }
}
