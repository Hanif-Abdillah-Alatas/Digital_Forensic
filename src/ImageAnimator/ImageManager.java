package ImageAnimator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    public static void load() {
        images.put("opening", loadImage("/asset/img/Opening.png"));
        images.put("dialog-box1", loadImage("/asset/img/Dialog1.png"));
        images.put("dialog-box2", loadImage("/asset/img/Dialog2.png"));
        images.put("background-prolog", loadImage("/asset/img/bgprolog.png"));
        images.put("namechar-box", loadImage("/asset/img/namechar.png"));
        images.put("choice1-button", loadImage("/asset/img/choice1.png"));
        images.put("choice2-button", loadImage("/asset/img/choice2.png"));
        images.put("choice3-button", loadImage("/asset/img/choice3.png"));
        images.put("choice4-button", loadImage("/asset/img/choice4.png"));
        images.put("choice5-button", loadImage("/asset/img/choice5.png"));
        images.put("laptop", loadImage("/asset/img/laptop.png"));
        images.put("cctv", loadImage("/asset/img/cctv.png"));
        images.put("laptop-evidence", loadImage("/asset/img/laptop-evidence.png"));
        images.put("file-evidence", loadImage("/asset/img/file-evidence.png"));
        images.put("image-evidence", loadImage("/asset/img/image-evidence.png"));
        images.put("cctv-evidence", loadImage("/asset/img/cctv-evidence.png"));
        images.put("hanif", loadImage("/asset/img/hanif.png"));
        images.put("nayla", loadImage("/asset/img/nayla.png"));
        images.put("alatas", loadImage("/asset/img/alatas.png"));
        images.put("labib", loadImage("/asset/img/labib.png"));
        images.put("dafa", loadImage("/asset/img/dafa.png"));
        images.put("alatas-dialog", loadImage("/asset/img/alatas-dialog.png"));
        images.put("syauqy-dialog", loadImage("/asset/img/syauqy-dialog.png"));
        images.put("dafa-dialog", loadImage("/asset/img/dafa-dialog.png"));
        images.put("hanif-dialog", loadImage("/asset/img/hanif-dialog.png"));
        images.put("labib-dialog", loadImage("/asset/img/labib-dialog.png"));
        images.put("nayla-dialog", loadImage("/asset/img/nayla-dialog.png"));
        images.put("pelaku-dialog", loadImage("/asset/img/pelaku-dialog.png"));
        images.put("ending-closing", loadImage("/asset/img/ending.png"));
    }

    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageManager.class.getResource(path));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Gagal load gambar: " + path);
            return null;
        }
    }

    public static BufferedImage get(String key) {
        return images.get(key);
    }
}

