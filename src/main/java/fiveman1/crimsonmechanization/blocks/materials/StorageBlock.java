package fiveman1.crimsonmechanization.blocks.materials;

import fiveman1.crimsonmechanization.enums.BaseMaterial;

public class StorageBlock extends MaterialBlock {
    public StorageBlock(BaseMaterial material) {
        super("block", material);
    }

    @Override
    public String getBlockTagName() {
        return "storage_block";
    }
}
