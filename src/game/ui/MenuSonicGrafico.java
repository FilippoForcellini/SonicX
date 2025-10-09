package game.ui;


import static game.util.ResourceUtil.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;


import game.state.SaveData;
import game.audio.SoundManager;

public class MenuSonicGrafico {

    public interface Listener {
        void onStartLevelSelected(int index);
        void onExit();
    }

    private final Scene scene;
    private final BorderPane root;
    private final Listener listener;

    public MenuSonicGrafico(Listener listener, double width, double height) {
        this.listener = listener;
        root = new BorderPane();
        scene = new Scene(root, width, height);

        // background gif
        ImageView bg = new ImageView(image("/assets/images/ui/background.png"));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        bg.setPreserveRatio(false);
        StackPane center = new StackPane(bg);
        root.setCenter(center);
        // Start background music in menu
        SoundManager.playMusic("/assets/music/menu.mp3");

        // Logo
        ImageView logo = new ImageView(image("/assets/images/ui/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitHeight(140);
        StackPane top = new StackPane(logo);
        top.setPrefHeight(160);
        top.setBackground(new Background(
        new BackgroundFill(Color.rgb(76, 45, 198), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setTop(top);

        // Main buttons
        VBox menuBox = new VBox(16);
        menuBox.setAlignment(Pos.CENTER);
        Button start = imageButton("file:src/game/assets/images/ui/btn_start.png", "Start");
        Button options = imageButton("file:src/game/assets/images/ui/btn_options.png", "Options");
        Button exit = imageButton("file:src/game/assets/images/ui/btn_exit.png", "Exit");
        menuBox.getChildren().addAll(start, options, exit);
        center.getChildren().add(menuBox);

        start.setOnAction(e -> showStartMenu());
        options.setOnAction(e -> showOptionsMenu());
        exit.setOnAction(e -> listener.onExit());
    }

    private Button imageButton(String path, String fallback) {
        ImageView iv = new ImageView(new Image(path));
        iv.setPreserveRatio(true);
        iv.setFitHeight(56);
        Button b = new Button("", iv);
        b.setStyle("-fx-background-color: transparent;");
        b.setOnMouseEntered(e -> iv.setEffect(new DropShadow(20, Color.rgb(0,0,0,0.5))));
        b.setOnMouseExited(e -> iv.setEffect(null));
        b.setFocusTraversable(false);
        b.setAccessibleText(fallback);
        return b;
    }

    private void showStartMenu() {
        VBox box = new VBox(14);
        box.setAlignment(Pos.CENTER);

        Text title = new Text("Select Level");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(28));

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER);

        Button b1 = imageButton("file:src/game/assets/images/ui/level1.png", "Level 1");
        Button b2 = imageButton("file:src/game/assets/images/ui/level2.png", "Level 2");
        Button b3 = imageButton("file:src/game/assets/images/ui/level3.png", "Level 3");

        if (!SaveData.isLevelCompleted(1)) { disableWithLock(b2); disableWithLock(b3); }
        else if (!SaveData.isLevelCompleted(2)) { disableWithLock(b3); }

        b1.setOnAction(e -> listener.onStartLevelSelected(1));
        b2.setOnAction(e -> { if (!b2.isDisabled()) listener.onStartLevelSelected(2); });
        b3.setOnAction(e -> { if (!b3.isDisabled()) listener.onStartLevelSelected(3); });

        row.getChildren().addAll(b1, b2, b3);

        Button back = imageButton("file:src/game/assets/images/ui/btn_back.png", "Back");
        back.setOnAction(e -> showMain());

        StackPane center = new StackPane();
        ImageView bg2 = new ImageView(image("/assets/images/ui/background.png"));
        bg2.setFitWidth(scene.getWidth());
        bg2.setFitHeight(scene.getHeight());
        center.getChildren().addAll(bg2, new VBox(10, title, row, back));
        root.setCenter(center);
        // Start background music in menu
        SoundManager.playMusic("/assets/music/menu.mp3");
    }

    private void disableWithLock(Button b) {
        b.setDisable(true);
        ImageView iv = (ImageView) b.getGraphic();
        ImageView lock = new ImageView(image("/assets/images/ui/lock.png"));
        lock.setFitHeight(32); lock.setPreserveRatio(true);
        StackPane sp = new StackPane(iv, lock);
        b.setGraphic(sp);
    }

    private void showOptionsMenu() {
        double m = SaveData.getMaster();
        double mu = SaveData.getMusic();
        double sx = SaveData.getSfx();

        Slider sMaster = new Slider(0,1,m);
        Slider sMusic  = new Slider(0,1,mu);
        Slider sSfx    = new Slider(0,1,sx);

        sMaster.valueProperty().addListener((obs,v, nv)-> { SaveData.setMaster(nv.doubleValue()); SoundManager.setMaster(nv.doubleValue());});
        sMusic.valueProperty().addListener((obs,v, nv)->  { SaveData.setMusic(nv.doubleValue()); SoundManager.setMusic(nv.doubleValue());});
        sSfx.valueProperty().addListener((obs,v, nv)->    { SaveData.setSfx(nv.doubleValue());   SoundManager.setSfx(nv.doubleValue());});

        VBox labels = new VBox(16, new Text("Master"), new Text("Music"), new Text("SFX"));
        labels.getChildren().forEach(n -> ((Text)n).setFill(Color.WHITE));
        VBox sliders = new VBox(10, sMaster, sMusic, sSfx);
        HBox row = new HBox(16, labels, sliders);
        row.setAlignment(Pos.CENTER);

        Button back = imageButton("file:src/game/assets/images/ui/btn_back.png", "Back");
        back.setOnAction(e -> showMain());

        StackPane center = new StackPane();
        ImageView bg2 = new ImageView(image("/assets/images/ui/background.png"));
        bg2.setFitWidth(scene.getWidth());
        bg2.setFitHeight(scene.getHeight());
        VBox box = new VBox(20, row, back);
        box.setAlignment(Pos.CENTER);

        center.getChildren().addAll(bg2, box);
        root.setCenter(center);
        // Start background music in menu
        SoundManager.playMusic("/assets/music/menu.mp3");
    }

    private void showMain() {
        ImageView bg = new ImageView(image("/assets/images/ui/background.png"));
        bg.setFitWidth(scene.getWidth());
        bg.setFitHeight(scene.getHeight());

        VBox menuBox = new VBox(16);
        menuBox.setAlignment(Pos.CENTER);
        Button start = imageButton("file:src/game/assets/images/ui/btn_start.png", "Start");
        Button options = imageButton("file:src/game/assets/images/ui/btn_options.png", "Options");
        Button exit = imageButton("file:src/game/assets/images/ui/btn_exit.png", "Exit");
        menuBox.getChildren().addAll(start, options, exit);

        StackPane center = new StackPane(bg, menuBox);
        root.setCenter(center);
        // Start background music in menu
        SoundManager.playMusic("/assets/music/menu.mp3");
        start.setOnAction(e -> showStartMenu());
        options.setOnAction(e -> showOptionsMenu());
        exit.setOnAction(e -> listener.onExit());
    }

    public Scene getScene() { return scene; }
}
