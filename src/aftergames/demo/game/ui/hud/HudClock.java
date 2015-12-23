package aftergames.demo.game.ui.hud;

import aftergames.engine.EngineRuntime;
import aftergames.engine.render.Color;
import aftergames.engine.ui.UIFont;
import aftergames.engine.ui.UIImage;
import aftergames.engine.ui.UIItem;
import aftergames.engine.ui.UIText;
import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.world.Time;

/**
 *
 * @author KiQDominaN
 */
public class HudClock extends UIItem {

    private UIText time;
    private UIImage clock;

    public void init() {
        clock = new UIImage(ResourceUtils.load_image("gui/hud/clock.png"));

        setSize((int)(clock.getWidth() * 0.75f), clock.getHeight());
        setPosition(EngineRuntime.screen_width - getWidth(), 0);

        UIFont time_font = UIFont.createGuiFont("TRANA", 18);

        time = new UIText();
        time.setFont(time_font);
        time.setColor(Color.red);
        time.setText("00:00");
        time.setPosition(getWidth() / 2 - time.getWidth() / 2 + 3, getHeight() / 2 - time_font.getHeight() / 2 - 1);

        add(clock);
        add(time);
    }

    public void onIdle() {
        time.setText(Time.timeOfDay);
    }
}
