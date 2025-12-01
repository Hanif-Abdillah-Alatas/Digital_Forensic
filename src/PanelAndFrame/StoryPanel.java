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
import engine.SuspectCharacter;

public class StoryPanel extends JPanel implements 
    TypingAnimator.TypingCompleteListener,
    NameCharBox.NameAnimationListener,
    ChoiceButton.ChoiceActionListener { 
    
    private GameStateManager gameState;
    private List<GameScene> currentStory;
    private GameScene currentScene;
    
    private DrawableAsset background; 
    private DrawableAsset fullScene; 
    
    private DialogBoxAnimator dialogBox;
    private NameCharBox nameCharBox;
    private TypingAnimator typingAnimator;
    
    private ChoiceManager currentManager; 
    
    public StoryPanel() {
        this.gameState = new GameStateManager();
        setupGameWorld();
        
        dialogBox = new DialogBoxAnimator();
        nameCharBox = new NameCharBox(this);
        typingAnimator = new TypingAnimator(this, "");
        
        new Timer(100, e -> repaint()).start();
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentManager != null && typingAnimator.isTypingComplete()) {
                    currentManager.checkAllClicks(e.getPoint(), getWidth(), getHeight());
                    return;
                }

                if (!typingAnimator.isTypingComplete()) {
                    typingAnimator.skipTyping();
                } else if (!nameCharBox.isNameTypingComplete()) {
                    nameCharBox.skipTyping();
                } else {
                    if (currentScene != null && currentScene.nextScene != null && !currentScene.nextScene.isEmpty()) {
                        if (currentScene.managerType == null) {
                            onSceneChangeRequest(currentScene.nextScene);
                        }
                    }
                }
            }
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentManager != null && typingAnimator.isTypingComplete()) {
                    currentManager.checkAllHovers(e.getPoint(), getWidth(), getHeight());
                }
            }
        });
    }
    
    private void setupGameWorld() {
        gameState.registerCharacter(new SuspectCharacter("hanif", "hanif"));
        gameState.registerCharacter(new SuspectCharacter("nayla", "nayla"));
        gameState.registerCharacter(new SuspectCharacter("alatas", "alatas"));
        gameState.registerCharacter(new SuspectCharacter("labib", "labib"));
        gameState.registerCharacter(new SuspectCharacter("dafa", "dafa"));
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
        }
    }
    
    @Override
    public void onChoiceClicked() {}
     
    public void showScene(GameScene scene) {
        this.currentScene = scene;
        
        if (scene.setGuilty != null) {
            engine.GameCharacter character = gameState.getCharacter(scene.setGuilty);
            if (character instanceof SuspectCharacter) {
                ((SuspectCharacter) character).setGuilty(true);
            }
        }
        
        if (scene.background != null) {
                background = new DrawableAsset(scene.background);
        }
            
        if (scene.hideCharacter) {
                fullScene = null;
        } else if (scene.characterImage != null) {
                fullScene = new DrawableAsset(scene.characterImage);
        }
        
        if (scene.hideNameBox || scene.characterName == null) {
            hideCharacterName();
        } else {
            showCharacterName(scene.characterName);
        }
        
        if (!scene.hideDialogBox) {
            setDialogText(scene.dialog);
            startDialogAnimation();
        } else {
            typingAnimator.skipTyping(); 
            stopDialogAnimation();
        }
        
        currentManager = null;

        if (scene.managerType != null) {
            switch (scene.managerType) {
                case "THREE_CHOICE": currentManager = new ThreeChoiceManager(this); break;
                case "EVIDENCE_CHOICE": currentManager = new EvidenceChoice(this); break;
                case "EVIDENCE_LAPTOP": currentManager = new EvidenceLaptop(this); break;
                case "PEOPLE_1": currentManager = new PeopleChoice1(this); break;
                case "PEOPLE_2": currentManager = new PeopleChoice2(this); break;
                case "TWO_CHOICE": currentManager = new TwoChoiceManager(this); break;
                case "ONE_CHOICE": currentManager = new OneChoiceManager(this); break;
            }
        }
        
        if (currentManager != null) {
            currentManager.showAll(false);
        }
        
        repaint();
    }
    
    @Override
    public void onTypingComplete() {
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
        if (fullScene != null && (currentScene == null || !currentScene.hideCharacter)) {
            fullScene.draw(g, 0, 0, w, h);
        }
        
        if (currentScene == null || !currentScene.hideDialogBox) {
            if (dialogBox.getCurrentDialogBox() != null) {
                g.drawImage(dialogBox.getCurrentDialogBox(), 0, 0, w, h, null);
            }
            drawText(g);
        }
        
        if (currentScene == null || !currentScene.hideNameBox) {
            nameCharBox.draw(g, w, h);
        }

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
