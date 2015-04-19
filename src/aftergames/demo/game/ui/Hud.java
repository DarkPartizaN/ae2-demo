package aftergames.demo.game.ui;

import aftergames.demo.game.npc.Hero;
import aftergames.demo.game.objects.Box;
import aftergames.demo.game.objects.Tree;
import aftergames.engine.Runtime;
import aftergames.engine.Controllable;
import aftergames.engine.geom.Shape;
import aftergames.engine.ui.GuiHud;
import aftergames.engine.ui.UIImage;
import aftergames.engine.utils.MathUtils;
import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.world.Entity;

/**
 *
 * @author DominaN
 */
public class Hud extends GuiHud {

    private Hero hero;
    private float cursor_indent = 5;

    //HUD elements
    private HudClock clock;
    private BottomPopup explore_popup;
    //Custom cursors
    private UIImage explore_cursor, axe_cursor;

    public void init() {
        explore_cursor = new UIImage(ResourceUtils.load_image("gui/hud/explore_cursor.png"));
        axe_cursor = new UIImage(ResourceUtils.load_image("gui/hud/axe_cursor.png"));

        explore_popup = new BottomPopup();
        explore_popup.init();
        explore_popup.setText("Press [".concat(Controllable.getKeyName(Controllable.USE)).concat("] to use"));

        clock = new HudClock();
        clock.init();

        addElement(explore_popup);
        addElement(clock);

        setCursor(new Cursor());
    }

    public void input() {
        hero = (Hero) Runtime.engine.getWorld().getActor();

        hero.rotateObject(MathUtils.angle(Runtime.getWorldMouseY() - hero.getWorldY(), Runtime.getWorldMouseX() - hero.getWorldX()));

        if (!Runtime.keyPressed(Controllable.KEY_ANY)) hero.setTask(hero.task_idle);
        if (Runtime.keyPressed(Controllable.MOVE_FORWARD)) hero.setTask(hero.task_walk);
        if (Runtime.keyPressed(Controllable.MOVE_BACKWARD)) hero.setTask(hero.task_walk_back);

        if (Runtime.keyPressed(Controllable.MOVE_FORWARD) && Runtime.keyPressed(Controllable.SPRINT)) hero.setTask(hero.task_run);
        else Runtime.resetKey(Controllable.SPRINT);

        if (Runtime.keyPressed(Controllable.FLASHLIGHT)) {
            hero.toggleFlashlight();
            Runtime.resetKey(Controllable.FLASHLIGHT);
        }
    }

    public void update() {
        super.update(); //It's important!!!

        pickCursor();
    }

    private void pickCursor() {
        //Dynamic cursor
        float player_speed = Runtime.engine.getWorld().getActor().current_speed.length() * 10;

        if (cursor_indent != player_speed) {
            if (cursor_indent < player_speed && cursor_indent < 25) cursor_indent += 0.55;
            if (cursor_indent > 5 && cursor_indent > player_speed) cursor_indent -= 0.55;
        }

        cursor.setIndent((int) cursor_indent);
        cursor.resetCustomImage(); //We need reset custom image every frame!!!

        explore_popup.hide(); //We need reset popup every frame! (yet, of course)

        for (Entity p : Runtime.engine.getWorld().getVisibleObjects()) {
            if (Runtime.worldMouseInRect(p.getBBOX()) && Shape.intersects(Runtime.engine.getWorld().getActor().getBBOX(), p.getBBOX())) {
                if (p instanceof Box) {
                    cursor.setCustomImage(explore_cursor);
                    explore_popup.show();
                }
                if (p instanceof Tree) {
                    cursor.setCustomImage(axe_cursor);
                    explore_popup.show();
                }
            }
        }
    }
}