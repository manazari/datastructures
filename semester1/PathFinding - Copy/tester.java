import java.awt.Color;

public class tester {
    public static void main(String args[]) {
        // PixelWindow window = PixelWindow.startWindow(10, 20, 20);
        PixelGrid mapBluePrint = new PixelGrid("resources\\blueprint.png");
        GameMap map = GameMap.fromBluePrint(mapBluePrint);
        Game game = Game.startFromGameMap(map);

        PixelGrid[] chomperSprites = new PixelGrid("resources\\chomper.png").toSprites(2);
        game.addEntity(new GreedyBum(15, 5, chomperSprites));

        PixelGrid[] batSprites = new PixelGrid("resources\\bat.png").toSprites(2);
        game.addEntity(new Sheriff(8, 5, batSprites));
       
        PixelGrid[] mageSprites = new PixelGrid("resources\\mage.png").toSprites(2);
        game.addEntity(new Player(2, 5, mageSprites));
        
        PixelWindow.startFromGame(game);
        game.loop();
    }
}
