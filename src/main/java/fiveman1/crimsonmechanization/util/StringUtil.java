package fiveman1.crimsonmechanization.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    public static void drawRightSidedText(String text, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text), y, ColorUtil.GREY);
    }

    public static void drawCenteredText(String text, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, ColorUtil.GREY);
    }

    public static String convertTooltip(String translateKey, Object... params) {
        String translated = I18n.format(translateKey, params);
        return COMPILE.matcher(translated).replaceAll("\u00a7");
    }
}
