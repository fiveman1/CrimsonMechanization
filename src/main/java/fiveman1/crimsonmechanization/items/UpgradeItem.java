package fiveman1.crimsonmechanization.items;

import com.google.common.collect.Lists;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeItem extends Item {

    private static final String SPEED_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.SPEED.name;
    private static final String EFFICIENCY_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.EFFICIENCY.name;
    private static final String LUCK_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.LUCK.name;

    private static final List<ITextComponent> SPEED_TOOL_TIP = Lists.newArrayList(
            new TranslationTextComponent(SPEED_KEY + "1").mergeStyle(TextFormatting.RED)
                    .append(new TranslationTextComponent(SPEED_KEY + "2").mergeStyle(TextFormatting.GRAY)),
            new TranslationTextComponent(SPEED_KEY + "3").mergeStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent(SPEED_KEY + "4").mergeStyle(TextFormatting.GRAY)));

    private static final List<ITextComponent> EFFICIENCY_TOOL_TIP = Lists.newArrayList(
            new TranslationTextComponent(EFFICIENCY_KEY + "1").mergeStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent(EFFICIENCY_KEY + "2").mergeStyle(TextFormatting.GRAY)));

    private static final List<ITextComponent> LUCK_TOOL_TIP = Lists.newArrayList(
            new TranslationTextComponent(LUCK_KEY + "1").mergeStyle(TextFormatting.GREEN)
                    .append(new TranslationTextComponent(LUCK_KEY + "2").mergeStyle(TextFormatting.GRAY)));

    protected final Type upgradeType;

    public UpgradeItem(Type upgradeType) {
        super(new Item.Properties().group(ItemGroup.MISC));
        setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, "upgrade_" + upgradeType.name));
        this.upgradeType = upgradeType;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        Type type = ((UpgradeItem) stack.getItem()).upgradeType;
        if (type == Type.SPEED) {
            tooltip.addAll(SPEED_TOOL_TIP);
        } else if (type == Type.EFFICIENCY) {
            tooltip.addAll(EFFICIENCY_TOOL_TIP);
        } else if (type == Type.LUCK) {
            tooltip.addAll(LUCK_TOOL_TIP);
        }
    }

    public enum Type {
        SPEED("speed"),
        EFFICIENCY("efficiency"),
        LUCK("luck")
        ;

        public final String name;

        Type(String name) {
            this.name = name;
        }
    }
}
