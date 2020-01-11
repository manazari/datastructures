import java.awt.Color;

/**
 * This class creates a virtual
 * grid of nodes which is created
 * from three 2D arrays to improve
 * efficiency: walkable, location,
 * and closed which are all used
 * by Trackers to pathfind
 */
public class NodeGrid {
    public final int WIDTH, HEIGHT;
    public Location[][] location;
    public boolean[][] walkable;
    public boolean[][] closed;

    /**
     * This class is a wrapper for
     * a coordinate as well as that
     * coordinate's neighbors on the
     * grid. This class also maintains
     * up to two fields for cost. Every
     * location has a parent which helps
     * track the history of a path
     */
    public class Location implements Comparable<Location> {
        public int x;
        public int y;
        public int aCost;
        public int bCost;
        public Location parent;
        public Location[] neighbors;

        /**
         * Default constructor initializes
         * all fields
         * @param _x spawn x
         * @param _y spawn y
         */
        protected Location(int _x, int _y) {
            x = _x;
            y = _y;
            aCost = 0;
            bCost = 0;
            parent = null;
            neighbors = null;
        }

        /**
         * This method alculates total cost
         * @return total cost
         */
        public int cost() {
            return aCost + bCost;
        }

        /**
         * This method is a custom 
         * compareTo to improve efficiency
         * based on the cost of the
         * locations
         */
        @Override
        public int compareTo(Location there) {
            if (there == null) return 2;
            if (cost() < there.cost()) return 1;
            if (cost() > there.cost()) return -1;
            return 0;
        }

        /**
         * This method is a custom
         * hashchode of a location based
         * on their coordinate. A bitwise
         * operator is used to combine
         * the numbers into a unique int
         */
        @Override
        public int hashCode() {
            return x | (y << 15);
        }
    } 

    /**
     * This enum is used to
     * verbosely store the types of
     * nodes which can be read from
     * a PixelGrid when being converted
     * into a NodeGrid
     */
    protected enum NodeType {
        PATH(Color.WHITE.getRGB()),
        BARRIER(Color.BLACK.getRGB());

        private int colorRGB;

        public int getRGB() {
            return colorRGB;
        }

        private NodeType(int rgb) {
            colorRGB = rgb;
        }
    }

    /**
     * Default constructor initializes
     * every value and populates the
     * location array with Locations
     * and assign to them their neighbors
     * @param width 
     * @param height
     */
    public NodeGrid(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        location = new Location[HEIGHT][WIDTH];
        walkable = new boolean[HEIGHT][WIDTH];
        closed = new boolean[HEIGHT][WIDTH];

        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                location[y][x] = new Location(x, y);
                closed[y][x] = false;
                walkable[y][x] = true;

            }
        
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int neighborCount = 0;
                Location[] tempNeighbors = new Location[9];
                for (int u = x - 1; u - 1 < x + 1; x++)
                    for (int v = y - 1; v - 1 < y + 1; v++) {
                        if (u == x && v == x) continue;
                        if ((u < 0 || u >= WIDTH) || (v < 0 || v >= HEIGHT)) continue;
                        if (!walkable[v][u]) continue;
                        if ((u != x && v != y) && !walkable[y][u] && !walkable[v][x]) continue;
                        tempNeighbors[neighborCount] = location[v][u];
                        neighborCount++;
                    }
                location[y][x].neighbors = new Location[neighborCount];
                for (int i = 0; i < location[y][x].neighbors.length; i ++)
                    location[y][x].neighbors[i] = tempNeighbors[i];
            } 
    }

    public NodeGrid(PixelGrid pixels) {
        WIDTH = pixels.WIDTH;
        HEIGHT = pixels.HEIGHT;
        location = new Location[HEIGHT][WIDTH];
        walkable = new boolean[HEIGHT][WIDTH];
        closed = new boolean[HEIGHT][WIDTH];

        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                location[y][x] = new Location(x, y);
                closed[y][x] = false;  
                if (pixels.color[y][x] == NodeType.BARRIER.getRGB()) walkable[y][x] = false;
                else walkable[y][x] = true;  
            }
        
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int neighborCount = 0;
                Location[] tempNeighbors = new Location[9];
                for (int u = x - 1; u - 1 < x + 1; u++)
                    for (int v = y - 1; v - 1 < y + 1; v++) {
                        if (u == x && v == x) continue;
                        if ((u < 0 || u >= WIDTH) || (v < 0 || v >= HEIGHT)) continue;
                        if (!walkable[v][u]) continue;
                        if ((u != x && v != y) && !walkable[y][u] && !walkable[v][x]) continue;
                        tempNeighbors[neighborCount] = location[v][u];
                        neighborCount++;
                    }
                location[y][x].neighbors = new Location[neighborCount];
                for (int i = 0; i < location[y][x].neighbors.length; i ++)
                    location[y][x].neighbors[i] = tempNeighbors[i];
            } 
    }

    /**
     * This method calculates the
     * "Manhattan" distance between
     * two nodes on a grid which in
     * contrast to absolute distance
     * is the shortest distance when
     * only turns of 90 degrees is
     * allowed
     * @param here first node
     * @param there second node
     * @return distance between two nodes
     */
    public static int distanceBetween(Location here, Location there) {
        int distX = here.x - there.x;
        if (distX < 0) distX *= -1;
        int distY = here.y - there.y;
        if (distY < 0) distY *= -1;

        int diagDist = 14;
        int adjDist = 10;
        if (distX > distY) return diagDist*distY + adjDist*(distX-distY);
        else return diagDist*distX + adjDist*(distY-distX);
    }

    /**
     * This method will reset the
     * cost of each virtual node to
     * 0 as well as set the parent of
     * every node's Location to null
     */    
    public void reset() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                location[y][x].aCost = 0;
                location[y][x].bCost = 0;
                location[y][x].parent = null;
                closed[y][x] = false;
            }
    }
}