package sokobandriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 16007873
 */
public class Level {

    private int numberOfMoves = 0;
    private MapElement[][] map = new MapElement[23][13];
    private WarehouseKeeper warehouseKeeper = new WarehouseKeeper();
    private MapElement[][] diamondList = new MapElement[23][13];
    private Coordinate coords = new Coordinate();
    private int levelNo = 1;

    //this will load the level
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
                            warehouseKeeper.createElement(x, y);
                            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
                            break;
                        case '*':
                            //crate
                            Crate crate = new Crate();
                            crate.createElement(x, y);
                            setMapElement(x, y, crate);
                            break;
                        case '.':
                            //diamond
                            Diamond diamond = new Diamond();
                            diamond.createElement(x, y);
                            setMapElement(x, y, diamond);
                            setDiamondListElement(x, y, diamond);
                            break;
                        case 'X':
                            //wall
                            Wall wall = new Wall();
                            wall.createElement(x, y);
                            setMapElement(x, y, wall);
                            break;
                        case ' ':
                            //tile
                            Tile tile = new Tile();
                            tile.createElement();
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

    //gets the number of moves
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    //checks whether the level is complete
    public boolean checkLevelComplete() {
        boolean levelComplete = false;
        return levelComplete;
    }

    public MapElement[][] getMap() {
        return map;
    }

    public void setMapElement(int x, int y, MapElement mapElement) {
        map[x][y] = mapElement;
    }

    public void setDiamondListElement(int x, int y, MapElement mapElement) {
        diamondList[x][y] = mapElement;
//        System.out.println(diamondList);

    }

    //movement controls and tests
    public void moveUp() {
        moveCrateCheck(0, -1);
    }

    public void moveRight() {
        moveCrateCheck(1, 0);
    }

    public void moveDown() {
        moveCrateCheck(0, 1);
    }

    public void moveLeft() {
        moveCrateCheck(-1, 0);
    }

    //class to handle player movement 
    public void characterMove(int moveX, int moveY) {
        if (map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY] instanceof Wall) {
            //instanceof was found here https://stackoverflow.com/questions/10531513/how-to-identify-object-types-in-java
            //dont move
        } else {
            Tile tile = new Tile();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
            warehouseKeeper.keeperCoords.setX(warehouseKeeper.keeperCoords.getX() + moveX);
            warehouseKeeper.keeperCoords.setY(warehouseKeeper.keeperCoords.getY() + moveY);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            diamondcheck();
            winCheck();
        }
    }

    // class to check for crates
    public void moveCrateCheck(int moveX, int moveY) {
        if (map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY] instanceof Crate
                && map[warehouseKeeper.keeperCoords.getX() + moveX * 2][warehouseKeeper.keeperCoords.getY() + moveY * 2] instanceof Wall
                || map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY] instanceof Crate
                && map[warehouseKeeper.keeperCoords.getX() + moveX * 2][warehouseKeeper.keeperCoords.getY() + moveY * 2] instanceof Crate) {
            //dont move
        } else if (map[warehouseKeeper.keeperCoords.getX() + moveX][warehouseKeeper.keeperCoords.getY() + moveY] instanceof Crate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX() + moveX * 2, warehouseKeeper.keeperCoords.getY() + moveY * 2, crate);
            characterMove(moveX, moveY);
        } else {
            characterMove(moveX, moveY);
        }
    }

    //checks to see if a diamond should be inhabiting floor space instead of a tile
    public void diamondcheck() {
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 13; y++) {
                if (diamondList[x][y] instanceof Diamond && map[x][y] instanceof Tile) {
                    Diamond diamond = new Diamond();
                    setMapElement(x, y, diamond);
                }
            }
        }
    }

    //checks whether all the crates are on the diamonds
    public void winCheck() {
        int NoOfCratesOnDiamonds = 0;
        int noOfDiamonds = 0;
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 13; y++) {
                if (diamondList[x][y] instanceof Diamond) {
                    noOfDiamonds++;
                }
            }
        }
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 13; y++) {
                if (diamondList[x][y] instanceof Diamond && map[x][y] instanceof Crate) {
                    NoOfCratesOnDiamonds++;
                    if (NoOfCratesOnDiamonds == noOfDiamonds) {
                        System.out.println("Congradulations You Win!");
                        clearMap();
                        setLevelNo();
                        Level();
                    }
                }
            }
        }
    }

    
    //increase the level number
    public void setLevelNo() {
        if (levelNo < 5) {
            levelNo++;
        } else {
            System.out.println("This is the Last Level!");
        }
    }

    
    //clear the map array
    public void clearMap() {
        for (int x = 0; x < 23; x++) {
            for (int y = 0; y < 13; y++) {
                setMapElement(x, y, null);
                setDiamondListElement(x, y, null);
            }
        }

    }
}
