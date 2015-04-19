package aftergames.demo.game.inventory;

import java.util.LinkedList;

import aftergames.demo.game.inventory.items.Item;
import aftergames.engine.world.World;

/**
 *
 * @author KiQDominaN
 */
public class InventorySlot {

    private LinkedList<Item> items;

    public void use(World world) {
        if (!items.isEmpty()) items.getLast().use();
    }

    public int getCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(Item i) {
        items.add(i);
    }

}
