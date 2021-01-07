package fiveman1.crimsonmechanization.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity {

    public String name;

    public TileEntityBase(String name) {
        this.name = name;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64;
    }
}
