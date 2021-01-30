package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CrusherRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.FurnaceRecipeManager;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeSerializerRegistration {

    public static BaseRecipeSerializer<CompactorRecipe> COMPACTOR_SERIALIZER;
    public static BaseRecipeSerializer<CrusherRecipe> CRUSHER_SERIALIZER;
    public static BaseRecipeSerializer<FurnaceRecipe> FURNACE_SERIALIZER;

    public static void register(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        COMPACTOR_SERIALIZER = new BaseRecipeSerializer<>(CompactorRecipe::new, CompactorRecipeManager.DEFAULT_ENERGY, RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        CRUSHER_SERIALIZER = new BaseRecipeSerializer<>(CrusherRecipe::new, CrusherRecipeManager.DEFAULT_ENERGY, RecipeTypeRegistration.CRUSHER_RECIPE_ID);
        FURNACE_SERIALIZER = new BaseRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.DEFAULT_ENERGY, RecipeTypeRegistration.FURNACE_RECIPE_ID);
        registry.registerAll(COMPACTOR_SERIALIZER, CRUSHER_SERIALIZER, FURNACE_SERIALIZER);
    }
}
