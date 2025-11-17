package PanelAndFrame;

import ImageAnimator.DialogBoxAnimator;
import ImageAnimator.DrawableAsset;
import ImageAnimator.NameCharBox;
import buttons.ChoiceButton;
import buttons.ChoiceManager; 
import buttons.ThreeChoiceManager;
import buttons.EvidenceChoice;
import buttons.EvidenceLaptop;
import buttons.PeopleChoice1;
import buttons.PeopleChoice2;
import buttons.TwoChoiceManager;
import buttons.OneChoiceManager;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import text.DialogText;
import text.FontManager;
import text.TypingAnimator;
import engine.GameScene;
import engine.GameStateManager;
import engine.DetectiveCharacter;
import engine.DigitalEvidence;
import engine.SuspectCharacter;

class StaticAsset extends DrawableAsset {
    public StaticAsset(String imageKey) {
        super(imageKey);
    }
}

public class StoryPanel extends JPanel implements 
    TypingAnimator.TypingCompleteListener,
    NameCharBox.NameAnimationListener,
    ChoiceButton.ChoiceActionListener { 
    
    private GameStateManager gameState;
    private List<GameScene> currentStory;
    private GameScene currentScene;
    
    // Komponen Visual
    private StaticAsset background;
    private StaticAsset fullScene; 
    private DialogBoxAnimator dialogBox;
    private NameCharBox nameCharBox;
    private TypingAnimator typingAnimator;
    
    // HANYA PAKAI SATU MANAGER
    private ChoiceManager currentManager; 
    
    public StoryPanel() {
        // Setup komponen
        this.gameState = new GameStateManager();
        setupGameWorld();
        
        dialogBox = new DialogBoxAnimator();
        nameCharBox = new NameCharBox(this);
        typingAnimator = new TypingAnimator(this, "");
        
        // Timer repaint
        new Timer(100, e -> repaint()).start();
        setOpaque(false);
        
        // --- MOUSE LISTENER (PERBAIKAN UTAMA) ---
        // KODE BARU (PERBAIKAN)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("🖱️ Mouse clicked");

                // PRIORITAS 1: Jika teks sedang mengetik, skip teksnya.
                if (!typingAnimator.isTypingComplete()) {
                    typingAnimator.skipTyping();
                } 

                // PRIORITAS 2: Jika nama sedang mengetik, skip namanya.
                else if (!nameCharBox.isNameTypingComplete()) {
                    nameCharBox.skipTyping();
                } 

                // PRIORITAS 3: Jika teks selesai DAN ada tombol, cek klik tombol.
                else if (currentManager != null) {
                    // Karena typing complete, kita bisa cek tombol
                    currentManager.checkAllClicks(e.getPoint(), getWidth(), getHeight());
                } 

                // PRIORITAS 4: Jika semua selesai DAN TIDAK ada tombol, cek lanjut scene.
                else {
                    if (currentScene != null && currentScene.nextScene != null && !currentScene.nextScene.isEmpty()) {
                        // Cek apakah scene ini MODE TOMBOL? Kalau mode tombol, jangan bolehin klik sembarang buat skip
                        if (currentScene.managerType == null) {
                            System.out.println("⏩ Lanjut ke scene berikutnya: " + currentScene.nextScene);
                            onSceneChangeRequest(currentScene.nextScene);
                        }
                    }
                }
            }
        });
        
        // Listener Hover Tombol
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentManager != null && typingAnimator.isTypingComplete()) {
                    currentManager.checkAllHovers(e.getPoint(), getWidth(), getHeight());
                }
            }
        });

        System.out.println("✅ StoryPanel setup complete");
    }
    
    private void setupGameWorld() {
        // 1. Setup Detective & Evidence (Kode Lama)
        gameState.registerCharacter(new DetectiveCharacter("det_syauqy", "syauqy-dialog"));
        gameState.registerEvidence(new DigitalEvidence("laptop_evidence", "laptop"));

        // 2. TAMBAHAN BARU: Register Tersangka (Agar bisa diset guilty/innocent)
        // ID Karakter harus cocok dengan yang nanti ditulis di setGuilty JSON
        gameState.registerCharacter(new SuspectCharacter("hanif", "hanif"));
        gameState.registerCharacter(new SuspectCharacter("nayla", "nayla"));
        gameState.registerCharacter(new SuspectCharacter("alatas", "alatas"));
        gameState.registerCharacter(new SuspectCharacter("labib", "labib"));
        gameState.registerCharacter(new SuspectCharacter("dafa", "dafa"));

        System.out.println("✅ Semua karakter telah didaftarkan!");
    }
    
    public void loadStory(List<GameScene> story) {
        this.currentStory = story;
        if (!story.isEmpty()) {
            showScene(story.get(0));
        }
    }
    
    public void onSceneChangeRequest(String nextSceneId) {
        if (nextSceneId != null && !nextSceneId.isEmpty()) {
            if (currentStory != null) {
                for (GameScene scene : currentStory) {
                    if (scene.sceneId.equals(nextSceneId)) {
                        showScene(scene);
                        return;
                    }
                }
            }
            System.out.println("Scene tidak ditemukan: " + nextSceneId);
        }
    }
    
    @Override
    public void onChoiceClicked() {
        // Biarkan kosong
    }
     
    public void showScene(GameScene scene) {
        this.currentScene = scene;
        
        // --- LOGIKA BARU: SET TERSANGKA DARI JSON ---
        if (scene.setGuilty != null) {
            // Ambil karakter dari GameStateManager
            engine.GameCharacter character = gameState.getCharacter(scene.setGuilty);

            // Cek apakah dia SuspectCharacter?
            if (character instanceof SuspectCharacter) {
                ((SuspectCharacter) character).setGuilty(true);
                System.out.println("⚖️ KEPUTUSAN: " + scene.setGuilty + " ditetapkan sebagai PELAKU!");
            } else {
                System.out.println("⚠️ Karakter " + scene.setGuilty + " tidak ditemukan atau bukan tersangka.");
            }
        }
        
        try {
            if (scene.background != null) {
                background = new StaticAsset(scene.background);
            }
            
            // 2. Update Karakter (Cek Flag hideCharacter)
            if (scene.hideCharacter) {
                fullScene = null; // Hapus karakter dari layar
            } else if (scene.characterImage != null) {
                fullScene = new StaticAsset(scene.characterImage);
            }
            // Jika tidak hide dan tidak ada image baru, biarkan yang lama (opsional)
            // Atau paksa null jika mau reset setiap scene:
            // else { fullScene = null; } 
            
        } catch (Exception e) {
            System.out.println("Gagal load gambar: " + e.getMessage());
        }
        
        // 3. Update Nama (Cek Flag hideNameBox)
        if (scene.hideNameBox || scene.characterName == null) {
            hideCharacterName();
        } else {
            showCharacterName(scene.characterName);
        }
        
        // Kalau dialog box di-hide, biasanya teks juga tidak perlu di-set/animasi
        if (!scene.hideDialogBox) {
            setDialogText(scene.dialog);
            startDialogAnimation();
        } else {
            // Hentikan animasi teks jika dialog box hilang
            typingAnimator.skipTyping(); 
            stopDialogAnimation();
        }
        
        // RESET MANAGER LAMA
        currentManager = null;

        // PILIH MANAGER BERDASARKAN KODE DI JSON
        if (scene.managerType != null) {
            switch (scene.managerType) {
                case "THREE_CHOICE": currentManager = new ThreeChoiceManager(this); break;
                case "EVIDENCE_CHOICE": currentManager = new EvidenceChoice(this); break;
                case "EVIDENCE_LAPTOP": currentManager = new EvidenceLaptop(this); break;
                case "PEOPLE_1": currentManager = new PeopleChoice1(this); break;
                case "PEOPLE_2": currentManager = new PeopleChoice2(this); break;
                case "TWO_CHOICE": currentManager = new TwoChoiceManager(this); break;
                case "ONE_CHOICE": currentManager = new OneChoiceManager(this); break;
                default: System.out.println("Manager Type tidak dikenal: " + scene.managerType);
            }
        }
        
        // Sembunyikan tombol sampai teks selesai
        if (currentManager != null) {
            currentManager.showAll(false);
        }
        
        repaint();
    }
    
    @Override
    public void onTypingComplete() {
        System.out.println("✅ Dialog typing complete");
        // TAMPILKAN TOMBOL SETELAH MENGETIK SELESAI
        if (currentManager != null) {
            currentManager.showAll(true);
        }
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        if (background != null) background.draw(g, 0, 0, w, h);
        // 2. Draw Karakter (Hanya jika tidak di-hide)
        if (fullScene != null && (currentScene == null || !currentScene.hideCharacter)) {
            fullScene.draw(g, 0, 0, w, h);
        }
        // 3. Draw Dialog Box & Name Box (Hanya jika tidak di-hide)
        if (currentScene == null || !currentScene.hideDialogBox) {
            if (dialogBox.getCurrentDialogBox() != null) {
                g.drawImage(dialogBox.getCurrentDialogBox(), 0, 0, w, h, null);
            }
            drawText(g); // Teks hanya digambar kalau box-nya ada
        }
        
        if (currentScene == null || !currentScene.hideNameBox) {
            nameCharBox.draw(g, w, h);
        }

        // GAMBAR TOMBOL (HANYA CURRENT MANAGER)
        if (currentManager != null && typingAnimator.isTypingComplete()) {
            currentManager.showAll(true);
            currentManager.drawAll(g, w, h);
        }
    }
    
    private void drawText(Graphics g) {
        g.setFont(FontManager.get("regular", 20f)); 
        g.setColor(Color.BLACK);
        
        int textX = DialogText.getTextX();
        int textY = DialogText.getTextY();
        int maxTextWidth = DialogText.getMaxTextWidth();
        
        String textToDraw = typingAnimator.getDisplayedText();
        String[] lines = wrapText(g.getFontMetrics(), textToDraw, maxTextWidth);
        
        int lineHeight = g.getFontMetrics().getHeight();
        for (String line : lines) {
            g.drawString(line, textX, textY);
            textY += lineHeight; 
        }
    }
    
    private String[] wrapText(FontMetrics fm, String text, int maxWidth) {
        if (fm.stringWidth(text) <= maxWidth) return new String[]{text};
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String currentLine = "";
        for (String word : words) {
            String prospectiveLine = currentLine.isEmpty() ? word : currentLine + " " + word;
            if (fm.stringWidth(prospectiveLine) <= maxWidth) currentLine = prospectiveLine;
            else { lines.add(currentLine); currentLine = word; }
        }
        lines.add(currentLine);
        return lines.toArray(new String[0]);
    }
    
    public void setDialogText(String newText) { typingAnimator.setDialogText(newText); }
    public void showCharacterName(String characterName) { nameCharBox.show(characterName); repaint(); }
    public void hideCharacterName() { nameCharBox.hide(); repaint(); }
    public void requestRepaint() { repaint(); }
    public void onNameTypingComplete() {}
    public void startDialogAnimation() { dialogBox.startAnimation(); }
    public void stopDialogAnimation() { dialogBox.stopAnimation(); }
    
    @Override
    public void removeNotify() {
        super.removeNotify();
        typingAnimator.cleanup();
        nameCharBox.cleanup();
        dialogBox.stopAnimation();
    }
    

    public GameStateManager getGameState() {
        return gameState;
    }
    public GameScene getCurrentScene() {
    return currentScene;
}

}