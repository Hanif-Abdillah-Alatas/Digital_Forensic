// Contoh di package ImageAnimator
package ImageAnimator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Kelas dasar untuk semua aset yang memiliki gambar dan dapat digambar.
 */
public class DrawableAsset {
    protected BufferedImage image;
    
    // Konstruktor harus mengambil kunci gambar
    public DrawableAsset(String imageKey) {
        this.image = ImageManager.get(imageKey);
    }
    
    /**
     * Menggambar aset pada koordinat dan dimensi yang ditentukan.
     */
    public void draw(Graphics g, int x, int y, int width, int height) {
        if (image != null) {
            // Gunakan 'null' atau 'this' sebagai ImageObserver
            g.drawImage(image, x, y, width, height, null);
        }
    }
    
    public BufferedImage getImage() {
        return image;
    }
}