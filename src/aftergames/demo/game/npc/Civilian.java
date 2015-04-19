package aftergames.demo.game.npc;

import aftergames.engine.ai.Condition;
import aftergames.engine.ai.Task;
import aftergames.engine.utils.MathUtils;

/**
 *
 * @author KiQDominaN
 */
public class Civilian extends Hero {

    //Example of friendly NPC, following by player
    //(but he's so stupid...))

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
            }

            public void update() {
                rotateObject(MathUtils.angle(target.getWorldY() - getWorldY(), target.getWorldX() - getWorldX()));
            }
        };

        task_idle.setNextUpdate(0.01f);
        task_idle.addCondition(near_hero); //Interrupt "far_hero" adds automaticaly!

        //Walk
        task_walk = new Task() {

            public void init() {
                model.setAnimation(anim_walk);
            }

            public void update() {
                rotateObject(MathUtils.angle(target.getWorldY() - getWorldY(), target.getWorldX() - getWorldX()));

                current_speed.x = speed * MathUtils.cos(angle);
                current_speed.y = speed * MathUtils.sin(angle);

                model.getCurrentAnimation().setFps((float) (current_speed.length() * 5));
                moveObject(current_speed);
            }
        };

        task_walk.setNextUpdate(0.01f);
        task_walk.addCondition(far_hero);
        task_walk.addDoneCondition(near_hero);

        addTask(task_idle);
        addTask(task_walk);
    }
}