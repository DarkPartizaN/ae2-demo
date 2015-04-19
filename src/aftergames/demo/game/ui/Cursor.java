package aftergames.demo.game.ui;

import aftergames.engine.Runtime;
import aftergames.engine.render.Renderer;
import aftergames.engine.render.Color;
import aftergames.engine.ui.UICursor;

/**
 *
 * @author KiQDominaN
 */
public class Cursor extends UICursor {

    public void draw() {
        if (custom_cursor == null) {
            //Draw crosshair
            Renderer.drawPoint(Runtime.getMouseX(), Runtime.getMouseY(), Color.white);

            Renderer.drawStraight(Runtime.getMouseX() - (size + indent), Runtime.getMouseY(), size, 0, color);
            Renderer.drawStraight(Runtime.getMouseX() + indent + 1, Runtime.getMouseY(), size, 0, color);

            Renderer.drawStraight(Runtime.getMouseX() + 1, Runtime.getMouseY() - (size + indent), 0, size, color);
            Renderer.drawStraight(Runtime.getMouseX() + 1, Runtime.getMouseY() + indent, 0, size, color);
        } else
            //Draw custom cursor
            Renderer.drawImage(custom_cursor.getImage(), (int) Runtime.getMouseX() - custom_cursor.getWidth() / 2, (int) Runtime.getMouseY() - custom_cursor.getHeight() / 2);
    }

}
