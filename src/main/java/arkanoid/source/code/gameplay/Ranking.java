package arkanoid.source.code.gameplay;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking {

    private static String path = "src/main/resources/arkanoid/resources/scorestatus/ranking.txt";
    private static String pathGame = "src/main/resources/arkanoid/resources/scorestatus/saveGame.txt";
    private static File file = new File(path);
    private static File fileGame = new File(pathGame);
    private static List<Integer> arr = new ArrayList<>();

    static {
        file.getParentFile().mkdirs();
        fileGame.getParentFile().mkdirs();
    }

    private static void writeScoreToFile(int score) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(score + "\n");
            fw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void loadRanking() {

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            arr.clear();
            while ((line = br.readLine()) != null) {
                try {
                    arr.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number: " + e.getMessage());
                }
            }
            arr.sort(Collections.reverseOrder());
            while (arr.size() > 5) {
                arr.remove(5);
            }
        } catch (IOException e) {
            System.err.println("Error reading ranking file: " + e.getMessage());
        }
    }


    private static void saveRankingToFile() {
        try (FileWriter fw = new FileWriter(file, false)) {
            for (Integer score : arr) {
                fw.write(score + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error overriding ranking file: " + e.getMessage());
        }
    }

    private static void printRankScore(List<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.println("No." + (i + 1) + " Score: " + arr.get(i));
        }
    }

    public static void printAndSaveRankScore(int score) {
        writeScoreToFile(score);
        loadRanking();
        saveRankingToFile();
        printRankScore(arr);
    }

    public static List<Integer> returnArr() {
        loadRanking();
        while (arr.size() < 5) {
            arr.add(0);
        }
        return arr;
    }

    public static void saveGame() {
        try {
            FileWriter writer = new FileWriter(fileGame, false);
            writer.write(InGameStatus.getScore() + " " + InGameStatus.getLives() + " " + InGameStatus.getCurrentMap()+ '\n');
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR SAVEGAME: " + e.getMessage());
        }
    }

    public static int getCurrentMapFromFile() {
        int currentMap=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileGame))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                int score = Integer.parseInt(parts[0]);
                int live = Integer.parseInt(parts[1]);
                currentMap = Integer.parseInt(parts[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentMap;
    }

    public static void resetGameInFile() {
        try {
            FileWriter writer = new FileWriter(fileGame,false);
            writer.write(0 + " " + 3 + " " + 1);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void setCurrentMapFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileGame))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                int score = Integer.parseInt(parts[0]);
                int live = Integer.parseInt(parts[1]);
                int currentMap = Integer.parseInt(parts[2]);
                InGameStatus.setCurrentMap(currentMap);
                InGameStatus.setScore(score);
                InGameStatus.setLives(live);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}