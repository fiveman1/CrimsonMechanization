package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.util.ResourceLocation;

public class RecipeTypeRegistration {

    public static final ResourceLocation COMPACTOR_RECIPE_ID = new ResourceLocation(CrimsonMechanization.MODID, "compactor");
    public static final ResourceLocation CRUSHER_RECIPE_ID = new ResourceLocation(CrimsonMechanization.MODID, "crusher");
    public static final ResourceLocation FURNACE_RECIPE_ID = new ResourceLocation(CrimsonMechanization.MODID, "furnace");

    public static final BaseRecipeType<CompactorRecipe> COMPACTOR_RECIPE_TYPE = new BaseRecipeType<>(COMPACTOR_RECIPE_ID);
    public static final BaseRecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = new BaseRecipeType<>(CRUSHER_RECIPE_ID);
    public static final BaseRecipeType<FurnaceRecipe> FURNACE_RECIPE_TYPE = new BaseRecipeType<>(FURNACE_RECIPE_ID);

    public static void register() {
        COMPACTOR_RECIPE_TYPE.register();
        CRUSHER_RECIPE_TYPE.register();
        FURNACE_RECIPE_TYPE.register();
    }
}
