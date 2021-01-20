package fiveman1.crimsonmechanization.inventory;

import fiveman1.crimsonmechanization.inventory.container.ContainerMachineUpgrades;
import fiveman1.crimsonmechanization.inventory.gui.*;
import fiveman1.crimsonmechanization.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int UPGRADE_GUI_ID = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (ID == 0) {
            if (te instanceof TileCrimsonFurnace) {
                return ((TileCrimsonFurnace) te).createContainer(player.inventory);
            } else if (te instanceof  TileCompactor) {
                return ((TileCompactor) te).createContainer(player.inventory);
            } else if (te instanceof TileAlloyer) {
                return ((TileAlloyer) te).createContainer(player.inventory);
            } else if (te instanceof TileCrusher) {
                return ((TileCrusher) te).createContainer(player.inventory);
            }
        } else if (te instanceof TileMachine) {
            switch (ID) {
                case UPGRADE_GUI_ID:
                    return new ContainerMachineUpgrades(player.inventory, (TileMachine) te);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (ID == 0) {
            if (te instanceof TileCrimsonFurnace) {
                return new GuiCrimsonFurnace(((TileCrimsonFurnace) te).createContainer(player.inventory), player.inventory);
            } else if (te instanceof TileCompactor) {
                return new GuiCompactor(((TileCompactor) te).createContainer(player.inventory), player.inventory);
            } else if (te instanceof TileAlloyer) {
                return new GuiAlloyer(((TileAlloyer) te).createContainer(player.inventory), player.inventory);
            } else if (te instanceof TileCrusher) {
                return new GuiCrusher(((TileCrusher) te).createContainer(player.inventory), player.inventory);
            }
        }
        else if (te instanceof TileMachine) {
            switch (ID) {
                case UPGRADE_GUI_ID:
                    return new GuiMachineUpgrades(new ContainerMachineUpgrades(player.inventory, (TileMachine) te), player.inventory);
            }
        }
        return null;
    }
}