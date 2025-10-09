package game.ui;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/** Minimal HUD with separate lines for info and boss. */
public class HUD {
    private final Text infoText;
    private final Text ringsText;
    private final Text livesText;
    private final Text bossText;

    public HUD(Group root) {
                        infoText = new Text(12, 46, "");
        ringsText = new Text(12, 22, "Rings: 0");
        livesText = new Text(1180, 22, "Lives: 3");
        infoText.setFill(Color.WHITE);
        ringsText.setFill(Color.WHITE);
        livesText.setFill(Color.WHITE);
        infoText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 4, 0.4, 1, 1);");
        livesText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 4, 0.4, 1, 1);");

        bossText = new Text(12, 44, "");
        bossText.setFill(Color.ORANGE);
        bossText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 4, 0.4, 1, 1);");

        root.getChildren().addAll(infoText, livesText, bossText, ringsText);
    }

    /** Generic top-line message. */
    public void setLives(int n) { livesText.setText("Lives: " + n); }

    public void setRings(int n) { ringsText.setText("Rings: " + n); }

    public void setMessage(String s) {
        infoText.setText(s == null ? "" : s);
    }

    /** Show Boss HP/Phase on the second line. */
    public void setBossStats(int hp, String phase) {
        bossText.setText("Boss Lives: " + hp + "  |  Phase: " + (phase == null ? "-" : phase));
    }

    /** Clear boss line. */
    public void clearBoss() {
        bossText.setText("");
    }
}
