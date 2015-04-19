package aftergames.demo.game.npc;

import aftergames.demo.game.inventory.Inventory;
import aftergames.engine.ai.Task;
import aftergames.engine.render.Color;
import aftergames.engine.utils.MathUtils;
import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.world.Animation;
import aftergames.engine.world.Model;
import aftergames.engine.world.Light;

/**
 *
 * @author DominaN
 */
public class Hero extends NPC {

    //Animations
    protected Animation anim_walk, anim_stay, anim_shoot_pistol;
    //Flashlight
    private Light flashlight;

    public void init() {
        type = NPC_HERO;
        movetype = MOVETYPE_DYNAMIC;
        phystype = PHYS_PRECISE;

        name = "Hero";

        solid = true;
        visible = true;
        layer = 1;

        life = 1f;
        speed = 1.5f;
        angle = 0;

        anim_walk = new Animation("anim_walk");
        anim_walk.setSequence(new Animation.AFTAnimationSequence(new int[]{3, 7, 2, 6, 1, 5}));
        anim_stay = new Animation("anim_stay");
        anim_stay.setSequence(new Animation.AFTAnimationSequence(new int[]{3, 6}));

        anim_shoot_pistol = new Animation("anim_shoot_pistol");
        anim_shoot_pistol.setSequence(new Animation.AFTAnimationSequence(new int[]{0, 4}));
        anim_shoot_pistol.setFps(2);

        model = new Model(ResourceUtils.load_image("npc/hero2_1.png"), 128, 128);
        model.addAnimation(anim_walk);
        model.addAnimation(anim_stay);
        model.addAnimation(anim_shoot_pistol);

        setModel(model);
        setCollisionCircle(0, 0, 40, 20);

        initAI();

        inventory = new Inventory();
        inventory.setMaxWeight(50);

        //Flashlight
        flashlight = new Light(Light.CONE);
        flashlight.init();
        flashlight.setColor(Color.white);
        flashlight.setDistance(400);
        flashlight.setRadius(48);
        flashlight.setVisible(false); //Initially off
        //Attach light to player
        flashlight.setTarget(this);
        addChild(flashlight);
    }

    public void initAI() {
        overrideAI(true); //Allow external controls

        //Tasks
        //Idle
        task_idle = new Task() {

            public void init() {
                current_speed.reset();
                model.setAnimation(anim_stay, 2);
            }
        };

        //Walk
        task_walk = new Task() {

            public void init() {
                model.setAnimation(anim_walk);
            }

            public void update() {
                current_speed.x = speed * MathUtils.cos(angle);
                current_speed.y = speed * MathUtils.sin(angle);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };

        //Walk back
        task_walk_back = new Task() {

            public void init() {
                model.setAnimation(anim_walk);
            }

            public void update() {
                current_speed.x = speed * MathUtils.cos(angle);
                current_speed.y = speed * MathUtils.sin(angle);

                current_speed = current_speed.invert().mul(0.5f);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };

        //Run
        task_run = new Task() {

            public void init() {
                model.setAnimation(anim_walk);
            }

            public void update() {
                current_speed.x = speed * run_coef * MathUtils.cos(angle);
                current_speed.y = speed * run_coef * MathUtils.sin(angle);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };
    }

    public void toggleFlashlight() {
        flashlight.setVisible(!flashlight.isVisible());
    }

    public void attack() {
        model.setAnimation(anim_shoot_pistol, 2); //Currently don't work
    }

}