package aftergames.demo.game.objects;

import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.model.Model;
import aftergames.engine.render.Material;
import aftergames.engine.world.Entity;

/**
 *
 * @author KiQDominaN
 */
public class Tree extends Entity {

    public void init() {
        movetype = MOVETYPE_STATIC;

        solid = true;
        visible = true;
        height = 0; //On top of screen

        Material mat = new Material();
        mat.diffuse = ResourceUtils.load_image("objects/tree1.png");

        model = new Model(mat, 210, 210);

        setModel(model);
        setCollisionCircle(0, 0, 20, 20); //Tree trunk
    }

}
