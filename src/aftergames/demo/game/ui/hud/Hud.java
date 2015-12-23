package aftergames.demo.game.ui.hud;

import aftergames.demo.DemoControls;
import aftergames.demo.game.npc.Hero;
import aftergames.demo.game.objects.*;
import aftergames.demo.game.ui.*;
import aftergames.engine.*;
import aftergames.engine.render.*;
import aftergames.engine.ui.*;
import aftergames.engine.utils.*;
import aftergames.engine.utils.geom.*;
import aftergames.engine.world.Entity;

/**
 *
 * @author DominaN
 */
public class Hud extends UIPanel {

    //Actor
    private Hero hero;
    //HUD elements
    private HudClock clock;
    private BottomPopup explore_popup;
    private UIText money;
    //Custom cursor
    private HudCursor custom_cursor;
    private UIImage explore_cursor, axe_cursor;
    private float cursor_offset = 5;

    public void onCreate() {
        setLayer(1); //Show under menu
        setSize(EngineRuntime.screen_width, EngineRuntime.screen_height);

        explore_popup = new BottomPopup();

        clock = new HudClock();
        clock.init();

        money = new UIText();
        money.setPosition(0, clock.getY() + clock.getHeight() + 4);
        money.setColor(Color.green);

        add(explore_popup);
        add(clock);
        add(money);

        custom_cursor = new HudCursor();
        explore_cursor = new UIImage(ResourceUtils.load_image("gui/cursors/explore_cursor.png"));
        axe_cursor = new UIImage(ResourceUtils.load_image("gui/cursors/axe_cursor.png"));

        setCursor(custom_cursor);
    }

    public void onDraw() {
        //Draw life & stamina
        int max_bar_width = 192;
        int bar_height = 16;

        if (hero != null) {
            //Stamina
            Renderer.fillRect(4, EngineRuntime.screen_height - bar_height - 4, (int) (max_bar_width * hero.stamina), bar_height, Color.blue);
            Renderer.fillRect(4, EngineRuntime.screen_height - bar_height - 4, max_bar_width, bar_height, Color.blue.mix(Color.transparent, 0.7f));
            Renderer.drawRect(4, EngineRuntime.screen_height - bar_height - 4, max_bar_width, bar_height, Color.blue.mix(Color.white));
            //Life
            Renderer.fillRect(4, EngineRuntime.screen_height - bar_height * 2 - 8, (int) (max_bar_width * hero.life), bar_height, Color.red);
            Renderer.fillRect(4, EngineRuntime.screen_height - bar_height * 2 - 8, max_bar_width, bar_height, Color.red.mix(Color.transparent, 0.7f));
            Renderer.drawRect(4, EngineRuntime.screen_height - bar_height * 2 - 8, max_bar_width, bar_height, Color.red.mix(Color.white));
            //Money
            money.setText(StringUtils.concat(hero.money, "$"));
            money.setX(getWidth() - money.getWidth() - 4);
        }
    }

    public void onKeyPressed() {
        if (EngineRuntime.console.isActive()) return;

        hero = (Hero) EngineAPI.getWorld().getActor();

        if (EngineRuntime.keyPressed(DemoControls.MOVE_FORWARD)) hero.setTask(hero.task_walk);
        if (EngineRuntime.keyPressed(DemoControls.MOVE_BACKWARD)) hero.setTask(hero.task_walk_back);

        if (EngineRuntime.keyPressed(DemoControls.MOVE_FORWARD) && EngineRuntime.keyPressed(DemoControls.SPRINT)) hero.setTask(hero.task_run);
        else EngineRuntime.resetKey(DemoControls.SPRINT);

        if (EngineRuntime.keyPressed(DemoControls.RELOAD)) {
            EngineRuntime.resetKey(DemoControls.RELOAD);

            Box box = new Box();
            box.init();
            box.rotateObject(MathUtils.random_int(0, 359));
            box.setWorldPosition(hero.getWorldX() + 128 * MathUtils.cos(hero.angle), hero.getWorldY() + 128 * MathUtils.sin(hero.angle));
            EngineAPI.addObject(box);
        }

        if (EngineRuntime.keyPressed(Controllable.ESC)) {
            UIFactory.main_menu.in_game = true;
            UIManager.show(UIFactory.main_menu);
        }
    }

    public void onIdle() {
        hero = (Hero) EngineAPI.getWorld().getActor();

        hero.rotateObject(MathUtils.angle(EngineRuntime.getWorldMouseY() - hero.getWorldY(), EngineRuntime.getWorldMouseX() - hero.getWorldX()));
        if (!EngineRuntime.keyPressed(Controllable.KEY_ANY)) hero.setTask(hero.task_idle);

        pickCursor();
    }

    private void pickCursor() {
        //Dynamic crosshair size
        float player_speed = EngineAPI.getWorld().getActor().current_speed.length() * 10;

        if (cursor_offset != player_speed) {
            if (cursor_offset < player_speed && cursor_offset < 25) cursor_offset += 0.55;
            if (cursor_offset > 5 && cursor_offset > player_speed) cursor_offset -= 0.55;
        }

        custom_cursor.setOffset((int) cursor_offset);
        custom_cursor.setImage(null); //We need reset custom image every frame!!!

        explore_popup.setText(StringUtils.concat("Press [", Controllable.getKeyName(DemoControls.USE), "] to use"));
        explore_popup.hide();

        for (Entity p : EngineAPI.getWorld().getVisible()) {
            if (EngineRuntime.worldMouseInRect((Rect) p.getBBOX()) && Shape.intersects(EngineAPI.getWorld().getActor().getBBOX(), p.getBBOX())) {
                if (p instanceof Box) {
                    custom_cursor.setImage(explore_cursor);

                    explore_popup.setText(p.name);
                    explore_popup.show();

                    if (EngineRuntime.keyPressed(DemoControls.USE))
                        EngineAPI.getWorld().removeObject(p);
                }
                if (p instanceof Tree) {
                    custom_cursor.setImage(axe_cursor);
                    explore_popup.show();
                }
            }
        }
    }
}