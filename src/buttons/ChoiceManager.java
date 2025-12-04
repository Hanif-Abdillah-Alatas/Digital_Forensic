package buttons;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ChoiceManager implements ChoiceButton.ChoiceActionListener {
    
    protected List<ChoiceButton> choiceButtons;
    protected ChoiceButton.ChoiceActionListener panelListener;
    
    public ChoiceManager(ChoiceButton.ChoiceActionListener listener) {
        this.panelListener = listener;
        this.choiceButtons = new ArrayList<>();
        setupButtons(); 
    }

    protected abstract void setupButtons();

    protected abstract void handleButtonClick(ChoiceButton clickedButton);
    
    public void checkAllHovers(Point p, int panelWidth, int panelHeight) {
        for (ChoiceButton button : choiceButtons) {
            button.checkHover(p, panelWidth, panelHeight);
        }
    }
    
    public void checkAllClicks(Point p, int panelWidth, int panelHeight) {
        for (ChoiceButton button : choiceButtons) {

            if (button.checkClick(p, panelWidth, panelHeight)) {

                handleButtonClick(button);     
                
                return; 
            }
        }
    }
    
    
    public void drawAll(Graphics g, int panelWidth, int panelHeight) {
        for (ChoiceButton button : choiceButtons) {
            button.draw(g, panelWidth, panelHeight);
        }
    }
    
    @Override
    public void onChoiceClicked() {}
    
    public void showAll(boolean show) {
        for (ChoiceButton button : choiceButtons) {
            button.show(show);
        }
    }
    
    protected void addButton(String imageKey) {
        choiceButtons.add(new ChoiceButton(imageKey));
    }
    
    
    public List<ChoiceButton> getChoiceButtons() {
        return choiceButtons;
    }
}