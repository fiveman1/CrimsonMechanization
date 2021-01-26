package fiveman1.crimsonmechanization.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/*
https://github.com/KingLemming/1.15/blob/a67152e26d4213cb4f055f631b9db3abe698a308/ThermalCore/src/main/java/cofh/thermal/core/util/recipes/ThermalRecipeSerializer.java

Derived from ThermalCore
 */

public class BaseRecipeSerializer<T extends BaseRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    protected final IFactory<T> factory;
    protected final int defaultEnergy;

    public BaseRecipeSerializer(IFactory<T> factory, int defaultEnergy, ResourceLocation registryName) {
        this.factory = factory;
        this.defaultEnergy = defaultEnergy;
        setRegistryName(registryName);
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
        int energy = defaultEnergy;

        if (json.has("energy")) {
            energy = json.get("energy").getAsInt();
        }

        List<Ingredient> inputItems = new ArrayList<>();
        JsonArray jsonInputs = RecipeParser.getInputs(json);
        if (jsonInputs == null) {
            throw new JsonSyntaxException("Recipe must have inputs array");
        }
        RecipeParser.parseInputs(inputItems, jsonInputs);

        List<ItemStack> outputItems = new ArrayList<>();
        List<Integer> outputChances = new ArrayList<>();
        JsonArray jsonOutputs = RecipeParser.getOutputs(json);
        if (jsonOutputs == null) {
            throw new JsonSyntaxException("Recipe must have outputs array");
        }
        RecipeParser.parseOutputs(outputItems, outputChances, jsonOutputs);

        return factory.create(recipeId, inputItems, outputItems, outputChances, energy);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        int energy = buffer.readInt();

        int inputItemsSize = buffer.readVarInt();
        List<Ingredient> inputItems = new ArrayList<>();
        for (int i = 0; i < inputItemsSize; i++) {
            inputItems.add(Ingredient.read(buffer));
        }

        int outputItemsSize = buffer.readVarInt();
        List<ItemStack> outputItems = new ArrayList<>();
        for (int i = 0; i < outputItemsSize; i++) {
            outputItems.add(buffer.readItemStack());
        }

        List<Integer> outputChances = new ArrayList<>();
        for (int i = 0; i < outputItemsSize; i++) {
            outputChances.add(buffer.readVarInt());
        }

        return factory.create(recipeId, inputItems, outputItems, outputChances, energy);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeInt(recipe.energy);

        buffer.writeVarInt(recipe.inputItems.size());
        for (Ingredient ingredient : recipe.inputItems) {
            ingredient.write(buffer);
        }

        buffer.writeVarInt(recipe.outputItems.size());
        for (ItemStack stack : recipe.outputItems) {
            buffer.writeItemStack(stack);
        }

        for (Integer chance : recipe.outputChances) {
            buffer.writeVarInt(chance);
        }
    }

    public interface IFactory<T extends BaseRecipe> {
        T create(ResourceLocation recipeID, List<Ingredient> inputItems, List<ItemStack> outputItems, List<Integer> outputChances, int energy);
    }
}
