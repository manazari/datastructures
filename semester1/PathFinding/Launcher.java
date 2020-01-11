/**
 * This is a tester class which
 * implements a program that opens
 * a new PixelWindow and runs a Game
 * onto that window from a few
 * examples of Entitys
 */
public class Launcher {
    public static void main(String args[]) {
        PixelGrid mapBluePrint = new PixelGrid("resources\\blueprint.png");
        GameMap map = GameMap.fromBluePrint(mapBluePrint);
        Game game = Game.startFromGameMap(map);

        PixelGrid[] chomperSprites = new PixelGrid("resources\\chomper.png").toSprites(2);
        game.addEntity(new GreedyBum(15, 5, chomperSprites));

        PixelGrid[] batSprites = new PixelGrid("resources\\bat.png").toSprites(2);
        game.addEntity(new Sheriff(8, 5, batSprites));
       
        PixelGrid[] mageSprites = new PixelGrid("resources\\mage.png").toSprites(2);
        game.addEntity(new Player(2, 5, mageSprites));

        // PixelGrid nodes = new PixelGrid("resources\\nodes.png");
        // GameMap map = new GameMap(nodes);
        // Game game = Game.startFromGameMap(map);

        // game.addEntity(new GreedyBum(15, 5));
        // game.addEntity(new Sheriff(8, 5));
        // game.addEntity(new Player(2, 5));

        
        PixelWindow.startFromGame(game);
        game.loop();
    }
}
