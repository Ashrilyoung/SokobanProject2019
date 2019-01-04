package sokobandriver;

/**
 *
 * @author 16007873
 */
public class Wall extends MapElement {

    private String filename = "resources/SokobanImages/Wall.png";

    //creates the element
    public void createElement(int x, int y) {
        Coordinate wallCoords = new Coordinate();
        wallCoords.setX(x);
        wallCoords.setY(y);
    }

    public String getFileName() {
        return filename;
    }
}
