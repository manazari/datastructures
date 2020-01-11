import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is a stupid attempt
 * by me to improve efficiency by
 * making my own image class. This
 * class is a wrapper for a 2D
 * array of RGB compressed colors.
 * This class also will interpret png
 * files and create pixels or divy a png
 * into array of PixelGrids which this
 * program uses as sprites
 */
public class PixelGrid {
    public final int WIDTH, HEIGHT;
    public int[][] color;

    /**
     * Default constructor will create
     * a PixelGrid of white cells of
     * width and height
     * @param width of grid
     * @param height of grid 
     */
    public PixelGrid(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        color = new int[HEIGHT][WIDTH];
        for(int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                color[y][x] = Color.WHITE.getRGB();
            }
        }
    }

    /**
     * Constructor for interpretting
     * a png file by converting it
     * to a PixelGrid of the same
     * height and width
     * @param filePath
     */
    public PixelGrid(String filePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch(IOException e) {
            System.out.println("Can't create PixelGrid from file path [" + filePath + "]!!!\n" + e);
        }

        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        color = new int[HEIGHT][WIDTH];
        
        for(int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                color[y][x] = image.getRGB(x, y);
            }
        }
    }

    /**
     * This constructor will just
     * duplicate a PixelGrid object
     * @param pixelGrid grid to copy
     */
    public PixelGrid(PixelGrid pixelGrid) {
        WIDTH = pixelGrid.WIDTH;
        HEIGHT = pixelGrid.HEIGHT;
        color = new int[HEIGHT][WIDTH];

        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                color[y][x] = pixelGrid.color[y][x];
    }

    /**
     * Will divy up a PixelGrid into
     * an array of multiple PixelGrids
     * based on a specified number
     * to split the PixelGrid vertically
     * @param numberOfSprites 
     * @return PixelGrid[]
     */
    public PixelGrid[] toSprites(int numberOfSprites) {
        // if (HEIGHT % numberOfSprites != 0)  ;
        // TODO throw exception if not evenly divisble
        PixelGrid[] sprites = new PixelGrid[numberOfSprites];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new PixelGrid(WIDTH, HEIGHT / numberOfSprites);
            for (int x = 0; x < sprites[i].WIDTH; x++) {
                for (int y = 0; y < sprites[i].HEIGHT; y++) {
                    sprites[i].color[y][x] = color[HEIGHT / numberOfSprites * i + y][x];
                }
            }
        }
        return sprites;
    }
}