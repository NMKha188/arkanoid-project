package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gamecontroller.SceneController;
import arkanoid.source.code.graphic.Texture;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class InGameStatus {

    private static int score = 0;
    private static int lives = 3;
    private static int level = 1;
    private static final Text scoreText = new Text("Score: " + score);
    private static final Text livesText = new Text("Lives: " + lives);
    private static final Text levelText = new Text("Level: " + level);

    private static final Rectangle topBorder = new Rectangle(0, 0, Config.EXTRA +  Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4);
    private static final Rectangle downBorder = new Rectangle(0, Config.EXTRA / 4 + Config.GAMEPLAY_SCREEN_HEIGHT, Config.EXTRA + Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4); // downBorder fire burning animation
    private static final Rectangle leftBorder = new Rectangle(0, Config.EXTRA / 4, Config.EXTRA / 2, Config.EXTRA / 8 + Config.GAMEPLAY_SCREEN_HEIGHT);
    private static final Rectangle rightBorder = new Rectangle(Config.EXTRA / 2 + Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4, Config.EXTRA / 2, Config.EXTRA / 8 + Config.GAMEPLAY_SCREEN_HEIGHT);

    private static final Group group = new Group(topBorder, downBorder, leftBorder, rightBorder, scoreText, livesText, levelText);


    private static final double SCORE_X = Config.BRICK_WIDTH * 2 ;
    private static final double SCORE_Y = Config.BRICK_HEIGHT - 2;
    private static final double LIVES_X = Config.BRICK_WIDTH * 12;
    private static final double LIVES_Y = Config.BRICK_HEIGHT - 2;
    private static final double level_X = (SCORE_X + LIVES_X) / 2;
    private static final double level_Y = Config.BRICK_HEIGHT - 2;
    static {
        scoreText.setFont(Font.font("Papyrus", 25));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(SCORE_X);
        scoreText.setY(SCORE_Y);

        livesText.setFont(Font.font("Papyrus", 25));
        livesText.setFill(Color.BLACK);
        livesText.setX(LIVES_X);
        livesText.setY(LIVES_Y);

        levelText.setFont(Font.font("Papyrus", 25));
        levelText.setFill(Color.BLACK);
        levelText.setX(level_X);
        levelText.setY(level_Y);
    }
    public static void applyBorderTextures() {
        Texture.applyTextureToTopRec(topBorder);
        Texture.applyTextureToLeftRec(leftBorder);
        Texture.applyTextureToRightRec(rightBorder);
    }

    public static void startDownRecAnimation() {
        Texture.applyAndPlayAnimation(downBorder);
    }

    public static void stopDownRecAnimation() {
        Texture.stopAnimation();
    }
    public static int getScore() {
        return score;
    }

    public static void setScore(int value) {
        score = value;
        updateTexts();
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int a) {
        level = a;
    }

    public static void setNextMap() {
        score = getFinalScore();
        updateTexts();
        level++;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives) {
        InGameStatus.lives = lives;
    }

    public static void recoverLife() {
        lives++;
        updateTexts();
    }

    public static void loseLife() {
        if (lives > 0) lives--;
        updateTexts();
        if (lives == 0) {
            Ranking.printAndSaveRankScore(score);
            SceneController.showGameOverScene();
        }
    }

    public static Group getGroup() {
        return group;
    }

    public static void updateTexts() {
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
        levelText.setText("Level: " + level);
    }

    public static void resetGame() {
        score = 0;
        lives = 3;
        level = 1;
        updateTexts();
        InGameLogic.reset();
    }
    public static boolean hasFinishedGame() {
        return level>7;
    }
    public static void addGroupToGameRoot() {
        InGameLogic.getRoot().getChildren().add(InGameStatus.getGroup());
    }
    public static int getBonusScorePercentage() {
        switch (lives) {
            case 5 -> {
                return 50;
            }
            case 4 -> {
                return 35;
            }
            case 3 -> {
                return 20;
            }
            case 2 -> {
                return 10;
            }
            case 1 -> {
                return 5;
            }
            default -> {
                return 0;
            }
        }
    }

    public static int getFinalScore() {
        score = (int) (score * (1 + ((double) getBonusScorePercentage()) / 100));
        return score;
    }
}
