package engine;

import ImageAnimator.DrawableAsset;

public abstract class GameCharacter extends DrawableAsset {
    protected String characterId;
    protected String displayName;
    
    public GameCharacter(String characterId, String imageKey) {
        super(imageKey);
        this.characterId = characterId;
        System.out.println("Character created: " + characterId);
    }
    
    // --- GETTERS & SETTERS ---
    public String getCharacterId() { return characterId; }
    public String getDisplayName() { return displayName; }
    
}