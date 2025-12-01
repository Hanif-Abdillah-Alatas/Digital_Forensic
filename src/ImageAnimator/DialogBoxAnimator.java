package ImageAnimator;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DialogBoxAnimator {
    private BufferedImage currentDialogBox;
    private List<BufferedImage> dialogBoxFrames;
    private Timer animationTimer;
    private int currentFrameIndex = 0;
    
    private static final int ANIMATION_DELAY = 400; 

    public DialogBoxAnimator() {
        dialogBoxFrames = new ArrayList<>();
        loadDialogBoxes();
        setupAnimation();
    }
    
    private void loadDialogBoxes() {
        BufferedImage dialog1 = ImageManager.get("dialog-box1");
        BufferedImage dialog2 = ImageManager.get("dialog-box2");
        
        if (dialog1 != null) {
            dialogBoxFrames.add(dialog1);
        }
        if (dialog2 != null) {
            dialogBoxFrames.add(dialog2);
        }
        
        if (!dialogBoxFrames.isEmpty()) {
            currentDialogBox = dialogBoxFrames.get(0);
        }
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(ANIMATION_DELAY, e -> animateDialogBox());
    }
    
    private void animateDialogBox() {
        if (dialogBoxFrames.size() > 1) {
            currentFrameIndex = (currentFrameIndex + 1) % dialogBoxFrames.size();
            currentDialogBox = dialogBoxFrames.get(currentFrameIndex);
        }
    }
    
    public void startAnimation() {
        if (animationTimer != null && !animationTimer.isRunning() && dialogBoxFrames.size() > 1) {
            animationTimer.start();
        }
    }
    
    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }
    
    public BufferedImage getCurrentDialogBox() {
        return currentDialogBox;
    }
}
