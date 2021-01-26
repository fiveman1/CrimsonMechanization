package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/*
https://github.com/KingLemming/1.15/blob/a67152e26d4213cb4f055f631b9db3abe698a308/CoFHCore/src/main/java/cofh/core/util/recipes/SerializableRecipeType.java#L7

Derived from ThermalCore
Actually this is almost identical but it's really simple so /shrug

KingLemming :fist:
 */

public class BaseRecipeType<T extends BaseRecipe> implements IRecipeType<T> {

    private final ResourceLocation registryName;

    public BaseRecipeType(ResourceLocation location) {
        this.registryName = location;
    }

    @Override
    public String toString() {
        return registryName.toString();
    }

    public void register() {
        Registry.register(Registry.RECIPE_TYPE, registryName, this);
    }
}
