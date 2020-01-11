/**
 * This class is a wrapper for
 * a PixelGrid and a NodeGrind and
 * if created from a "blue print"
 * creates a NodeGrid which 
 * corresponds to a PixelGrid
 * 
 */
public class GameMap {
    public PixelGrid pixels;
    public NodeGrid nodes;

    /**
     * Default constructor creates
     * a nodeGrid from the PixelGrid
     * passed in
     * @param _pixels
     */
    public GameMap(PixelGrid _pixels) {
        pixels = _pixels;
        nodes = new NodeGrid(pixels);
    }

    /**
     * This method returns a GameMap
     * when fed a single PixelGrid that
     * represents both the PixelGrids for
     * a NodeGrid and for the screen to
     * display
     * @param bluePrint 
     * @return GameMap built from blue print
     */
    public static GameMap fromBluePrint(PixelGrid bluePrint) {
        PixelGrid[] pixelsAndNodes = new PixelGrid[2];
        pixelsAndNodes[0] = new PixelGrid(bluePrint.WIDTH, bluePrint.HEIGHT / 2);
        pixelsAndNodes[1] = new PixelGrid(bluePrint.WIDTH, bluePrint.HEIGHT / 2);

        for (int i = 0; i < pixelsAndNodes.length; i++) {
            for (int x = 0; x < bluePrint.WIDTH; x++) {
                for (int y = 0; y < bluePrint.HEIGHT / 2; y++) {
                    pixelsAndNodes[i].color[y][x] = bluePrint.color[i*bluePrint.HEIGHT/2 + y][x];
                }
            }
        }
        GameMap map = new GameMap(pixelsAndNodes[0]);
        map.nodes = new NodeGrid(pixelsAndNodes[1]);
        return map;
    }   
}

