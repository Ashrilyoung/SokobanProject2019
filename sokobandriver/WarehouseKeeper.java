package sokobandriver;

/**
 *
 * @author 16007873
 *
 * class to create a warehouseKeeper object and give it coordinates
 */
public class WarehouseKeeper extends MapElement {

    private String filename = "resources/SokobanImages/WarehouseKeeper.png";
    Coordinate keeperCoords = new Coordinate();
    private int numberOfMoves = 0;                          //counts number of moves

    /**
     *
     * @param x
     * @param y
     *
     * creates coordinates for the WarehouseKeeper
     */
    public void createElement(int x, int y) {
        keeperCoords.setX(x);
        keeperCoords.setY(y);
//        System.out.println(keeperCoords);
    }

    /**
     *
     * @return returns the file path for the object so it can be used in another
     * class
     */
    public String getFileName() {
        return filename;
    }

    /**
     *
     * @return gets the number of moves
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * increases the number of character moves
     */
    public void increaseMoveNo() {
        numberOfMoves++;
    }

    /**
     * resets the number of character moves
     */
    public void resetMoveNo() {
        numberOfMoves = 0;
    }
    
    
}
