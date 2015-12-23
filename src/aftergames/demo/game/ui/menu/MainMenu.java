package aftergames.demo.game.ui.menu;

import aftergames.demo.DemoScreen;
import aftergames.demo.game.ui.*;
import aftergames.engine.*;
import aftergames.engine.render.*;
import aftergames.engine.ui.*;
import aftergames.engine.utils.*;

/**
 *
 * @author KiQDominaN
 */
public class MainMenu extends UIPanel {

    public boolean in_game = false;

    private UIButton continue_game;
    private UIButton new_game;
    private UIButton settings;
    private UIButton exit;

    private BottomPopup popup_hint;

    private ConfigMessage config_msg;
    private ExitMessage exit_msg;

    public void onCreate() {
        setSize(EngineRuntime.screen_width, EngineRuntime.screen_height);
        setBackground(new UIImage(ResourceUtils.load_image("launcher/cloth_background.png")));
        resizeBackground(false);
        setCursor(UIFactory.default_cursor);

        init_buttons();

        popup_hint = new BottomPopup();

        config_msg = new ConfigMessage();
        config_msg.setPosition(width / 2 - config_msg.getWidth() / 2, height / 2 - config_msg.getHeight() / 2);

        exit_msg = new ExitMessage();
        exit_msg.setPosition(width / 2 - exit_msg.getWidth() / 2, height / 2 - exit_msg.getHeight() / 2);

        add(popup_hint);
        add(config_msg);
        add(exit_msg);
    }

    private void init_buttons() {
        continue_game = new UIButton() {
            public void onFocus() {
                setLabelColor(Color.white);

                popup_hint.setText("Continue current game");
                popup_hint.show();
            }

            public void onFocusLost() {
                setLabelColor(Color.gray);
                popup_hint.hide();
            }

            public void onClick() {
                UIManager.hide(UIFactory.main_menu);
            }

            public void onIdle() {
                if (isEnabled() != in_game) {
                    setEnabled(in_game);
                    setActive(in_game);
                }
            }

            public void onEnable() {
                continue_game.setLabelColor(Color.gray);
            }

            public void onDisable() {
                setLabelColor(Color.dark_gray);
            }
        };
        continue_game.setLabel("CONTINUE");
        continue_game.setLabelColor(Color.gray);
        continue_game.setPosition(width / 2 - continue_game.getWidth() / 2, height / 2 - continue_game.getHeight() - 4);

        new_game = new UIButton() {
            public void onFocus() {
                setLabelColor(Color.white);

                popup_hint.setText("Start new game");
                popup_hint.show();
            }

            public void onFocusLost() {
                setLabelColor(Color.gray);
                popup_hint.hide();
            }

            public void onClick() {
                DemoScreen.startGame();
            }
        };
        new_game.setLabel("NEW GAME");
        new_game.setLabelColor(Color.gray);
        new_game.setPosition(width / 2 - new_game.getWidth() / 2, continue_game.getY() + continue_game.getHeight() + 4);

        settings = new UIButton() {
            public void onFocus() {
                setLabelColor(Color.white);

                popup_hint.setText("Configure game parameters");
                popup_hint.show();
            }

            public void onFocusLost() {
                setLabelColor(Color.gray);
                popup_hint.hide();
            }

            public void onClick() {
                UIManager.show(config_msg);
            }
        };
        settings.setLabel("CONFIGURATION");
        settings.setLabelColor(Color.gray);
        settings.setPosition(width / 2 - settings.getWidth() / 2, new_game.getY() + new_game.getHeight() + 4);

        exit = new UIButton() {
            public void onFocus() {
                setLabelColor(Color.white);

                popup_hint.setText("Exit current game");
                popup_hint.show();
            }

            public void onFocusLost() {
                setLabelColor(Color.gray);
                popup_hint.hide();
            }

            public void onClick() {
                UIManager.show(exit_msg);
            }
        };
        exit.setLabel("EXIT");
        exit.setLabelColor(Color.gray);
        exit.setPosition(width / 2 - exit.getWidth() / 2, settings.getY() + settings.getHeight() + 4);

        add(continue_game);
        add(new_game);
        add(settings);
        add(exit);
    }

    public void onKeyPressed() {
        if (EngineRuntime.keyPressed(Controllable.ESC)) {
            if (in_game)
                if (isVisible()) UIManager.hide(this);
                else
                    UIManager.show(exit_msg);
        }
    }

    public void onShow() {
        EngineAPI.setState(Engine.IN_PAUSED);
        UIManager.hide(UIFactory.hud);
    }

    public void onHide() {
        EngineAPI.setState(Engine.IN_GAME);
        UIManager.show(UIFactory.hud);
    }

    private final class ExitMessage extends UIPanel {

        private UIButton yes, no;
        private UIText text;

        public void onCreate() {
            setVisible(false);
            setBackColor(Color.dark_gray);
            setSize(128, 64);

            text = new UIText("Exit game?");
            text.setPosition(width / 2 - text.getWidth() / 2, 4);

            yes = new UIButton() {
                public void onFocus() {
                    setLabelColor(Color.white);
                }

                public void onFocusLost() {
                    setLabelColor(Color.gray);
                }

                public void onClick() {
                    EngineAPI.stopEngine();
                }
            };
            yes.setLabel("Yes");
            yes.setLabelColor(Color.gray);
            yes.setPosition(20, getHeight() - yes.getHeight() - 4);

            no = new UIButton() {
                public void onFocus() {
                    setLabelColor(Color.white);
                }

                public void onFocusLost() {
                    setLabelColor(Color.gray);
                }

                public void onClick() {
                    UIManager.hide(getParent());
                }
            };
            no.setLabel("No");
            no.setLabelColor(Color.gray);
            no.setPosition(getWidth() - no.getWidth() - 20, getHeight() - yes.getHeight() - 4);

            add(text);
            add(yes);
            add(no);
        }

        public void onShow() {
            if (hasParent()) getParent().setActive(false);
        }

        public void onHide() {
            if (hasParent()) getParent().setActive(true);
        }

        public void onDraw() {
            Renderer.drawRect(x, y, width, height, Color.gray);
        }
    }

    private final class ConfigMessage extends UIPanel {

        private UIButton ok;
        private UIText text;

        public void onCreate() {
            setVisible(false);
            setBackColor(Color.dark_gray);
            setSize(128, 64);

            text = new UIText("Not avaliable yet");
            text.setPosition(width / 2 - text.getWidth() / 2, 4);

            ok = new UIButton() {
                public void onFocus() {
                    setLabelColor(Color.white);
                }

                public void onFocusLost() {
                    setLabelColor(Color.gray);
                }

                public void onClick() {
                    UIManager.hide(getParent());
                }
            };
            ok.setLabel("OK");
            ok.setLabelColor(Color.gray);
            ok.setPosition(getWidth() / 2 - ok.getWidth() / 2, getHeight() - ok.getHeight() - 4);

            add(text);
            add(ok);
        }

        public void onShow() {
            if (hasParent()) getParent().setActive(false);
        }

        public void onHide() {
            if (hasParent()) getParent().setActive(true);
        }

        public void onDraw() {
            Renderer.drawRect(x, y, width, height, Color.gray);
        }
    }
}
