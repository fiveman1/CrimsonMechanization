package fiveman1.crimsonmechanization.inventory;

import fiveman1.crimsonmechanization.inventory.gui.GuiCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileCrimsonFurnace) {
            return ((TileCrimsonFurnace) te).createContainer(player.inventory);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileCrimsonFurnace) {
            TileCrimsonFurnace tileCrimsonFurnace = (TileCrimsonFurnace) te;
            return new GuiCrimsonFurnace(tileCrimsonFurnace, tileCrimsonFurnace.createContainer(player.inventory), player.inventory, tileCrimsonFurnace.name, 176, 166);
        }
        return null;
    }
}