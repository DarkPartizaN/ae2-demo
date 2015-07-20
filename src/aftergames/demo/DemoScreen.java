package aftergames.demo;

import aftergames.demo.game.npc.Hero;
import aftergames.demo.game.objects.Box;
import aftergames.demo.game.objects.Tree;
import aftergames.demo.game.ui.Hud;
import aftergames.engine.EngineAPI;
import aftergames.engine.Screen;
import aftergames.engine.render.Color;
import aftergames.engine.utils.MathUtils;
import aftergames.engine.world.Layer;
import aftergames.engine.world.Light;
import aftergames.engine.world.World;

/**
 *
 * @author KiQDominaN
 */
public class DemoScreen extends Screen {

    public DemoScreen(int width, int height, boolean fullscreen) {
        super("After Engine 2 Demo", width, height, fullscreen);
    }

    public void init() {
        World world = new World();
        world.createDefaultLocation(50, 50, 64, 64, 4);

        Layer layer_trees = new Layer();

        Hero hero = new Hero();
        hero.init();
        hero.setWorldPosition(200, 200);

        world.getCamera().setTarget(hero);
        world.setActor(hero);
        world.addObject(hero);

        Tree tree;
        Box box;
        Light light;

        for (int i = 0; i < 50; i++) {
            Color c = MathUtils.random_color();
            c.a = 1f; //Max brightness

            light = new Light(Light.POINT);
            light.init();
            light.setColor(c);
            light.setDistance(MathUtils.random_int(64, 256));
            light.setWorldPosition(MathUtils.random_float(0, world.getWidth()), MathUtils.random_float(0, world.getHeight()));
            world.addObject(light);

            if (MathUtils.random_chance() > 0.8f) {
                box = new Box();
                box.init();
                box.rotateObject(MathUtils.random_int(0, 359));
                box.setWorldPosition(MathUtils.random_float(0, world.getWidth() - box.getWidth()), MathUtils.random_float(0, world.getHeight() - box.getHeight()));
                box.name = "Box" + (i + 1000);

                world.addObject(box);
            } else {
                tree = new Tree();
                tree.init();
                tree.rotateObject(MathUtils.random_int(0, 359));
                tree.setWorldPosition(MathUtils.random_float(0, world.getWidth() - tree.getWidth()), MathUtils.random_float(0, world.getHeight() - tree.getHeight()));

                layer_trees.add(tree);
            }
        }

        world.addLayer(layer_trees);

        EngineAPI.createEngine(world);

        Hud hud = new Hud();
        hud.init();

        EngineAPI.setHUD(hud);
        EngineAPI.startEngine();
    }
}
