package sokobandriver;

/**
 *
 * @author 16007873
 * 
 * class to create crate objects
 */
public class Crate extends MapElement {

    private String filename = "resources/SokobanImages/Crate.png";  //file location for crate 

    /**
     *
     * @return
     * returns the file name so it can be used
     */
    public String getFileName() {
        return filename;
    }
}
