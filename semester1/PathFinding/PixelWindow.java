import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * PixelWindow creates a JFrame
 * from a PixelCanvas which wraps
 * a PixelGrid that extends JPanel
 */
public class PixelWindow {
    private static PixelWindow instance = null;
    private final int PIXEL_SIZE, WIDTH, HEIGHT;
    private PixelCanvas canvas;

    /**
     * PixelGrid wraps a PixelGrid
     * and extends JPanel. It paints
     * by drawing a square for every
     * element and cell in the PixelGrid
     */
    protected class PixelCanvas extends JPanel {
        public PixelGrid pixels;

        public PixelCanvas() {
            pixels = new PixelGrid(PixelWindow.this.WIDTH, PixelWindow.this.HEIGHT);
        }

        public PixelCanvas(PixelGrid _pixels) {
            pixels = new PixelGrid(_pixels);
        }

        public void paint(Graphics g) {
            for(int x = 0; x < pixels.WIDTH; x++) 
                for (int y = 0; y < pixels.HEIGHT; y++) {
                    int pxlSze = PixelWindow.this.PIXEL_SIZE;
                    g.setColor(new Color(pixels.color[y][x]));;
                    g.fillRect(x * pxlSze, y * pxlSze, (x+1) * pxlSze, (y+1) * pxlSze);
                }            
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int x = 0; x < pixels.WIDTH; x++) 
                for (int y = 0; y < pixels.HEIGHT; y++) {
                    int pxlSze = PixelWindow.this.PIXEL_SIZE;
                    g.setColor(new Color(pixels.color[y][x]));;
                    g.fillRect(x * pxlSze, y * pxlSze, (x+1) * pxlSze, (y+1) * pxlSze);
                } 
        }
    }

    private PixelWindow(int pixelSize, int width, int height) {
        this(pixelSize, new PixelGrid(width, height));
    }

    private PixelWindow(int pixelSize, PixelGrid pixelGrid) {
        PIXEL_SIZE = pixelSize;
        WIDTH = pixelGrid.WIDTH;
        HEIGHT = pixelGrid.HEIGHT;
        canvas = new PixelCanvas(pixelGrid);


        JFrame frame = new JFrame("from PIXELGRID");
        canvas.setPreferredSize(new Dimension(PIXEL_SIZE * WIDTH, PIXEL_SIZE * HEIGHT));
        frame.add(canvas);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }

    public static PixelWindow start(int pixelSize, int width, int height) {
        if (instance != null) return instance;
        instance = new PixelWindow(Math.max(1, pixelSize), Math.max(1, width), Math.max(1, height));
        return instance;
    }

    public static PixelWindow startFromGame(Game game) {
        if (instance != null) return instance;
        final int LARGEST_WINDOW_SIDE_LENGTH = 1200;
        int pixelSize = LARGEST_WINDOW_SIDE_LENGTH / Math.max(game.map.pixels.WIDTH, game.map.pixels.HEIGHT);
        instance = new PixelWindow(pixelSize, game.map.pixels);
        instance.canvas.setFocusable(true);
        instance.canvas.addKeyListener(game);
        game.window = instance;
        game.paint();
        return instance;
    }

    public void fillPixel(int x, int y, int color) {
        if (x < 0 || x >= WIDTH) return;
        if (y < 0 || y >= HEIGHT) return;
        canvas.pixels.color[y][x] = color;
    }

    public void repaint() {
        canvas.repaint();
    }
}