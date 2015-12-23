package aftergames.demo.game.ui;

import aftergames.demo.game.ui.hud.Hud;
import aftergames.demo.game.ui.menu.MainMenu;
import aftergames.engine.ui.UICursor;
import aftergames.engine.ui.UIImage;
import aftergames.engine.utils.ResourceUtils;

/**
 *
 * @author KiQDominaN
 */
public class UIFactory {

    //Cursor
    public static UIImage default_cursor_image;
    public static UICursor default_cursor;
    //Menu
    public static MainMenu main_menu;
    //Hud
    public static Hud hud;

    public static void init() {
        default_cursor_image = new UIImage(ResourceUtils.load_image("gui/cursors/default_cursor.png"));

        default_cursor = new UICursor();
        default_cursor.setImage(default_cursor_image);

        hud = new Hud();

        main_menu = new MainMenu();
    }

}
