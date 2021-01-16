package fiveman1.crimsonmechanization.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.Random;

public class ItemStackHandlerUtil {

    private static final Random random = new Random();

    public static ItemStack[] getStacks(ItemStackHandler itemStackHandler) {
        int slots = itemStackHandler.getSlots();
        ItemStack[] stacks = new ItemStack[slots];
        for (int i = 0; i < slots; i++) {
            stacks[i] = itemStackHandler.getStackInSlot(i);
        }
        return stacks;
    }

    public static ItemStackHandler getHandlerCopy(ItemStackHandler itemStackHandler) {
        int slots = itemStackHandler.getSlots();
        NonNullList<ItemStack> stacks = NonNullList.create();
        for (int i = 0; i < slots; i++) {
            stacks.add(itemStackHandler.getStackInSlot(i).copy());
        }
        return new ItemStackHandler(stacks);
    }

    public static boolean canProcessOutputs(ItemStackHandler outputHandler, List<ItemStack> outputs) {
        ItemStackHandler outputHandlerCopy = ItemStackHandlerUtil.getHandlerCopy(outputHandler);
        int slots = outputHandlerCopy.getSlots();
        for (ItemStack output : outputs) {
            ItemStack result = output.copy();
            for (int i = 0; i < slots; i++) {
                result = outputHandlerCopy.insertItem(i, result, false);
                if (result.isEmpty()) {
                    break;
                }
            }
            if (!result.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // should be used in conjunction with canProcessOutputs
    public static void processOutputs(ItemStackHandler outputHandler, List<ItemStack> outputs, int[] chances) {
        int slots = outputHandler.getSlots();
        for (int output = 0; output < outputs.size() && random.nextInt(100) < chances[output]; output++) {
            ItemStack result = outputs.get(output).copy();
            for (int slot = 0; slot < slots; slot++) {
                result = outputHandler.insertItem(slot, result, false);
                if (result.isEmpty()) {
                    break;
                }
            }
        }
    }
}
