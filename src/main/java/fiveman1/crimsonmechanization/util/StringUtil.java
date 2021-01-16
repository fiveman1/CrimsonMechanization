package fiveman1.crimsonmechanization.util;

import net.minecraft.client.gui.FontRenderer;

public class StringUtil {

    public static void drawRightSidedText(String text, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text), y, ColorUtil.GREY);
    }

    public static void drawCenteredText(String text, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, ColorUtil.GREY);
    }
}
