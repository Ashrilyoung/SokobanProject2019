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

    Level newLevel = new Level();   //initialise level vlass

    //load in FXML File
    @Override
    public void start(Stage primaryStage) throws Exception {  //load the javafx document

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            String viewerFxml = "sokobanMenu.fxml";        //the fxml document to load
            VBox page = (VBox) fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());   
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.exit(1);
        }

    }

    //create buttons from fxml document
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
    private Button NextLevelButton;

    
    //create grid to hold images for game
    @FXML
    private GridPane gameGrid;

    public void SokobanGame() {

    }

    //function to set the game level number.
    public void SetLevel(int LevelNumber) {

    }

    //button implementations
    @FXML
    private void startButton() throws IOException {
        newLevel.Level();                           //run the level class
        setImage(newLevel.getMap());            //set images as the object in each part of the map 2d array
    }

    @FXML
    private void NextLevelButton() throws IOException {
        gameGrid.getChildren().clear();                 //clear the gridpane
        newLevel.clearMap();                          //clear the map 2d array
        newLevel.setLevelNo();                       //change the level no by +1
        newLevel.Level();                           //run the level class with the new level number
        setImage(newLevel.getMap());            //set images as the object in each part of the map 2d array
    }

    @FXML
    private void exitButton() {
        System.exit(0);        //exit the program
    }

    //movement buttons
    @FXML
    private void upButton() throws IOException {
        gameGrid.getChildren().clear();
        newLevel.moveUp();
        setImage(newLevel.getMap());

    }

    @FXML
    private void downButton() throws IOException {
        gameGrid.getChildren().clear();
        newLevel.moveDown();
        setImage(newLevel.getMap());
    }

    @FXML
    private void leftButton() throws IOException {
        gameGrid.getChildren().clear();
        newLevel.moveLeft();
        setImage(newLevel.getMap());
    }

    @FXML
    private void rightButton() throws IOException {
        gameGrid.getChildren().clear();
        newLevel.moveRight();
        setImage(newLevel.getMap());
    }
    
    
 //Class to find and set images to the game panel
    
    public void setImage(MapElement[][] map) {
        int y = 0;
        int x = 0;
        while (map[x][y] != null) {           //make sure the map array is not empty
            if (map[x][y] != null) {
                Image imageno = new Image(map[x][y].getFileName(), 32, 32, false, false);      //find the image based on the object type
                gameGrid.add(new ImageView(imageno), x, y);                             //add the image to the pane
            }
            x++;
            if (map[x][y] == null) {
                y++;
                x = 0;
            }
        }
    }

}
