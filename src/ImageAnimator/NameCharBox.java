package ImageAnimator;

import text.FontManager;
import text.TypingAnimator;
import java.awt.*;

public class NameCharBox extends DrawableAsset {
    private String characterName;
    private TypingAnimator nameAnimator;
    private boolean isVisible = false;
    
    // POSISI TEKS (tetap fix)
    private static final int TEXT_X = 40;   // 67 + 30 = 97
    private static final int TEXT_Y = 583;  // 862 + 50 = 912
    
    public interface NameAnimationListener {
        void onNameTypingComplete();
        void requestRepaint();
    }
    
    public NameCharBox(NameAnimationListener listener) {
        super("namechar-box");
        System.out.println("✅ NameCharBox constructor called");
        System.out.println("✅ Image loaded: " + (image != null));
        
        this.characterName = "";
        this.nameAnimator = new TypingAnimator(new TypingAnimator.TypingCompleteListener() {
            @Override
            public void onTypingComplete() {
                System.out.println("✅ Name typing complete");
                if (listener != null) listener.onNameTypingComplete();
            }
            
            @Override
            public void requestRepaint() {
                if (listener != null) listener.requestRepaint();
            }
        }, "");
    }
    
    public void show(String characterName) {
        System.out.println("NameCharBox.show() called with: " + characterName);
        this.characterName = characterName;
        this.isVisible = true;
        this.nameAnimator.setDialogText(characterName);
        this.nameAnimator.startTypingEffect();
    }
    
    public void hide() {
        this.isVisible = false;
        this.nameAnimator.skipTyping();
    }
    
    /**
     * Gambar name box FULL-STRETCH dan teks nama di posisi fix
     */
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        if (!isVisible) {
            System.out.println("NameCharBox not visible");
            return;
        }
        if (image == null) {
            System.out.println("NameCharBox image is null");
            return;
        }
        
        System.out.println("Drawing NameCharBox FULL-STRETCH");
        
        // 1. Gambar box name FULL-STRETCH (0,0,width,height)
        g.drawImage(image, 0, 0, panelWidth, panelHeight, null);
        
        // 2. Gambar teks nama di POSISI TETAP
        drawNameText(g);
    }
    
    private void drawNameText(Graphics g) {
        g.setFont(FontManager.get("bold", 40f));
        g.setColor(Color.WHITE);
        
        String displayedName = nameAnimator.getDisplayedText();
        
        g.drawString(displayedName, TEXT_X, TEXT_Y);
        System.out.println("Drawing name: '" + displayedName + "' at fixed position " + TEXT_X + "," + TEXT_Y);
    }
    
    public void skipTyping() {
        nameAnimator.skipTyping();
    }
    
    public boolean isNameTypingComplete() {
        return nameAnimator.isTypingComplete();
    }
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public void changeName(String newName) {
        this.characterName = newName;
        this.nameAnimator.setDialogText(newName);
    }
    
    public void cleanup() {
        nameAnimator.cleanup();
    }
}