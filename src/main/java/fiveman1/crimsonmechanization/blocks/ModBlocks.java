package fiveman1.crimsonmechanization.blocks;

public class ModBlocks {

    public static BlockBart blockBart;
    public static BlockCrimsonFurnace blockCrimsonFurnace;
    public static BlockCrimson blockCrimson;

    public static void init() {
        blockBart = new BlockBart("bart_block");
        blockCrimsonFurnace = new BlockCrimsonFurnace("crimson_furnace");
        blockCrimson = new BlockCrimson("crimson_block");
    }
}
