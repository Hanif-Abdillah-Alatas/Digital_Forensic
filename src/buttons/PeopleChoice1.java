package buttons;
import PanelAndFrame.StoryPanel;

public class PeopleChoice1 extends ChoiceManager {
    
    public PeopleChoice1(ChoiceButton.ChoiceActionListener listener) {
        super(listener);
    }
    
    @Override
    protected void setupButtons() {
        addButton("hanif");
        addButton("nayla"); 
        addButton("alatas");
        addButton("choice5-button");
    }
    
    @Override
    protected void handleButtonClick(ChoiceButton clickedButton) {
        int buttonIndex = choiceButtons.indexOf(clickedButton);
        if (panelListener instanceof StoryPanel) {
            StoryPanel panel = (StoryPanel) panelListener;
            switch(buttonIndex) {
                case 0:
                    panel.onSceneChangeRequest("pilih-hanif");
                    break;
                case 1:
                    panel.onSceneChangeRequest("pilih-nayla");
                    break;
                case 2:
                    panel.onSceneChangeRequest("pilih-alatas");
                    break;
                case 3:
                    panel.onSceneChangeRequest("kembali-saksi");
                    break;   
            }
        }
    }
}