
package buttons;

import PanelAndFrame.StoryPanel;

public class PeopleChoice2 extends ChoiceManager {
    
    public PeopleChoice2(ChoiceButton.ChoiceActionListener listener) {
        super(listener);
    }
    
    @Override
    protected void setupButtons() {
        // Setup 3 tombol dengan gambar berbeda
        addButton("labib");
        addButton("nayla"); 
        addButton("dafa");
        addButton("choice5-button");
    }
    
    @Override
    protected void handleButtonClick(ChoiceButton clickedButton) {
        // Logic spesifik untuk 3 tombol
        int buttonIndex = choiceButtons.indexOf(clickedButton);
        if (panelListener instanceof StoryPanel) {
            StoryPanel panel = (StoryPanel) panelListener;
            switch(buttonIndex) {
                case 0:
                    panel.onSceneChangeRequest("tuduh-labib");
                    break;
                case 1:
                    panel.onSceneChangeRequest("tuduh-nayla");
                    break;
                case 2:
                    panel.onSceneChangeRequest("tuduh-dafa");
                    break;
                case 3:
                    panel.onSceneChangeRequest("kembali-tuduh");
                    break;  
            }
        }
    }
    
}