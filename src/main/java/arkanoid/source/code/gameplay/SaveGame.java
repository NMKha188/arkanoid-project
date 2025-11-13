package arkanoid.source.code.gameplay;

import java.io.*;

public class SaveGame {
    private static String pathGame = "src/main/resources/arkanoid/resources/scorestatus/saveGame.txt";
    private static File fileGame = new File(pathGame);

    public static void saveGame() {
        try {
            FileWriter writer = new FileWriter(fileGame, false);
            writer.write(InGameStatus.getScore() + " " + InGameStatus.getLives() + " " + InGameStatus.getLevel()+ '\n');
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR SAVEGAME: " + e.getMessage());
        }
    }

    public static void resetSaveGameFile() {
        try {
            FileWriter writer = new FileWriter(fileGame,false);
            writer.write(0 + " " + 3 + " " + 1);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileGame))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                int score = Integer.parseInt(parts[0]);
                int live = Integer.parseInt(parts[1]);
                int level = Integer.parseInt(parts[2]);
                InGameStatus.setLevel(level);
                InGameStatus.setScore(score);
                InGameStatus.setLives(live);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}