package aftergames.demo.game.npc;

import aftergames.engine.EngineRuntime;
import aftergames.engine.ai.Task;
import aftergames.engine.model.Animation;
import aftergames.engine.model.Model;
import aftergames.engine.render.Material;
import aftergames.engine.utils.MathUtils;
import aftergames.engine.utils.ResourceUtils;

/**
 *
 * @author DominaN
 */
public class Hero extends NPC {

    //Animations
    protected Animation anim_walk, anim_stay, anim_shoot_pistol;

    public void init() {
        type = NPC_HERO;
        movetype = MOVETYPE_DYNAMIC;

        name = "Hero";

        solid = true;
        visible = true;
        height = 1;

        life = 1f;
        stamina = 1f;
        speed = 1.5f;
        angle = 0;
        money = 1000;

        anim_walk = new Animation("anim_walk");
        anim_walk.setSequence(new Animation.AnimationSequence(new int[]{3, 7, 2, 6, 1, 5}));
        anim_stay = new Animation("anim_stay");
        anim_stay.setSequence(new Animation.AnimationSequence(new int[]{3, 6}));

        anim_shoot_pistol = new Animation("anim_shoot_pistol");
        anim_shoot_pistol.setSequence(new Animation.AnimationSequence(new int[]{0, 4}));
        anim_shoot_pistol.setFps(2);

        Material mat = new Material();
        mat.diffuse = ResourceUtils.load_image("npc/hero2_1.png");

        model = new Model(mat, 128, 128);
        model.addAnimation(anim_walk);
        model.addAnimation(anim_stay);
        model.addAnimation(anim_shoot_pistol);

        setModel(model);
        setCollisionCircle(0, 0, 40, 20);

        initAI();
    }

    public void initAI() {
        overrideAI(true); //Allow external controls

        //Tasks
        //Idle
        task_idle = new Task() {

            public void init() {
                current_speed.reset();
                model.setAnimation(anim_stay, 2);

                //Stamina recharge
                if (stamina < 1) {
                    stamina += 0.0008f * EngineRuntime.frametime;
                } else {
                    stamina = 1;
                }
            }
        };

        //Walk
        task_walk = new Task() {

            public void init() {
                model.setAnimation(anim_walk);
            }

            public void update() {
                if (stamina >= 0)
                    stamina -= 0.0001f * EngineRuntime.frametime;
                else {
                    stamina = 0;
                    setTask(task_idle);
                    return;
                }

                current_speed.x = speed * stamina * MathUtils.cos(angle);
                current_speed.y = speed * stamina * MathUtils.sin(angle);

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
                if (stamina >= 0)
                    stamina -= 0.0001f * EngineRuntime.frametime;
                else {
                    stamina = 0;
                    setTask(task_idle);
                    return;
                }

                current_speed.x = speed * stamina * MathUtils.cos(angle);
                current_speed.y = speed * stamina * MathUtils.sin(angle);

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
                if (stamina > 0.25)
                    stamina -= 0.0006f * EngineRuntime.frametime;
                if (stamina < 0.25f) {
                    setTask(task_walk);
                    return;
                }

                current_speed.x = speed * stamina * run_coef * MathUtils.cos(angle);
                current_speed.y = speed * stamina * run_coef * MathUtils.sin(angle);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };
    }
}
