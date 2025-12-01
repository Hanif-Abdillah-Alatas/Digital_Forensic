package buttons;

import ImageAnimator.DrawableAsset;
import java.awt.*;

public class ChoiceButton extends DrawableAsset {
    private boolean showChoiceButton = false;
    private boolean isChoiceButtonHover = false;
    private Rectangle choiceButtonBounds = new Rectangle();
    
    public interface ChoiceActionListener {
        void onChoiceClicked();
    }
    
    private ChoiceActionListener listener;
    
    // Konstruktor dengan imageKey default
    public ChoiceButton(ChoiceActionListener listener) {
        super("choice1-button");
        this.listener = listener;
    }
    
    // Konstruktor dengan imageKey spesifik
    public ChoiceButton(ChoiceActionListener listener, String imageKey) {
        super(imageKey);
        this.listener = listener;
    }
    
    // ========== DETEKSI DAN INTERAKSI (Full-Stretch) ==========
    
    public void checkHover(Point p, int panelWidth, int panelHeight) {
        boolean wasHovered = isChoiceButtonHover;
        
        // Perbarui bounds agar sesuai dengan ukuran panel saat ini (Full-Stretch)
        choiceButtonBounds.setBounds(0, 0, panelWidth, panelHeight);
        
        if (showChoiceButton && isPointOnImage(p, panelWidth, panelHeight)) {
            isChoiceButtonHover = true;
        } else {
            isChoiceButtonHover = false;
        }
    }
    
    public boolean checkClick(Point p, int panelWidth, int panelHeight) {
        // Mengembalikan TRUE jika tombol aktif dan kursor berada di atas pixel gambar
        if (showChoiceButton && isPointOnImage(p, panelWidth, panelHeight)) {
            return true; // Kena klik!
        }
        return false; // Tidak kena
    }
    
    /**
     * PIXEL-PERFECT DETECTION METHOD (Full-Stretch)
     */
    private boolean isPointOnImage(Point p, int panelWidth, int panelHeight) {
        if (image == null || !choiceButtonBounds.contains(p)) {
            return false;
        }
        
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        // Konversi koordinat panel ke koordinat gambar asli
        int imageX = (p.x * originalWidth) / panelWidth;
        int imageY = (p.y * originalHeight) / panelHeight;
        
        if (imageX < 0 || imageY < 0 || imageX >= originalWidth || imageY >= originalHeight) {
             return false;
        }
        
        int pixel = image.getRGB(imageX, imageY);
        int alpha = (pixel >> 24) & 0xff; 
        
        return alpha > 0;
    }
    
    // ========== DRAWING (Full-Stretch) ==========
    
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        if (!showChoiceButton || image == null) {
            return;
        }
        
        int x = 0;
        int y = 0;
        int width = panelWidth;
        int height = panelHeight;
            
        if (isChoiceButtonHover) {
            // Efek hover
            int hoverWidth = (int)(width * 1.005);
            int hoverHeight = (int)(height * 1.005);
            int hoverX = x - (hoverWidth - width) / 2;
            int hoverY = y - (hoverHeight - height) / 2;
            super.draw(g, hoverX, hoverY, hoverWidth, hoverHeight);
        } else {
            // Gambar normal (Full-Stretch)
            super.draw(g, x, y, width, height);
        }
    }
    
    // ========== CONTROLS ==========
    
    public void show(boolean show) {
        this.showChoiceButton = show;
        if (!show) {
            this.isChoiceButtonHover = false;
        }
    }
    
    public boolean isShown() {
        return showChoiceButton;
    }
    
    public boolean isHovering() {
        return isChoiceButtonHover;
    }
}