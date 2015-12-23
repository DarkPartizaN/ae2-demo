package aftergames.demo.game.objects;

import aftergames.engine.model.Model;
import aftergames.engine.render.Material;
import aftergames.engine.utils.ResourceUtils;
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
        height = 0;

        Material mat = new Material();
        mat.diffuse = ResourceUtils.load_image("objects/box.png");

        model = new Model(mat, 80, 80);

        setModel(model);
        setCollisionRect(0, 0, 80, 80);
    }

}
