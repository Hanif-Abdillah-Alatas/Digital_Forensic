package engine;



public abstract class GameCharacter {
    private String characterId;
    
    public GameCharacter(String characterId) {

        this.characterId = characterId;
        System.out.println("Character created: " + characterId);
    }
    
    // --- GETTERS & SETTERS ---
    public String getCharacterId() { return characterId; }
}