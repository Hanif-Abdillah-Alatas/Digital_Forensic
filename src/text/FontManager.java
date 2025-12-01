package text;

import java.awt.*;
import java.util.HashMap;

public class FontManager {
    private static final HashMap<String, Font> fonts = new HashMap<>();

    public static void load() {
        fonts.put("regular", loadFont("/asset/fonts/Nunito-Regular.ttf"));
        fonts.put("bold", loadFont("/asset/fonts/Tektur-Bold.ttf"));
    }

    private static Font loadFont(String path) {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, FontManager.class.getResourceAsStream(path));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            return f;
        } catch (Exception e) {
            System.out.println("Gagal load font: " + path);
            return new Font("Arial", Font.PLAIN, 24);
        }
    }

    public static Font get(String style, float size) {
        Font base = fonts.getOrDefault(style.toLowerCase(), fonts.get("regular"));
        return base.deriveFont(size);
    }
}
