package aftergames.demo.game.inventory;

import aftergames.demo.game.inventory.items.Item;
import aftergames.engine.ui.UIImage;
import aftergames.engine.world.World;

import java.util.LinkedList;

/**
 *
 * @author KiQDominaN
 */
public class Inventory {

    private LinkedList<InventorySlot> slots = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private float max_capacity;
    private float used_capacity;

    public void setMaxWeight(float weight) {
        this.max_capacity = weight;
    }

    public float getMaxWeight() {
        return max_capacity;
    }

    public float getUsedWeight() {
        return used_capacity;
    }

    public void addItem(Item item) {
        if (used_capacity + item.weight <= max_capacity) {
            item.index = items.size() + 1;
            items.add(item);

            used_capacity += item.weight;
        }
    }

    public void useItem(int index, World loc) {
        if (index > items.size() || items.isEmpty() || items.get(index) == null) return;

        //Handle use here
        Item item = items.get(index);
        item.use();
        if (item.once_used)
            removeItem(index);
    }

    public void removeItem(int index) {
        items.remove(index);
        for (int i = index + 1; i < items.size(); i++) items.get(i).index--;
    }

    public void dropItem(int index, World loc) {
        items.get(index).drop();
        items.remove(index);
    }

    public UIImage getItemImage(int index) {
        if (index >= items.size() || items.isEmpty() || items.get(index) == null)
            return null;
        else
            return (items.get(index)).image;
    }

    public String getItemDescription(int index) {
        if (index >= items.size() || items.isEmpty() || items.get(index) == null)
            return "Описание отсутствует!";
        else
            return (items.get(index)).description;
    }
}
