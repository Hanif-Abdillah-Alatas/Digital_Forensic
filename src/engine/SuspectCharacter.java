package engine;

public class SuspectCharacter{
    private boolean isGuilty; // Hanya butuh satu variabel penting ini

    public SuspectCharacter() {
        this.isGuilty = false; // Default: Tidak bersalah
    }

    // Method untuk menandai dia pelaku (dipanggil dari JSON)
    public void setGuilty(boolean guilty) {
        this.isGuilty = guilty;
    }

    // Method untuk mengecek (dipanggil saat tombol Tuduh ditekan)
    public boolean isGuilty() {
        return isGuilty;
    }
}