package sokobandriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 16007873 class to generate levels and handle character movement
 */
public class Level {

    private MapElement[][] map = new MapElement[23][14];             //stores map information
    WarehouseKeeper warehouseKeeper = new WarehouseKeeper();    //creates warehousekeeper object
    private MapElement[][] diamondList = new MapElement[23][14];       //stores location of diamonds
    private int levelNo = 1;        //level number to start on

    /**
     * this will load the level
     */
    public void Level() {

        File textFile = new File("src/resources/SokobanMaps/level" + levelNo + ".txt");	// we pass the filename as a parameter to the constructor

        // tests if the file exists on the file system
        if (textFile.exists()) {
            System.out.println("Found file textFile : " + textFile.getName());
        } else {
            System.out.println("Cannot find file : " + textFile.getAbsolutePath() + "/" + textFile.getName());
        }

        // READING FROM A FILE
        FileReader reader = null;

        BufferedReader inputBuffer = null;

        try {

            warehouseKeeper.resetMoveNo();                      //sets the character move number back to 0
            reader = new FileReader(textFile);
            inputBuffer = new BufferedReader(reader);
            String inputLine = inputBuffer.readLine();
            int x = 0;
            int y = 0;
            while (inputLine != null) {
                System.out.println(inputLine);	// output the result
                for (int i = 0; i < inputLine.length(); i++) {
                    char mapItem = inputLine.charAt(i);
                    switch (mapItem) {
                        case '@':
                            //warehouseKeeper
                            warehouseKeeper.createElement(x, y);                   //assign the correct coordinates to the warehouseKeeper
                            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);    //add the warehousekeeper to the map
                            break;
                        case '*':
                            //crate
                            Crate crate = new Crate();             //create a new crate object
                            setMapElement(x, y, crate);             //add the crate to the map[][] array
                            break;
                        case '.':
                            //diamond
                            Diamond diamond = new Diamond();
                            setMapElement(x, y, diamond);
                            setDiamondListElement(x, y, diamond);
                            break;
                        case 'X':
                            //wall
                            Wall wall = new Wall();
                            setMapElement(x, y, wall);
                            break;
                        case ' ':
                            //tile
                            Tile tile = new Tile();
                            setMapElement(x, y, tile);
                            break;
                        default:
                            System.out.println("Sorry that Character was not Recognised");
                            System.exit(0);
                    }
                    x++;
                    if (x >= inputLine.length()) {
                        y++;
                        x = 0;
                    }

                }
                inputLine = inputBuffer.readLine();	// try to read a line

            }
        } catch (FileNotFoundException fnfe) {	// even though we know the file exists Java still needs to catch the potential error
            fnfe.printStackTrace();
        } catch (IOException ioe) {	// readLine() throws an IOException for any problem reading a line from the file
            ioe.printStackTrace();
        }

    }


    /**
     *
     * @return checks whether or not all the level is complete
     */
    public boolean checkLevelComplete() {
        boolean levelComplete = false;
        return levelComplete;
    }

    /**
     *
     * @return returns the map[][] array allowing it to be read from another
     * class
     *
     */
    public MapElement[][] getMap() {
        return map;
    }

    /**
     *
     * @param x
     * @param y
     * @param mapElement
     *
     * sets a space in the map[][] array to a certain object which is passed in
     */
    public void setMapElement(int x, int y, MapElement mapElement) {
        map[x][y] = mapElement;
    }

    /**
     *
     * @param x
     * @param y
     * @param mapElement sets a space in the diamondList[][] array to a certain
     * object which is passed in
     */
    public void setDiamondListElement(int x, int y, MapElement mapElement) {
        diamondList[x][y] = mapElement;

    }

    /**
     *
     * function to move the character up by passing numbers to move my into the
     * movement class
     */
    public void moveUp() {
        moveCrateCheck(0, -1);
    }

    /**
     * function to move the character right by passing numbers to move my into
     * the movement class
     */
    public void moveRight() {
        moveCrateCheck(1, 0);
    }

    /**
     * function to move the character down by passing numbers to move my into
     * the movement class
     */
    public void moveDown() {
        moveCrateCheck(0, 1);
    }

    /**
     * function to move the character left by passing numbers to move my into
     * the movement class
     */
    public void moveLeft() {
        moveCrateCheck(-1, 0);
    }

    
    /**
     *
     * @param moveX
     * @param moveY
     * 
     * class to handle player movement 
     */
    public void characterMove(int moveX, int moveY) {
        if (map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY] instanceof Wall) {  //check if are player wants to move to is a wall
            //instanceof was found here https://stackoverflow.com/questions/10531513/how-to-identify-object-types-in-java
            //dont move
        } else {
            Tile tile = new Tile();  //create a new tile object
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
            warehouseKeeper.keeperCoords.setX(warehouseKeeper.keeperCoords.getX() + moveX);                        //set the warehousekeepers new coordinates
            warehouseKeeper.keeperCoords.setY(warehouseKeeper.keeperCoords.getY() + moveY);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            diamondcheck();      
            winCheck();         
            warehouseKeeper.increaseMoveNo();    
        }
    }

    /**
     *
     * @param moveX
     * @param moveY class to check for crates check if the both the objects in
     * front of the player are crates or a crate and wall
     */
    public void moveCrateCheck(int moveX, int moveY) {

        MapElement elementAtCharacterDestination = map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY];
        MapElement elementAfterCharacterDestination = map[warehouseKeeper.keeperCoords.getX() + moveX * 2][warehouseKeeper.keeperCoords.getY() + moveY * 2];

        boolean objectaAtCharacterDestinationIsCrate = elementAtCharacterDestination instanceof Crate;
        boolean objectaAfterCharacterDestinationIsCrate = elementAfterCharacterDestination instanceof Crate;
        boolean objectaAfterCharacterDestinationIsWall = elementAfterCharacterDestination instanceof Wall;

        if (objectaAtCharacterDestinationIsCrate && objectaAfterCharacterDestinationIsWall
                || objectaAtCharacterDestinationIsCrate && objectaAfterCharacterDestinationIsCrate) {
        
            //dont move
        } else if (objectaAtCharacterDestinationIsCrate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX() + moveX * 2, warehouseKeeper.keeperCoords.getY() + moveY * 2, crate);
            characterMove(moveX, moveY);       //call the method to move the warehousekeeper
        } else {
            characterMove(moveX, moveY);
        }
    }

    /**
     * checks to see if a diamond should be inhabiting floor space instead of a
     * tile
     */
    public void diamondcheck() {
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 14; y++) {
                if (diamondList[x][y] instanceof Diamond && map[x][y] instanceof Tile) {
                    Diamond diamond = new Diamond();
                    setMapElement(x, y, diamond);
                }
            }
        }
    }

    /**
     * checks whether all the crates are on the diamonds
     */
    public void winCheck() {
        int NoOfCratesOnDiamonds = 0;
        int noOfDiamonds = 0;
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 14; y++) {
                if (diamondList[x][y] instanceof Diamond) {
                    noOfDiamonds++;
                }
            }
        }
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 14; y++) {
                if (diamondList[x][y] instanceof Diamond && map[x][y] instanceof Crate) {
                    NoOfCratesOnDiamonds++;
                    if (NoOfCratesOnDiamonds == noOfDiamonds) {
                        System.out.println("Congradulations You Win!");      //message for player
                        clearMap();                                           //clear the map[][] array
                        increaseLevelNo();                       //go to the next level
                        Level();                            //remake the map[][] array with the new map
                    }
                }
            }
        }
    }

    /**
     * increase the level number
     */
    public void increaseLevelNo() {
        if (levelNo < 5) {
            levelNo++;
        } else {
            System.out.println("This is the Last Level!");
            
        }
    }


    /**
     * clear the map array
     */
    public void clearMap() {
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 14; y++) {
                setMapElement(x, y, null);               //make every object in the array null
                setDiamondListElement(x, y, null);
            }
        }

    }
}
