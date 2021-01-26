package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeSerializerRegistration {

    public static BaseRecipeSerializer<CompactorRecipe> compactorRecipeSerializer;

    public static void register(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        compactorRecipeSerializer = new BaseRecipeSerializer<>(CompactorRecipe::new, CompactorRecipeManager.DEFAULT_ENERGY, RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registry.register(compactorRecipeSerializer);
    }
}
