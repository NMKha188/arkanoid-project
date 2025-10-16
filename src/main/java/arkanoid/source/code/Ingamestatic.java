package arkanoid.source.code;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Ingamestatic {

    private int score;
    private int lives;

    public int getScore() { return score; }
    public int getLives() { return lives; }

    private final Text scoreText;
    private final Text livesText;

    private static final Font INFO_FONT = Font.font("Consolas", 24);
    private static final Color INFO_COLOR = Color.WHITE;
    private static final double SCORE_X = 20;
    private static final double SCORE_Y = 30;
    private static final double LIVES_X = 600;
    private static final double LIVES_Y = 30;

    public Ingamestatic(int live) {
        score = 0;
        lives = live;

        scoreText = new Text("Score: " + score);
        scoreText.setFont(Font.font("Consolas", 24));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(400); //hien thi o dau thi luc ghi sau
        scoreText.setY(400);

        livesText = new Text("Lives: " + lives);
        livesText.setFont(INFO_FONT);
        livesText.setFill(INFO_COLOR);
        livesText.setX(300);
        livesText.setY(300);
    }

    public void addScore(int value) {
        score += value;
        updateTexts();
    }

    public void loseLife() {
        if (lives > 0) lives--;
        updateTexts();
    }

    public void reset(int live) {
        score = 0;
        lives = live;
        updateTexts();
    }

    public void addToGroup(Group root) {
        root.getChildren().addAll(scoreText, livesText);
    }

    private void updateTexts() {
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
    }

    public void Check(boolean breakbrick, boolean balldie) { // cần check phá gạch và mất bóng
        if (breakbrick) {
            addScore(1);
        }
        if (balldie) {
            loseLife();
        }
    }
}
