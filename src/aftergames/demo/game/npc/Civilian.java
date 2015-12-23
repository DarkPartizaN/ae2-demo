//
//Example of friendly (but sooo stupid) NPC, following by player
//

package aftergames.demo.game.npc;

import aftergames.engine.EngineRuntime;
import aftergames.engine.ai.Condition;
import aftergames.engine.ai.Task;
import aftergames.engine.utils.MathUtils;

/**
 *
 * @author KiQDominaN
 */
public class Civilian extends Hero {

    public void init() {
        super.init();

        type = NPC_FRIEND;
        speed = 1.2f;
        setCollisionCircle(0, 0, 20, 20);
    }

    public void initAI() {
        //Conditions
        Condition near_hero = new Condition() {

            public boolean satisfied() {
                return (Math.abs(target.getWorldX() - getWorldX()) < 200) && (Math.abs(target.getWorldY() - getWorldY()) < 200);
            }
        };

        Condition far_hero = near_hero.invert();

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

            public void update() {
                rotateObject(MathUtils.angle(target.getWorldY() - getWorldY(), target.getWorldX() - getWorldX()));
            }
        };
        task_idle.addCondition(near_hero);

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
                
                rotateObject(MathUtils.angle(target.getWorldY() - getWorldY(), target.getWorldX() - getWorldX()));

                current_speed.x = speed * stamina * MathUtils.cos(angle);
                current_speed.y = speed * stamina * MathUtils.sin(angle);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };
        task_walk.addCondition(far_hero);

        addTask(task_idle);
        addTask(task_walk);
    }
}
