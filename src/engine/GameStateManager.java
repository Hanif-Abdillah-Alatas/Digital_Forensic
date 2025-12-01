package engine;

import java.util.HashMap;
import java.util.Map;

public class GameStateManager {
    private Map<String, GameCharacter> characters;
    
    public GameStateManager() {
        this.characters = new HashMap<>();
        System.out.println("🎮 Game State Manager initialized");
    }
    
    // --- CHARACTER MANAGEMENT ---
    public void registerCharacter(GameCharacter character) {
        characters.put(character.getCharacterId(), character);
        System.out.println("👤 Character registered: " + character.getCharacterId());
    }
    
    public GameCharacter getCharacter(String characterId) {
        GameCharacter character = characters.get(characterId);
        if (character == null) {
            System.out.println("❌ Character not found: " + characterId);
        }
        return character;
    }
    
}