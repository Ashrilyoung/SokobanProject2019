package sokobandriver;

/**
 *
 * @author 16007873
 */
public class Diamond extends MapElement {

    boolean hasCrate = false;

    private String filename = "resources/SokobanImages/Diamond.png";

    //thios creates the diamond element
    public void createElement(int x, int y) {
        Coordinate diamondCoords = new Coordinate();
        diamondCoords.setX(x);
        diamondCoords.setY(y);
    }

    //this will check whether or not the diamond has a crate
    public void setHasCrate() {

    }

    //this gets whether or not the diamond has a crate
    public boolean getHasCrate() {
        return hasCrate;
    }

    public String getFileName() {
        return filename;
    }
}
