package aftergames.demo.game.inventory.items;

import aftergames.engine.render.Texture;
import aftergames.engine.ui.UIImage;

/**
 *
 * @author KiQDominaN
 */
public class Item {

    public static final int ITEM_MEDIC = 0, ITEM_FOOD = 1, ITEM_CLOTHES = 2, ITEM_WEAPON = 3, ITEM_AMMO = 4, ITEM_SPECIAL = 5;
    public int index;
    public int type;//Type of item
    public boolean once_used;//If true, then item magically way disappears after using
    public int cost;
    public float drop_chance;//Chance of drop in corpses of enemy, in trade or other places
    public float weight;
    public UIImage image;//Image used by inventory GUI
    public String name, description;

    public void init() {
    }

    public void use() {
    }

    public void drop() {
    }

}
