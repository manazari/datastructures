import java.util.HashSet;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tester_Nazari {
    public static void main(String[] args) {
        System.out.println("Dab");
        Grid grid = new Grid(180, 120);
        grid.grid[10][10].walkable = false;

        JFrame frame = new JFrame("A* Path Finding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(8*grid.gridSizeX, 8*grid.gridSizeY));
        frame.add(new GridSketcher(grid));
        frame.pack();

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}

class GridSketcher extends JPanel {
    private Grid grid;
    private Point lastPoint;
    private int nodeSize;
    private int guiWidth;
    private int guiHeight;
    
    public GridSketcher(Grid grid) {
        this.grid = grid;
        nodeSize = 8;
        guiWidth = nodeSize * grid.gridSizeX;
        guiHeight = nodeSize * grid.gridSizeY;
    
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastPoint = gridPoint(e.getX(), e.getY());
                grid.grid[lastPoint.y][lastPoint.x].toggleWalkable();
                block(lastPoint.x, lastPoint.y, getGraphics());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point nextPoint = gridPoint(e.getX(), e.getY());
                double deltaY = nextPoint.y - lastPoint.y;
                double deltaX = nextPoint.x - lastPoint.x;
                double slope = deltaY/deltaX;
                double increment = 5.0;
                for (double i = 0.0; i <= increment; i++) {
                    int blockX = lastPoint.x + (int)(deltaX/increment*i);
                    int blockY = lastPoint.y + (int)(deltaY/increment*i);
                    if (lastPoint.x == blockX && lastPoint.y == blockY) continue;
                    
                    grid.grid[blockY][blockX].toggleWalkable();                    
                    // grid.grid[blockY][blockX].walkable = true;
                    block(blockX, blockY, getGraphics());
                    lastPoint = new Point(blockX, blockY);
                }
                lastPoint = nextPoint;
                System.out.println("Delta x,y: " + deltaX + ", " + deltaY);
            }
        });
    }

    public Point gridPoint(int x, int y) {
        return new Point(x/nodeSize, y/nodeSize);
    }

    public void block(int x, int y, Graphics g) {
        g.setColor(Color.white);
        if (!grid.grid[y][x].walkable) g.setColor(Color.black);
        g.fillRect(x*nodeSize, y*nodeSize, nodeSize, nodeSize);
    }

    public void paint(Graphics g) {
        for (int y = 0; y < grid.gridSizeY; y++)
            for (int x = 0; x < grid.gridSizeX; x++) block(x, y, g);
    }
}

class Node {
    public boolean walkable;
    public int gridX;
    public int gridY;

    public int gCost;
    public int hCost;
    public Node parent;

    public Node(boolean walkable, int gridX, int gridY) {
        this.walkable = walkable;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int fCost() {
        return gCost + hCost;
    }

    public void toggleWalkable() {
        walkable = !walkable;
    }
}

class Grid {
    Node[][] grid;
    int gridSizeX;
    int gridSizeY;

    public Grid(int gridSizeX, int gridSizeY) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        grid = new Node[gridSizeY][gridSizeX];

        for (int y = 0; y < gridSizeY; y++)
            for (int x = 0; x < gridSizeX; x++)
                grid[y][x] = new Node(true, x, y);
    }

    public ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;

                int checkX = node.gridX + x;
                int checkY = node.gridY + y;

                if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY) {
                    neighbors.add(grid[checkY][checkX]);
                }
            }
        }

        return neighbors;
    }
}

class Pathfinding {
    Grid grid;
    ArrayList<Node> path;

    public void FindPath(Node startNode, Node targetNode) {
        ArrayList<Node> openSet = new ArrayList<Node>();
        HashSet<Node> closedSet = new HashSet<Node>();
        openSet.add(startNode);

        while(!openSet.isEmpty()) {
            Node currentNode = openSet.get(0);
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).fCost() < currentNode.fCost() || openSet.get(i).fCost() == currentNode.fCost() && openSet.get(i).hCost < currentNode.hCost) {
                    currentNode = openSet.get(i);
                }
            }
            openSet.remove(currentNode);
            closedSet.add(currentNode);

            if (currentNode == targetNode) {
                return;
            }

            for (Node neighbor : grid.getNeighbors(currentNode)) {
                if (!neighbor.walkable || closedSet.contains(neighbor)) {
                    continue;
                }

                int newMovementCostNeighbor = currentNode.gCost + GetDistance(currentNode, neighbor);
                if (newMovementCostNeighbor < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = newMovementCostNeighbor;
                    neighbor.hCost = GetDistance(neighbor, targetNode);
                    neighbor.parent = currentNode;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

    }

    void RetracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = endNode;

        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        for (int i = 0; i < path.size()/2; i++) {
            int rightSideIndex = path.size()-1 - i;
            Node left = path.get(i);
            path.set(i, path.get(rightSideIndex));
            path.set(rightSideIndex, left);
        }
    }

    int GetDistance(Node nodeA, Node nodeB) {
        int distX = Math.abs(nodeA.gridX - nodeB.gridX);
        int distY = Math.abs(nodeA.gridY - nodeB.gridY);

        if (distX > distY) return 14*distY + 10*(distX - distY);
        return 14*distX + 10*(distY - distX);
    }
}