package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.TextUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeItem extends Item {

    private static final String SPEED_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.SPEED.name;
    private static final String EFFICIENCY_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.EFFICIENCY.name;
    private static final String LUCK_KEY = "info." + CrimsonMechanization.MODID + ".upgrade_" + Type.LUCK.name;

    private static final List<IFormattableTextComponent> SPEED_TOOL_TIP = TextUtil.getToolTipBuilder()
            .append(SPEED_KEY + "1", TextFormatting.RED)
            .appendString(" ")
            .append(SPEED_KEY + "2", TextFormatting.GRAY)
            .add()
            .append(SPEED_KEY + "3", TextFormatting.BLUE)
            .appendString(" ")
            .append(SPEED_KEY + "4", TextFormatting.GRAY)
            .add().build();

    private static final List<IFormattableTextComponent> EFFICIENCY_TOOL_TIP = TextUtil.getToolTipBuilder()
            .append(EFFICIENCY_KEY + "1", TextFormatting.BLUE)
            .appendString(" ")
            .append(EFFICIENCY_KEY + "2", TextFormatting.GRAY)
            .add().build();

    private static final List<IFormattableTextComponent> LUCK_TOOL_TIP = TextUtil.getToolTipBuilder()
            .append(LUCK_KEY + "1", TextFormatting.GREEN)
            .appendString(" ")
            .append(LUCK_KEY + "2", TextFormatting.GRAY)
            .add().build();


    protected final Type upgradeType;

    public UpgradeItem(Type upgradeType) {
        super(new Item.Properties().group(CrimsonMechanization.MOD_GROUP));
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
