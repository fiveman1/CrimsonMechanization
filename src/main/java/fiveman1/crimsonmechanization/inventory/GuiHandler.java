package fiveman1.crimsonmechanization.inventory;

import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.inventory.gui.GuiAlloyer;
import fiveman1.crimsonmechanization.inventory.gui.GuiCompactor;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrimsonFurnace;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrusher;
import fiveman1.crimsonmechanization.tile.TileAlloyer;
import fiveman1.crimsonmechanization.tile.TileCompactor;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrusher;
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
        } else if (te instanceof  TileCompactor) {
            return ((TileCompactor) te).createContainer(player.inventory);
        } else if (te instanceof TileAlloyer) {
            return ((TileAlloyer) te).createContainer(player.inventory);
        } else if (te instanceof TileCrusher) {
            return ((TileCrusher) te).createContainer(player.inventory);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileCrimsonFurnace) {
            return new GuiCrimsonFurnace(((TileCrimsonFurnace) te).createContainer(player.inventory), player.inventory, ModBlocks.blockCrimsonFurnace.getName(), 176, 166);
        } else if (te instanceof TileCompactor) {
            return new GuiCompactor(((TileCompactor) te).createContainer(player.inventory), player.inventory, ModBlocks.blockCompactor.getName(), 176, 166);
        } else if (te instanceof TileAlloyer) {
            return new GuiAlloyer(((TileAlloyer) te).createContainer(player.inventory), player.inventory, ModBlocks.blockAlloyer.getName(), 176, 166);
        } else if (te instanceof TileCrusher) {
            return new GuiCrusher(((TileCrusher) te).createContainer(player.inventory), player.inventory, ModBlocks.blockCrusher.getName(), 176, 166);
        }
        return null;
    }
}