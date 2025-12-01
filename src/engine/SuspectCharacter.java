package engine;

// 1. Tambahkan 'extends GameCharacter' agar dikenali sebagai karakter game
public class SuspectCharacter extends GameCharacter {
    
    private boolean isGuilty; 

    // 2. Perbaiki Constructor agar sesuai dengan panggilan di StoryPanel ("id", "image")
    public SuspectCharacter(String characterId, String imageKey) {
        super(characterId, imageKey); // Panggil constructor induk (GameCharacter)
        this.isGuilty = false;        // Default: Tidak bersalah
    }

    // Method untuk menandai dia pelaku (dipanggil dari JSON/StoryPanel)
    public void setGuilty(boolean guilty) {
        this.isGuilty = guilty;
    }

    // Method untuk mengecek (dipanggil saat tombol Tuduh ditekan)
    public boolean isGuilty() {
        return isGuilty;
    }
}