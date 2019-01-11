package sokobandriver;

/**
 *
 * @author 16007873
 */
public class Crate extends MoveableMapElement {

    private String filename = "resources/SokobanImages/Crate.png";  //file location for crate 

    //creates the element
    public void createElement(int x, int y) {
        Coordinate crateCoords = new Coordinate();
        crateCoords.setX(x);
        crateCoords.setY(y);
    }

    //returns the file name so it can be used
    public String getFileName() {
        return filename;
    }
}
