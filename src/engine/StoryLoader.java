// engine/StoryLoader.java
package engine;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.util.List;

public class StoryLoader {
    public static List<GameScene> loadStory(String storyFile) {
            InputStreamReader reader = new InputStreamReader(
                StoryLoader.class.getResourceAsStream("/stories/" + storyFile)
            );
            GameScene[] scenes = new Gson().fromJson(reader, GameScene[].class);
            return List.of(scenes);
    }
}