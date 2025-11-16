package arkanoid.source.code.gameplay.gamestatus;

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
}