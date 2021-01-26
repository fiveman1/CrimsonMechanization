package fiveman1.crimsonmechanization.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractBaseContainer extends Container {

    protected IItemHandler playerInventory;
    protected final List<IContainerListener> listeners = ObfuscationReflectionHelper.getPrivateValue(Container.class, this, "listeners");

    public AbstractBaseContainer(int windowID, PlayerInventory playerInventory, ContainerType<?> containerType) {
        super(containerType, windowID);
        this.playerInventory = new InvWrapper(playerInventory);
    }

    protected void addInventorySlots(int xOffset, int yOffset) {
        int x;
        int y;
        //hotbar
        y = yOffset + 58;
        for (int col = 0; col < 9; col++) {
            x = xOffset + (col * 18);
            addSlot(new SlotItemHandler(playerInventory, col, x, y));
        }
        //inventory
        for(int row = 0; row < 3; row++) {
            y = yOffset + (row * 18);
            for (int col = 0; col < 9; col++) {
                x = xOffset + (col * 18);
                addSlot(new SlotItemHandler(playerInventory, (row * 9) + col + 9, x, y));
            }
        }
    }
}
