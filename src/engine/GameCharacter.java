package engine;

import ImageAnimator.DrawableAsset;

public abstract class GameCharacter extends DrawableAsset {
    protected String characterId;
    
    public GameCharacter(String characterId, String imageKey) {
        super(imageKey);
        this.characterId = characterId;
        System.out.println("Character created: " + characterId);
    }
    
    // --- GETTERS & SETTERS ---
    public String getCharacterId() { return characterId; }
}