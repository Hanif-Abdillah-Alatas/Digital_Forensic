package ImageAnimator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawableAsset {
    protected BufferedImage image;
    
    public DrawableAsset(String imageKey) {
        this.image = ImageManager.get(imageKey);
    }
    
    public void draw(Graphics g, int x, int y, int width, int height) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }
    
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        draw(g, 0, 0, panelWidth, panelHeight);
    }
    
    public BufferedImage getImage() {
        return image;
    }
}
