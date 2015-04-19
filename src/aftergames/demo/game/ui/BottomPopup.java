package aftergames.demo.game.ui;

import aftergames.engine.Runtime;
import aftergames.engine.render.Color;
import aftergames.engine.render.Renderer;
import aftergames.engine.ui.UIText;

/**
 *
 * @author KiQDominaN
 */
public class BottomPopup extends UIText {

    protected final static int FADEIN = 0, FADEOUT = 1, IDLE = 2;

    public void init() {
        color = new Color(1, 1, 1, 0);
        state = IDLE;
    }

    public void draw() {
        Renderer.drawString(text, getX(), getY(), color);
    }

    public void setText(String text) {
        this.text = text;

        setPosition(Runtime.screen_width / 2 - font.stringWidth(text) / 2, Runtime.screen_height - font.getHeight() - 8);
        setSize(font.stringWidth(text), font.getHeight());
    }

    public void update() {
        switch (state) {
            case FADEIN:
                fadein();
                break;
            case FADEOUT:
                fadeout();
                break;
        }
    }

    public void show() {
        state = FADEIN;
    }

    public void hide() {
        state = FADEOUT;
    }

    private void fadein() {
        color.a += 0.02;

        if (color.a > 1) {
            color.a = 1;
            state = IDLE;
        }
    }

    private void fadeout() {
        color.a -= 0.02;

        if (color.a < 0) {
            color.a = 0;
            state = IDLE;
        }
    }

}
