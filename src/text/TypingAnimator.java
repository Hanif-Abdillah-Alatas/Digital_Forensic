package text;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class TypingAnimator {
    private Timer typingTimer; 
    private String dialogText = ""; 
    private String displayedText = ""; 
    private int textIndex = 0;
    private final int TYPING_DELAY = 30; 

    public interface TypingCompleteListener {
        void onTypingComplete();
        void requestRepaint();
    }

    private TypingCompleteListener listener;

    public TypingAnimator(TypingCompleteListener listener, String initialText) {
        this.listener = listener;
        this.dialogText = initialText;
        typingTimer = new Timer(TYPING_DELAY, this::handleTypingTick);
    }

    private void handleTypingTick(ActionEvent e) {
        if (textIndex < dialogText.length()) {
            displayedText += dialogText.charAt(textIndex);
            textIndex++;
            listener.requestRepaint();
        } else {
            typingTimer.stop();
            listener.onTypingComplete();
        }
    }

    public void startTypingEffect() {
        displayedText = "";
        textIndex = 0;
        if (typingTimer != null && !typingTimer.isRunning()) {
            typingTimer.start();
        }
    }

    public String getDisplayedText() {
        return displayedText;
    }

    public boolean isTypingComplete() {
        return !typingTimer.isRunning();
    }

    public void setDialogText(String newText) {
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
        }
        this.dialogText = newText;
        listener.onTypingComplete();
        startTypingEffect(); 
    }

    public void skipTyping() {
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
            this.displayedText = this.dialogText;
            listener.onTypingComplete();
            listener.requestRepaint();
        }
    }

    public void cleanup() {
        if (typingTimer != null) {
            typingTimer.stop();
        }
    }
}
