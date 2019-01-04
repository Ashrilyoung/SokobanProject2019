package sokobandriver;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author 16007873
 */
public class SokobanGame extends Application {

    Level newLevel = new Level();

    //load in FXML File
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            String viewerFxml = "sokobanMenu.fxml";
            VBox page = (VBox) fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.exit(1);
        }

    }

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private GridPane gameGrid;

    public void SokobanGame() {

    }

    //function to set the game level number.
    public void SetLevel(int LevelNumber) {

    }

    @FXML
    private void startButton() throws IOException {
        newLevel.Level();
        setImage(newLevel.getMap());
    }

    @FXML
    private void exitButton() {
        System.exit(0);
    }

    @FXML
    private void upButton() throws IOException {
        newLevel.moveup();
        setImage(newLevel.getMap());

    }

    @FXML
    private void downButton() throws IOException {
    }

    @FXML
    private void leftButton() throws IOException {
    }

    @FXML
    private void rightButton() throws IOException {
    }

    public void setImage(MapElement[][] map) {
        int y = 0;
        int x = 0;
        while (map[x][y] != null) {
            if (map[x][y] != null) {
                Image imageno = new Image(map[x][y].getFileName(), 32, 32, false, false);
                gameGrid.add(new ImageView(imageno), x, y);
            }
            x++;
            if (map[x][y] == null) {
                y++;
                x = 0;
            }
        }
    }

}
