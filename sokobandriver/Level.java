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
    private MapElement[][] map = new MapElement[25][15];
    WarehouseKeeper warehouseKeeper = new WarehouseKeeper();
    private Crate crate[];
    Coordinate coords = new Coordinate();

    //this will load the level
    public void Level() {

        File textFile = new File("src/resources/SokobanMaps/level1.txt");	// we pass the filename as a parameter to the constructor

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

    //movement controls and tests
    public void moveUp() {
        if (map[warehouseKeeper.keeperCoords.getX()][warehouseKeeper.keeperCoords.getY() - 1] instanceof Wall) {
            //dont move
        } else {
            moveUpCrateCheck();
//            if (map[warehouseKeeper.keeperCoords.getX()][warehouseKeeper.keeperCoords.getY() - 1] instanceof Diamond) {
//                Diamond diamond = new Diamond();
//                setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), diamond);
//            } else {
            Tile tile = new Tile();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
//            }
            warehouseKeeper.keeperCoords.setY(warehouseKeeper.keeperCoords.getY() - 1);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            System.out.println(warehouseKeeper.keeperCoords.getY());
            System.out.println(getMap());
        }
    }

    public void moveRight() {
        if (map[warehouseKeeper.keeperCoords.getX() + 1][warehouseKeeper.keeperCoords.getY()] instanceof Wall) {
            //dont move
        } else {
            moveRightCrateCheck();
            Tile tile = new Tile();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
            warehouseKeeper.keeperCoords.setX(warehouseKeeper.keeperCoords.getX() + 1);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            System.out.println(warehouseKeeper.keeperCoords.getY());
            System.out.println(getMap());
        }
    }

    public void moveDown() {
        if (map[warehouseKeeper.keeperCoords.getX()][warehouseKeeper.keeperCoords.getY() + 1] instanceof Wall) {
            //dont move
        } else {
            moveDownCrateCheck();
            Tile tile = new Tile();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
            warehouseKeeper.keeperCoords.setY(warehouseKeeper.keeperCoords.getY() + 1);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            System.out.println(warehouseKeeper.keeperCoords.getY());
            System.out.println(getMap());
        }
    }

    public void moveLeft() {
        if (map[warehouseKeeper.keeperCoords.getX() - 1][warehouseKeeper.keeperCoords.getY()] instanceof Wall) {
            //dont move
        } else {
            moveLeftCrateCheck();
            Tile tile = new Tile();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), tile);
            warehouseKeeper.keeperCoords.setX(warehouseKeeper.keeperCoords.getX() - 1);
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY(), warehouseKeeper);
            System.out.println(warehouseKeeper.keeperCoords.getY());
            System.out.println(getMap());
        }
    }

    // checks for crates
    public void moveUpCrateCheck() {
        if (map[warehouseKeeper.keeperCoords.getX()][warehouseKeeper.keeperCoords.getY() - 1] instanceof Crate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY() - 2, crate);
            System.out.println(getMap());

        } else {
        }
    }

    public void moveDownCrateCheck() {
        if (map[warehouseKeeper.keeperCoords.getX()][warehouseKeeper.keeperCoords.getY() + 1] instanceof Crate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX(), warehouseKeeper.keeperCoords.getY() + 2, crate);
            System.out.println(getMap());

        } else {
        }
    }

    public void moveRightCrateCheck() {
        if (map[warehouseKeeper.keeperCoords.getX() + 1][warehouseKeeper.keeperCoords.getY()] instanceof Crate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX() + 2, warehouseKeeper.keeperCoords.getY(), crate);
            System.out.println(getMap());

        } else {
        }
    }

    public void moveLeftCrateCheck() {
        if (map[warehouseKeeper.keeperCoords.getX() - 1][warehouseKeeper.keeperCoords.getY()] instanceof Crate) {
            //move crate
            Crate crate = new Crate();
            setMapElement(warehouseKeeper.keeperCoords.getX() + 2, warehouseKeeper.keeperCoords.getY(), crate);
            System.out.println(getMap());

        } else {
        }
    }

}
