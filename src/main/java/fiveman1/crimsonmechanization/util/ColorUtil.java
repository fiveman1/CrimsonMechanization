package fiveman1.crimsonmechanization.util;

import java.awt.*;

public class ColorUtil {

    public static final int GREY = 0x404040;

    public static int getRGB(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha).getRGB();
    }
    public static int getRGB(int red, int green, int blue) {
        return getRGB(red, green, blue, 0);
    }
}