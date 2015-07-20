package aftergames.demo.game.objects;

import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.world.Model;
import aftergames.engine.world.Entity;

/**
 *
 * @author KiQDominaN
 */
public class Box extends Entity {

    public void init() {
        movetype = MOVETYPE_STATIC;

        solid = true;
        visible = true;
        height = 2;

        model = new Model(ResourceUtils.load_image("objects/box.png"), 80, 80);

        setModel(model);
        setCollisionRect(0, 0, 80, 80);
    }
}
