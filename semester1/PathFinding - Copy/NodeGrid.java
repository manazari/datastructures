import java.util.ArrayList;
import java.awt.Color;

public class NodeGrid {
    public final int WIDTH, HEIGHT;
    public Location[][] location;
    public boolean[][] walkable;
    public boolean[][] closed;

    public class Location implements Comparable<Location> {
        public int x;
        public int y;
        public int aCost;
        public int bCost;
        public Location parent;
        public Location[] neighbors;

        protected Location(int _x, int _y) {
            x = _x;
            y = _y;
            aCost = 0;
            bCost = 0;
            parent = null;
            neighbors = null;
        }

        public int cost() {
            return aCost + bCost;
        }

        @Override
        public int compareTo(Location there) {
            if (there == null) return 2;
            if (cost() < there.cost()) return 1;
            if (cost() > there.cost()) return -1;
            return 0;
        }

        @Override
        public int hashCode() {
            return x | (y << 15);
        }
    } 

    protected enum NodeType {
        PATH(Color.WHITE.getRGB()),
        BARRIER(Color.BLACK.getRGB()),
        START(Color.BLUE.getRGB()),
        GOAL(Color.RED.getRGB());

        private int colorRGB;

        public int getRGB() {
            return colorRGB;
        }

        private NodeType(int rgb) {
            colorRGB = rgb;
        }
    }

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

    public static int distanceBetween(Location here, Location there) {
        // ! MAGIC NUMBERS
        int distX = here.x - there.x;
        if (distX < 0) distX *= -1;
        int distY = here.y - there.y;
        if (distY < 0) distY *= -1;

        if (distX > distY) return 14*distY + 10*(distX-distY);
        else return 14*distX + 10*(distY-distX);
    }

    // public Location[] neighborsOf(Location here) {
    //     Location[] possibleNeighbors = new Location[9];
    //     int neighborTally = 0;

    //     for (int x = here.x - 1; x - 1 < here.x + 1; x++)
    //         for (int y = here.y - 1; y - 1 < here.y + 1; y++) {
    //             if (x == 0 && y == 0) continue;
    //             if (!((x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT))) continue;
    //             if ((x != here.x && y != here.y) && !walkable[x][here.y] && !walkable[here.x][y]) continue;

    //             possibleNeighbors[neighborTally] = location[y][x];
    //             neighborTally++;
    //         }

    //     Location[] neighbors = new Location[neighborTally];
    //     for (int i = 0; i < neighbors.length; i++) neighbors[i] = possibleNeighbors[i];
    //     return neighbors;
    // }
    
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