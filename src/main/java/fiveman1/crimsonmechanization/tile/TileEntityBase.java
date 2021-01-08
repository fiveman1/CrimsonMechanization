package fiveman1.crimsonmechanization.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {

    public String name;

    public TileEntityBase(String name) {
        this.name = name;
    }

    // YOU MUST ADD THE EMPTY CONSTRUCTOR TO REGISTER TILE ENTITIES
    public TileEntityBase() {
        super();
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64;
    }
}
