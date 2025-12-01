package engine;

import java.util.HashMap;
import java.util.Map;

public class GameStateManager {
    private Map<String, GameCharacter> characters;
    
    public GameStateManager() {
        this.characters = new HashMap<>();
    }
    
    // --- CHARACTER MANAGEMENT ---
    public void registerCharacter(GameCharacter character) {
        characters.put(character.getCharacterId(), character);
    }
    
    public GameCharacter getCharacter(String characterId) {
        GameCharacter character = characters.get(characterId);
        return character;
    }
    
}