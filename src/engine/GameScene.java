package engine;


public class GameScene {
    public String sceneId;
    public String background;
    public String characterImage;
    public String characterName;
    public String dialog;
    public String managerType;
    public String nextScene;
    public String setGuilty; // Untuk setting pelaku di awal
    public String suspectId; // TAMBAHAN BARU: Siapa tersangka di scene ini?
    public boolean hideDialogBox;    // True = Sembunyikan kotak dialog & teks
    public boolean hideCharacter;    // True = Sembunyikan karakter
    public boolean hideNameBox;      // True = Sembunyikan kotak nama
    
}

