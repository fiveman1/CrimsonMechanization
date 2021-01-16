package fiveman1.crimsonmechanization.blocks;

public class ModBlocks {

    public static BlockBart blockBart;
    public static BlockCompactor blockCompactor;
    public static BlockCrimsonFurnace blockCrimsonFurnace;
    public static BlockCrimson blockCrimson;
    public static BlockAlloyer blockAlloyer;
    public static BlockCrusher blockCrusher;

    public static void init() {
        blockBart = new BlockBart("bart_block");
        blockCompactor = new BlockCompactor("crimson_compactor");
        blockCrimsonFurnace = new BlockCrimsonFurnace("crimson_furnace");
        blockCrimson = new BlockCrimson("crimson_block");
        blockAlloyer = new BlockAlloyer("crimson_alloyer");
        blockCrusher = new BlockCrusher("crimson_crusher");
    }
}
