import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PathSketcher extends JPanel {
    private Point lastPoint;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int BLOCK_SIZE = 20;
    public static final int GUI_WIDTH = BLOCK_SIZE * WIDTH;
    public static final int GUI_HEIGHT = BLOCK_SIZE * HEIGHT;
    
    public PathSketcher() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastPoint = new Point(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Graphics g = getGraphics();
                g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
                lastPoint = new Point(e.getX(), e.getY());
                g.dispose();
            }
        });
    }

    public void block(int x, int y, Graphics g) {
        g.setColor(Color.red);
        if (x == y) g.setColor(Color.green);
        g.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    public static void main(String[] args) {
        final int[] gridDim = new int[]{10, 10};

        JFrame frame = new JFrame("TANAY");
        // frame.getContentPane().add(new PathSketcher(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        frame.add(new PathSketcher());
        frame.pack();

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //System.out.println(frame.getContentPane().getSize());
    }

    public void paint(Graphics g) {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++) block(x, y, g);
    }
} 

// class Map extends JPanel {
//     Dimension dimension;
//     Node[][] mapstate;
//     JFrame frame;

//     public Map(JFrame frame, Dimension dimension) {
//         // mapstate = new Node[(int)dimensions.getHeight()][(int)dimensions.getWidth()];
//         // mapstate =
//         this.dimension = dimension;
//         this.frame = frame;
//         int w = 10, h = 10;
//     }

//     public void paint(Graphics g) {
//         g.setColor(Color.gray);
//         for (int i = 0; i < )
//         int buffer = PathSketcher.WINDOW_WIDTH / 200;
//         int w = PathSketcher.WINDOW_WIDTH, h = PathSketcher.WINDOW_HEIGHT;
        
//         Dimension nodeDimension = new Dimension((w-2*buffer)/dimension.width, (h-2*buffer)/dimension.height);
//         for (int i = 0, y = buffer; i < dimension.height; i ++, y += nodeDimension.height + buffer)
//             for (int j = 0, x = buffer; j < dimension.width; j++, x += nodeDimension.width + buffer)
//                 g.fillRect(x, y, nodeDimension.height, nodeDimension.width);
//     }
// }

// class Node {
//     public int g, h, f;
// }