package aftergames.demo.game.npc;

import aftergames.engine.ai.Actor;
import aftergames.engine.ai.Task;

/**
 *
 * @author DominaN
 */
public class NPC extends Actor {

    public static final int NPC_HERO = 0, NPC_MONSTER = 1, NPC_FRIEND = 2, NPC_ENEMY = 3, NPC_INDIFFERENT = 4;
    public int type;

    public float life;
    public float stamina;
    public float run_coef = 2.5f; //Speed multiplier
    public int score = 0;
    public int money = 0;

    public Task task_idle, task_walk, task_walk_back, task_run, task_attack; //Typical tasks
}
