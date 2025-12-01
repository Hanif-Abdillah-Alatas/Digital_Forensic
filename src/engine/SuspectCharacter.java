package engine;

public class SuspectCharacter extends GameCharacter {
    private boolean isGuilty; 

    public SuspectCharacter(String characterId, String imageKey) {
        super(characterId, imageKey);
        this.isGuilty = false;
    }

    public void setGuilty(boolean guilty) {
        this.isGuilty = guilty;
    }

    public boolean isGuilty() {
        return isGuilty;
    }
}
