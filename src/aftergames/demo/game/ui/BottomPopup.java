package aftergames.demo.game.ui;

import aftergames.engine.EngineRuntime;
import aftergames.engine.render.Color;
import aftergames.engine.render.Renderer;
import aftergames.engine.ui.UIText;

/**
 *
 * @author KiQDominaN
 */
public class BottomPopup extends UIText {

    private static final int FADEIN = -1;
    private static final int FADEOUT = 1;
    private int fade = 0;

    private float fade_speed = 0.02f;

    public BottomPopup() {
        color = new Color(1, 1, 1, 0);
    }

    public void onDraw() {
        Renderer.drawString(text, getX(), getY(), color);
    }

    public void setText(String text) {
        this.text = text;

        setPosition(EngineRuntime.screen_width / 2 - font.stringWidth(text) / 2, EngineRuntime.screen_height - font.getHeight() - 8);
        setSize(font.stringWidth(text), font.getHeight());
    }

    public void onIdle() {
        if (fade == FADEIN) {
            fadein();
            return;
        }

        if (fade == FADEOUT) fadeout();
    }

    public void show() {
        fade = FADEIN;
    }

    public void hide() {
        fade = FADEOUT;
    }

    private void fadein() {
        color.a += fade_speed * EngineRuntime.frametime;

        if (color.a > 1) {
            color.a = 1;
            fade = 0;
        }
    }

    private void fadeout() {
        color.a -= fade_speed * EngineRuntime.frametime;

        if (color.a < 0) {
            color.a = 0;
            fade = 0;
        }
    }

    public void setFadeSpeed(float speed) {
        fade_speed = speed;
    }

}