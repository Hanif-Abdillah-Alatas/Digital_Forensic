package PanelAndFrame;

import ImageAnimator.ImageManager;
import text.FontManager;
import javax.swing.*;
import java.awt.*;
import engine.GameScene;
import engine.StoryLoader;
import java.util.List;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private StoryPanel storyPanel;
    
    public GameFrame() {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        ImageManager.load();
        FontManager.load();
        
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        
        // Setup CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Inisialisasi panel
        OpeningPanel openingPanel = new OpeningPanel(this);
        storyPanel = new StoryPanel(); 
        
        mainPanel.add(openingPanel, "opening");
        mainPanel.add(storyPanel, "Story");
        
        add(mainPanel);
        
        // Tampilkan opening panel pertama
        cardLayout.show(mainPanel, "opening");
    }
    
    public void switchToStory() {
        // 1. Load data default
        List<GameScene> story = StoryLoader.loadStory("Story.json");
        
        // 2. Masukkan data ke StoryPanel yang sudah ada
        storyPanel.loadStory(story); 
        
        // 3. Tampilkan Panel
        cardLayout.show(mainPanel, "Story");
    }
}