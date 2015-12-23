package aftergames.demo.game.ui.hud;

import aftergames.engine.render.Renderer;
import aftergames.engine.ui.UICursor;

/**
 *
 * @author KiQDominaN
 */
public class HudCursor extends UICursor {

    private int size = 10, offset = 2;

    public void onDraw() {
        //Draw crosshair
        Renderer.drawPoint(x, y, color);

        Renderer.drawStraight(x - (size + offset), y, size, 0, color);
        Renderer.drawStraight(x + offset + 1, y, size, 0, color);

        Renderer.drawStraight(x, y - (size + offset), 0, size, color);
        Renderer.drawStraight(x, y + offset + 1, 0, size, color);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
