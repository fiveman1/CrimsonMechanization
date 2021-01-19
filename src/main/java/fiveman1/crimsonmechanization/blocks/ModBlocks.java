package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.blocks.materials.BlockMaterialBlock;

public class ModBlocks {

    public static BlockBart blockBart;
    public static BlockCompactor blockCompactor;
    public static BlockCrimsonFurnace blockCrimsonFurnace;
    public static BlockAlloyer blockAlloyer;
    public static BlockCrusher blockCrusher;
    public static BlockMaterialBlock blockMaterialBlock;

    public static void init() {
        blockBart = new BlockBart("bart_block");
        blockCompactor = new BlockCompactor("compactor");
        blockCrimsonFurnace = new BlockCrimsonFurnace("furnace");
        blockAlloyer = new BlockAlloyer("alloyer");
        blockCrusher = new BlockCrusher("crusher");
        blockMaterialBlock = new BlockMaterialBlock("block");
    }
}
