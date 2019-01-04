package sokobandriver;

/**
 *
 * @author 16007873
 */
public class WarehouseKeeper extends MoveableMapElement {

    private String filename = "resources/SokobanImages/WarehouseKeeper.png";
    Coordinate keeperCoords = new Coordinate();
    //creaes the warehouseKeeper

    public void createElement(int x, int y) {
        keeperCoords.setX(x);
        keeperCoords.setY(y);
//        System.out.println(keeperCoords);
    }

    public String getFileName() {
        return filename;
    }
}
