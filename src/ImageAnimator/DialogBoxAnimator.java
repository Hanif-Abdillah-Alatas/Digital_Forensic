package ImageAnimator;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas yang menangani pemuatan, penyimpanan, dan animasi 
 * pergantian frame (gambar) kotak dialog.
 * Kelas ini TIDAK lagi bertanggung jawab atas posisi teks.
 */
public class DialogBoxAnimator {
    private BufferedImage currentDialogBox;
    private List<BufferedImage> dialogBoxFrames;
    private Timer animationTimer;
    private int currentFrameIndex = 0;
    
    // Penundaan (delay) untuk animasi pergantian frame (400 ms)
    private static final int ANIMATION_DELAY = 400; 

    public DialogBoxAnimator() {
        dialogBoxFrames = new ArrayList<>();
        loadDialogBoxes();
        setupAnimation();
    }
    
    /**
     * Memuat semua frame gambar dialog box yang tersedia.
     */
    private void loadDialogBoxes() {
        // Asumsi ImageManager.get() berfungsi untuk memuat gambar
        BufferedImage dialog1 = ImageManager.get("dialog-box1");
        BufferedImage dialog2 = ImageManager.get("dialog-box2");
        
        if (dialog1 != null) {
            dialogBoxFrames.add(dialog1);
        }
        if (dialog2 != null) {
            dialogBoxFrames.add(dialog2);
        }
        
        // Set frame default
        if (!dialogBoxFrames.isEmpty()) {
            currentDialogBox = dialogBoxFrames.get(0);
        }
    }
    
    /**
     * Mengatur Timer untuk animasi pergantian frame.
     */
    private void setupAnimation() {
        animationTimer = new Timer(ANIMATION_DELAY, e -> animateDialogBox());
    }
    
    /**
     * Logika untuk beralih ke frame dialog box berikutnya.
     */
    private void animateDialogBox() {
        if (dialogBoxFrames.size() > 1) {
            // Pindah ke frame berikutnya (melingkar)
            currentFrameIndex = (currentFrameIndex + 1) % dialogBoxFrames.size();
            currentDialogBox = dialogBoxFrames.get(currentFrameIndex);
        }
        // Catatan: Kelas pemanggil (StoryPanel) bertanggung jawab memanggil repaint()
    }
    
    // --- Kontrol Animasi Publik ---
    
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
    
    // --- Getter Publik ---
    
    public BufferedImage getCurrentDialogBox() {
        return currentDialogBox;
    }
    
}


