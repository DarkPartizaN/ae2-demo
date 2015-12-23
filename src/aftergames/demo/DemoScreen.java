package aftergames.demo;

import aftergames.demo.game.npc.*;
import aftergames.demo.game.objects.*;
import aftergames.demo.game.ui.UIFactory;
import aftergames.engine.*;
import aftergames.engine.render.Color;
import aftergames.engine.ui.UIManager;
import aftergames.engine.utils.MathUtils;
import aftergames.engine.world.*;

/**
 *
 * @author KiQDominaN
 */
public class DemoScreen extends Screen {

    public DemoScreen(int width, int height, boolean fullscreen) {
        super("After Engine 2 Demo", width, height, fullscreen);
    }

    public void init() {
        EngineAPI.createEngine();

        UIFactory.init();
        UIManager.add(UIFactory.main_menu);
    }

    public static void startGame() {
        UIManager.hide(UIFactory.main_menu);

        World world = new World();
        world.createDefaultLocation(50, 50, 64, 64, 4);

        Hero hero = new Hero();
        hero.init();
        hero.setWorldPosition(100, 100);

        world.getCamera().setTarget(hero);
        world.setActor(hero);
        world.addObject(hero);

        Tree tree;
        Box box;
        Light light;

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 2; j++) {
                Color c = new Color(MathUtils.random_color());
                c.r = MathUtils.max(c.r, 0.1f);
                c.g = MathUtils.max(c.g, 0.1f);
                c.b = MathUtils.max(c.b, 0.1f);
                c.a = 1f; //Max brightness

                light = new Light(Light.POINT);
                light.init();
                light.setColor(c);
                light.setDistance(MathUtils.random_int(64, 256));
                light.setWorldPosition(MathUtils.random_float(0, world.getWidth()), MathUtils.random_float(0, world.getHeight()));
                world.addObject(light);
            }

            if (MathUtils.random_chance() > 0.8f) {
                box = new Box();
                box.init();
                box.rotateObject(MathUtils.random_int(0, 359));
                box.setWorldPosition(MathUtils.random_float(0, world.getWidth() - box.getWidth()), MathUtils.random_float(0, world.getHeight() - box.getHeight()));
                box.name = "Box" + i;

                world.addObject(box);
            } else {
                tree = new Tree();
                tree.init();
                tree.rotateObject(MathUtils.random_int(0, 359));
                tree.setWorldPosition(MathUtils.random_float(0, world.getWidth() - tree.getWidth()), MathUtils.random_float(0, world.getHeight() - tree.getHeight()));
                tree.height = 2;

                world.addObject(tree);
            }
        }

        Civilian civ = new Civilian();
        civ.init();
        civ.setWorldPosition(200, 200);
        civ.setTarget(hero);

        world.addObject(civ);

        EngineAPI.createEngine();

        UIManager.add(UIFactory.hud);
        UIManager.show(UIFactory.hud);

        EngineAPI.startEngine(world);
    }
}
