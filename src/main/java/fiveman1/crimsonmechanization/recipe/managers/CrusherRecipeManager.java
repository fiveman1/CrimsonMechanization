package fiveman1.crimsonmechanization.recipe.managers;

import com.google.common.collect.Lists;
import fiveman1.crimsonmechanization.recipe.CrusherRecipe;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.util.TagUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class CrusherRecipeManager extends AbstractRecipeManager {

    public static int DEFAULT_ENERGY = 2000;

    private static final CrusherRecipeManager INSTANCE = new CrusherRecipeManager();
    public static CrusherRecipeManager instance() {
        return INSTANCE;
    }

    private CrusherRecipeManager() {}

    private static final Object2ObjectOpenHashMap<String, String> orePairsLookup = new Object2ObjectOpenHashMap<>();
    private static final HashSet<String> ignoreList = new HashSet<>();

    static class OrePair {
        public final String first;
        public final String second;
        public boolean reversible = true;
        OrePair(String first, String second) {
            this.first = first;
            this.second = second;
        }
        OrePair(String first, String second, boolean reversible) {
            this.first = first;
            this.second = second;
            this.reversible = reversible;
        }
    }

    static {
        ArrayList<OrePair> orePairs = new ArrayList<>();
        orePairs.add(new OrePair("iron", "tin"));
        orePairs.add(new OrePair("gold", "copper"));
        orePairs.add(new OrePair("lead", "silver"));
        orePairs.add(new OrePair("nickel", "iron", false));

        for (OrePair orePair : orePairs) {
            orePairsLookup.put(orePair.first, orePair.second);
            if (orePair.reversible) {
                orePairsLookup.put(orePair.second, orePair.first);
            }
        }

        ignoreList.add("coal");
        ignoreList.add("redstone");
        ignoreList.add("lapis");
        ignoreList.add("quartz");
    }

    @Override
    protected void onRefresh(RecipeManager recipeManager) {
        for (CrusherRecipe recipe : recipeManager.getRecipesForType(RecipeTypeRegistration.CRUSHER_RECIPE_TYPE)) {
            addRecipe(recipe.getInputItems(), recipe.getOutputItems(), recipe.getOutputChances(), recipe.getEnergy());
        }
        generateRecipes();
    }

    @Override
    public String getName() {
        return "Crusher Recipes";
    }

    private void generateRecipes() {
        ITagCollection<Item> tagCollection = ItemTags.getCollection();
        Collection<ResourceLocation> registeredTags = tagCollection.getRegisteredTags();

        for (ResourceLocation name : registeredTags) {
            if (name.getNamespace().equals("forge")) {
                if (name.getPath().startsWith("ingots") || name.getPath().startsWith("gems")) {
                    String suffix = TagUtil.getPathMaterial(name.getPath());
                    if (suffix.equals("")) continue;
                    ResourceLocation outputName = new ResourceLocation("forge", "dusts/" + suffix);
                    if (registeredTags.contains(outputName)) {
                        ITag<Item> inputTag = tagCollection.getTagByID(name);
                        List<Ingredient> input = Collections.singletonList(Ingredient.fromTag(inputTag));
                        List<ItemStack> output = Collections.singletonList(new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputName), false)));
                        List<Integer> chances = Collections.singletonList(100);
                        addRecipe(input, output, chances, DEFAULT_ENERGY);
                    }
                } else if (name.getPath().startsWith("ores")) {
                    String suffix = TagUtil.getPathMaterial(name.getPath());
                    if (suffix.equals("") || ignoreList.contains(suffix)) continue;
                    ResourceLocation outputGem = new ResourceLocation("forge", "gems/" + suffix);
                    if (registeredTags.contains(outputGem)) {
                        ITag<Item> inputTag = tagCollection.getTagByID(name);
                        List<Ingredient> input = Collections.singletonList(Ingredient.fromTag(inputTag));
                        ItemStack output = new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputGem), false));
                        output.setCount(2);
                        List<ItemStack> outputList = Collections.singletonList(output);
                        List<Integer> chances = Collections.singletonList(100);
                        addRecipe(input, outputList, chances, DEFAULT_ENERGY * 2);
                    }
                    ResourceLocation outputDust = new ResourceLocation("forge", "dusts/" + suffix);
                    if (registeredTags.contains(outputDust)) {
                        String orePair = orePairsLookup.get(suffix);
                        if (orePair != null) {
                            ResourceLocation outputDustPair = new ResourceLocation("forge", "dusts/" + orePair);
                            if (registeredTags.contains(outputDustPair)) {
                                ITag<Item> inputTag = tagCollection.getTagByID(name);
                                List<Ingredient> input = Collections.singletonList(Ingredient.fromTag(inputTag));
                                ItemStack first = new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputDust), false));
                                first.setCount(2);
                                ItemStack second = new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputDustPair), false));
                                List<ItemStack> output = Lists.newArrayList(first, second);
                                List<Integer> chances = Lists.newArrayList(100, 20);
                                addRecipe(input, output, chances, DEFAULT_ENERGY * 2);
                            }
                        } else {
                            ITag<Item> inputTag = tagCollection.getTagByID(name);
                            List<Ingredient> input = Collections.singletonList(Ingredient.fromTag(inputTag));
                            ItemStack first = new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputDust), false));
                            first.setCount(2);
                            List<ItemStack> output = Collections.singletonList(first);
                            List<Integer> chances = Collections.singletonList(100);
                            addRecipe(input, output, chances, DEFAULT_ENERGY * 2);
                        }
                    }
                }
            }
        }

    }
}
