package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;

public class GuiCrimsonFurnace extends GuiBase {

    private TileCrimsonFurnace te;

    public GuiCrimsonFurnace(TileCrimsonFurnace te, ContainerCrimsonFurnace container, String name, int width, int height) {
        super(container, name, width, height);
        this.te = te;
    }
}
