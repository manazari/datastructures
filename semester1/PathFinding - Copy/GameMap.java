// *** make these privated so that you cannot update pixelgrid without nodeGrid

public class GameMap {
    public PixelGrid pixels;
    public NodeGrid nodes;

    public GameMap(PixelGrid _pixels) {
        pixels = _pixels;
        nodes = new NodeGrid(pixels);
    }

    public static GameMap fromBluePrint(PixelGrid bluePrint) {
        // TODO IF NOT DIVISBLE BY 2 EVENLY THROW ERROR
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

