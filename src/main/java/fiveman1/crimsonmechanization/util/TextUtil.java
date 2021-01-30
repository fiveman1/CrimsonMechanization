package fiveman1.crimsonmechanization.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.*;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static void drawCenteredText(MatrixStack matrixStack, String text, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(matrixStack, text, x - fontRenderer.getStringWidth(text) / 2.0f, y, color);
    }
    
    public static ToolTipBuilder getToolTipBuilder() {
        return new ToolTipBuilder();
    }
    
    public static class ToolTipBuilder {
        
        private List<IFormattableTextComponent> components = new ArrayList<>();
        private final List<IFormattableTextComponent> lines = new ArrayList<>();
        
        private ToolTipBuilder() {}
        
        public ToolTipBuilder add() {
            if (components.size() > 0) {
                IFormattableTextComponent line = components.get(0);
                for (int i = 1; i < components.size(); i++) {
                    line = line.append(components.get(i));
                }
                lines.add(line);
                components = new ArrayList<>();
            }
            return this;
        }
        
        public ToolTipBuilder append(String translationKey, TextFormatting formatting) {
            components.add(new TranslationTextComponent(translationKey).mergeStyle(formatting));
            return this;
        }
        
        public ToolTipBuilder append(String translationKey) {
            components.add(new TranslationTextComponent(translationKey));
            return this;
        }

        public ToolTipBuilder appendString(String msg, TextFormatting formatting) {
            components.add(new StringTextComponent(msg).mergeStyle(formatting));
            return this;
        }
        
        public ToolTipBuilder appendString(String msg) {
            components.add(new StringTextComponent(msg));
            return this;
        }
        
        public List<IFormattableTextComponent> build() {
            return lines;
        }
    }
}
