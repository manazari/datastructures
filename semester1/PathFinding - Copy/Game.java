import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.ArrayList;

public class Game implements KeyListener{
    private static Game instance = null;
    public PixelWindow window;
    public GameMap map;
    public HashMap<Integer, Boolean> keysHeld;
    public Player player;
    private ArrayList<Tracker> trackers;
    public boolean paused;
    protected final int PAUSE_KEY = KeyEvent.VK_SPACE;
    public int frame;
    
    private Game(GameMap _map) {
        map = _map;
        keysHeld = new HashMap<>();
        trackers = new ArrayList<>();
        paused = keysHeld.containsKey(PAUSE_KEY) && keysHeld.get(PAUSE_KEY);
        frame = 0;
    }

    public static Game startFromGameMap(GameMap _map) {
        if (instance != null) return instance;
        instance = new Game(_map);
        return instance;
    }

    public void addEntity(Entity entity) {
        entity.assignToGame(instance);
        if (entity instanceof Player)  {
            player = (Player) entity;
            for (Tracker tracker : trackers) tracker.playerReference = player;
        }
        else if (entity instanceof Tracker) trackers.add((Tracker) entity);
    }

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }

    public void update() {
        // ! check to see if window is null
        player.update();
        for (int i = 0; i < trackers.size(); i++) {
            trackers.get(i).update();
        }
    }

    public void paint() {
        for (int x = 0; x < map.pixels.WIDTH; x++)
            for (int y = 0; y < map.pixels.HEIGHT; y++) 
                window.fillPixel(x, y, map.pixels.color[y][x]);
        
        if (Boolean.TRUE.equals(keysHeld.get(KeyEvent.VK_SHIFT)))
            for (int i = 0; i < trackers.size(); i++)
                for (NodeGrid.Location node : trackers.get(i).path)
                    window.fillPixel(node.x, node.y, Color.GRAY.getRGB());

        for (int i = 0; i < trackers.size(); i++)
            trackers.get(i).paint();

        player.paint();

        window.repaint();
    }

    public void loop() {
        // while (true) {
        //     update();
        //     paint();
        //     try {
        //         Thread.sleep(300);
        //     } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }  
        // }
        update();
        paint();
        frame++;
    }


    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        keysHeld.put(e.getKeyCode(), true);
        // if (e.getKeyCode() == PAUSE_KEY) paused = true;
        if (e.getKeyCode() == PAUSE_KEY) loop();
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) paint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysHeld.put(e.getKeyCode(), false);
        if (e.getKeyCode() == PAUSE_KEY) paused = false;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) paint();
    }
}