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
    
    public ChoiceButton(ChoiceActionListener listener) {
        this(listener, "choice1-button");
    }
    
    public ChoiceButton(ChoiceActionListener listener, String imageKey) {
        super(imageKey);
    }
    
    public void checkHover(Point p, int panelWidth, int panelHeight) {
        choiceButtonBounds.setBounds(0, 0, panelWidth, panelHeight);
        isChoiceButtonHover = showChoiceButton && isPointOnImage(p, panelWidth, panelHeight);
    }
    
    public boolean checkClick(Point p, int panelWidth, int panelHeight) {
        return showChoiceButton && isPointOnImage(p, panelWidth, panelHeight);
    }
    
    private boolean isPointOnImage(Point p, int panelWidth, int panelHeight) {
        if (image == null || !choiceButtonBounds.contains(p)) return false;
        
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        int imageX = (p.x * originalWidth) / panelWidth;
        int imageY = (p.y * originalHeight) / panelHeight;
        
        if (imageX < 0 || imageY < 0 || imageX >= originalWidth || imageY >= originalHeight) return false;
        
        int pixel = image.getRGB(imageX, imageY);
        int alpha = (pixel >> 24) & 0xff; 
        
        return alpha > 0;
    }
    
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        if (!showChoiceButton || image == null) return;
        
        int x = 0, y = 0, width = panelWidth, height = panelHeight;
        
        if (isChoiceButtonHover) {
            int hoverWidth = (int)(width * 1.005);
            int hoverHeight = (int)(height * 1.005);
            int hoverX = x - (hoverWidth - width) / 2;
            int hoverY = y - (hoverHeight - height) / 2;
            super.draw(g, hoverX, hoverY, hoverWidth, hoverHeight);
        } else {
            super.draw(g, x, y, width, height);
        }
    }
    
    public void show(boolean show) {
        this.showChoiceButton = show;
        if (!show) this.isChoiceButtonHover = false;
    }
    
    public boolean isShown() {
        return showChoiceButton;
    }
    
    public boolean isHovering() {
        return isChoiceButtonHover;
    }
}
