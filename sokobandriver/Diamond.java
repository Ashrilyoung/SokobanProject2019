package sokobandriver;

/**
 *
 * @author 16007873
 *
 * class to create diamond elements
 */
public class Diamond extends MapElement {

    private String filename = "resources/SokobanImages/Diamond.png";
    MapElement[][] diamondList = new MapElement[23][14];       //stores location of diamonds

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
     * @return returns the map[][] array allowing it to be read from another
     * class
     *
     */
    public MapElement[][] getDiamondList() {
        return diamondList;
    }

}
