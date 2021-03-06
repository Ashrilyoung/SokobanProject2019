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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 16007873
 *
 * This class handles the on screen elements and interactive elements such as
 * buttons which control the game
 */
public class SokobanGame extends Application {

    Level newLevel = new Level();   //initialise level class

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

    /**
     * create buttons from fxml document
     */
    @FXML
    private Text NumberOfMovesText;

    @FXML
    private Text ErrorText;

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

    /**
     * create grid to hold images for game
     */
    @FXML
    private GridPane gameGrid;

    /**
     *
     * @throws IOException method to handle start button. initialises the map
     */
    @FXML
    private void startButton() throws IOException {
        newLevel.Level();                           //run the level class
        setImage(newLevel.getMap());            //set images as the object in each part of the map 2d array
    }

    /**
     *
     * @throws IOException
     *
     * method to move map to the next level
     */
    @FXML
    private void NextLevelButton() throws IOException {
        gameGrid.getChildren().clear();                 //clear the gridpane
        newLevel.clearMap();                          //clear the map 2d array
        newLevel.increaseLevelNo();                       //change the level no by +1
        newLevel.Level();                           //run the level class with the new level number
        setImage(newLevel.getMap());            //set images as the object in each part of the map 2d array
        setErrorText();
    }

    /**
     *
     * method to exit the game
     */
    @FXML
    private void exitButton() {
        System.exit(0);        //exit the program
    }

    /**
     *
     * @throws IOException
     *
     * method to move the character up
     */
    @FXML
    private void upButton() throws IOException {
        gameGrid.getChildren().clear();                     //clears the old images from the screen
        moveUp();                                  //moves the warehousekeeper
        setImage(newLevel.getMap());                        //sets the images based on the new postisions of the objects in the mp array
        setMovesText();  //counts the number of moves by the user

    }

    /**
     *
     * @throws IOException method to move the character down
     */
    @FXML
    private void downButton() throws IOException {
        gameGrid.getChildren().clear();
        moveDown();
        setImage(newLevel.getMap());
        setMovesText();
    }

    /**
     *
     * @throws IOException
     *
     * method to move the character left
     */
    @FXML
    private void leftButton() throws IOException {
        gameGrid.getChildren().clear();
        moveLeft();
        setImage(newLevel.getMap());
        setMovesText();
    }

    /**
     *
     * @throws IOException
     *
     * Moves the character right
     */
    @FXML
    private void rightButton() throws IOException {
        gameGrid.getChildren().clear();
        moveRight();
        setImage(newLevel.getMap());
        setMovesText();
    }

    /**
     *
     * sets the text for number of character moves
     */
    public void setMovesText() {
        NumberOfMovesText.setText("Moves " + newLevel.warehouseKeeper.getNumberOfMoves());   //displays the characters movement number on screen
    }

    /**
     *
     * sets the text for any error messages to displayed to the user
     */
    public void setErrorText() {
        ErrorText.setText(newLevel.getErrorText());   //displays error text on screen
    }

    /**
     *
     * @param map Class to find and set images to the game panel
     */
    public void setImage(MapElement[][] map) {
        int yCoord = 0;
        int xCoord = 0;
        NumberOfMovesText.setText(""); //clears the text so that it resets properly when a level is restarted or new level loaded
        while (map[xCoord][yCoord] != null) {           //make sure the map array is not empty
            if (map[xCoord][yCoord] != null) {
                Image imageno = new Image(map[xCoord][yCoord].getFileName(), 32, 32, false, false);      //find the image based on the object type, Example of polymorphism
                gameGrid.add(new ImageView(imageno), xCoord, yCoord);                             //add the image to the pane

            }
            xCoord++;
            if (map[xCoord][yCoord] == null) {
                yCoord++;
                xCoord = 0;
            }

        }
    }

    /**
     *
     * Function to move the character up
     */
    public void moveUp() {
        newLevel.moveCrateCheck(0, -1);
    }

    /**
     * Function to move the character right
     */
    public void moveRight() {
        newLevel.moveCrateCheck(1, 0);
    }

    /**
     * Function to move the character down
     */
    public void moveDown() {
        newLevel.moveCrateCheck(0, 1);
    }

    /**
     * Function to move the character left
     */
    public void moveLeft() {
        newLevel.moveCrateCheck(-1, 0);
    }

}
