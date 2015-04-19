package aftergames.demo.game.ui;

import aftergames.engine.Runtime;
import aftergames.engine.render.Color;
import aftergames.engine.ui.UIElement;
import aftergames.engine.ui.UIFont;
import aftergames.engine.ui.UIImage;
import aftergames.engine.ui.UIText;
import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.world.Time;

/**
 *
 * @author KiQDominaN
 */
public class HudClock extends UIElement {

    private UIText time;
    private UIImage clock;

    public void init() {
        UIFont time_font = UIFont.createGuiFont(ResourceUtils.load_ttf("TRANA.TTF", 18));

        setPosition(Runtime.screen_width - time_font.stringWidth(Time.timeOfDay) - 2, 2);

        time = new UIText();
        time.setFont(time_font);
        time.setColor(Color.red);
        time.setPosition(-1, 0);

        clock = new UIImage(ResourceUtils.load_image("gui/hud/clock.png"));
        clock.setPosition(-8, 0);

        addElement(clock);
        addElement(time);
    }

    public void update() {
        time.setText(Time.timeOfDay);
    }
}
