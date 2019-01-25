package sokobandriver;

/**
 *
 * @author 16007873
 *
 * class to create a tile object
 */
public class Tile extends MapElement {

    private String filename = "resources/SokobanImages/Floor.png";

    /**
     *
     * @return returns the file path for the object so it can be used in another
     * class
     */
    public String getFileName() {
        return filename;
    }

}
