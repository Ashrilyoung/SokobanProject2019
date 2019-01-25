package sokobandriver;

/**
 *
 * @author 16007873
 *
 * class to create a wall object
 */
public class Wall extends MapElement {

    private String filename = "resources/SokobanImages/Wall.png";

    /**
     *
     * @return returns the file path for the object so it can be used in another
     * class
     */
    public String getFileName() {
        return filename;
    }
}
