package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerAlloyer;
import fiveman1.crimsonmechanization.recipe.managers.AlloyerRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;

public class TileAlloyer extends TileMachine {

    public static final int INPUT_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    @Override
    public int getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected IRecipeManager getRecipes() {
        return new AlloyerRecipeManager();
    }

    public TileAlloyer(String name) {
        super(name);
    }

    public TileAlloyer() {}

    public ContainerAlloyer createContainer(InventoryPlayer playerInventory) {
        return new ContainerAlloyer(playerInventory, this, 8, 84);
    }
}

