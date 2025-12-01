package buttons;

import PanelAndFrame.StoryPanel;
import engine.GameCharacter;
import engine.SuspectCharacter;

public class TwoChoiceManager extends ChoiceManager {
    
    public TwoChoiceManager(ChoiceButton.ChoiceActionListener listener) {
        super(listener);
    }
    
    @Override
    protected void setupButtons() {
        addButton("choice4-button");
        addButton("choice5-button");
    }
    
    @Override
    protected void handleButtonClick(ChoiceButton clickedButton) {
        int buttonIndex = choiceButtons.indexOf(clickedButton);
        
        if (panelListener instanceof StoryPanel) {
            StoryPanel panel = (StoryPanel) panelListener;
            
            switch(buttonIndex) {
                case 0:
                    String suspectId = panel.getCurrentScene().suspectId;
                    if (suspectId != null) {
                        GameCharacter character = panel.getGameState().getCharacter(suspectId);
                        if (character instanceof SuspectCharacter) {
                            boolean isGuilty = ((SuspectCharacter) character).isGuilty();
                            if (isGuilty) {
                                panel.onSceneChangeRequest("true_ending");
                            } else {
                                panel.onSceneChangeRequest("bad_ending");
                            }
                        }
                    }
                    break;
                    
                case 1:
                    panel.onSceneChangeRequest("scene_tuduh"); 
                    break;
            }
        }
    }
}
