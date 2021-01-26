package fiveman1.crimsonmechanization.recipe;

import com.google.gson.*;
import fiveman1.crimsonmechanization.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeParser {

    private static final String[] inputNames = {"input", "inputs", "ingredient", "ingredients"};
    private static final String[] outputNames = {"output", "outputs", "result", "results"};

    @Nullable
    public static JsonArray getInputs(JsonObject jsonObject) {
        for (String name : inputNames) {
            if (jsonObject.has(name)) {
                return jsonObject.getAsJsonArray(name);
            }
        }
        return null;
    }

    @Nullable
    public static JsonArray getOutputs(JsonObject jsonObject) {
        for (String name : outputNames) {
            if (jsonObject.has(name)) {
                return jsonObject.getAsJsonArray(name);
            }
        }
        return null;
    }

    public static void parseInputs(List<Ingredient> ingredients, JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject()) {
                throw new JsonSyntaxException("Each input should be a json object");
            }
            ingredients.add(parseIngredient(element.getAsJsonObject()));
        }
    }

    public static Ingredient parseIngredient(JsonObject jsonObject) {
        Ingredient ingredient = Ingredient.deserialize(jsonObject);
        int count = 1;
        if (jsonObject.has("count")) {
            count = jsonObject.get("count").getAsInt();
        }
        if (count > 1) {
            for (ItemStack stack : ingredient.getMatchingStacks()) {
                stack.setCount(count);
            }
        }
        return ingredient;
    }

    public static void parseOutputs(List<ItemStack> outputs, List<Integer> outputChances, JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject()) {
                throw new JsonSyntaxException("Each output should be a json object");
            }
            outputs.add(parseItemStack(element.getAsJsonObject(), outputChances));
        }

    }

    public static ItemStack parseItemStack(JsonObject jsonObject, List<Integer> outputChances) {
        Item item;
        if (jsonObject.has("item")) {
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(jsonObject.get("item").getAsString()));
        } else if (jsonObject.has("tag")) {
            ITag<Item> tag = ItemTags.getCollection().getTagByID(new ResourceLocation(jsonObject.get("tag").getAsString()));
            item = TagUtil.getModItemFromTag(tag, false);
        } else {
            throw new JsonSyntaxException("Outputs need to have a specified item or tag");
        }
        if (item == null || item == Items.AIR) {
            throw new JsonParseException("Output has invalid item");
        }
        ItemStack stack = new ItemStack(item);
        int count = 1;
        if (jsonObject.has("count")) {
            count = jsonObject.get("count").getAsInt();
        }
        if (count > 1) {
            stack.setCount(count);
        }
        if (jsonObject.has("chance")) {
            outputChances.add(jsonObject.get("chance").getAsInt());
        } else {
            outputChances.add(100);
        }
        return stack;
    }
}
