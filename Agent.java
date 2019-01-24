import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Agent{
	public enum Action{
		LEFT(0), RIGHT(1), UP(2), DOWN(3);
		private final int id;
		
		private Action(final int id){
			this.id = id;
		}
		public static final List<Action> ActionList = Collections.unmodifiableList(Arrays.asList(values()));
		public static Action getAction(int n){
			return ActionList.get(n);
		}
		public int getId(){
			return id;
		}
	}
	
	// エージェントの状態
	private State state;
	private State stateDash;
	private int stepNum;

	private ArrayList<String> route = new ArrayList<>(); // 経路保存用配列

	Agent(){
		// エージェントをスタートにセット
		state = new State(1, 1);
		stateDash = new State(1, 1);
		stepNum = 0;
	}

	public State getState(){
		return state;
	}
	
	public State getStateDash(){
		return stateDash;
	}
	
	public int getStepNum(){
		return stepNum;
	}

	public ArrayList<String> getRoute(){
		return route;
	}

	public void incrementStepNum(){
		stepNum++;
	}

	// directionの向きに移動可能であれば移動
	public int Action(Action act) {
		int reward = 0;

		// 観測後の状態に現在の状態を設定
		// この関数内で観測後のものに書き換える
		stateDash.setX(state.getX());
		stateDash.setY(state.getY());

		switch (act) {
		case LEFT:
			if (Problem.MAZE[state.getY()][state.getX()-1] == 1) { // 移動可能か
				stateDash.setX(stateDash.getX()-1);
			} else {					
				reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
			}
			route.add("←" + "[" + String.valueOf(stateDash.getX()) + "][" + String.valueOf(stateDash.getY()) + "] ");
			break;
		case UP:
			if (Problem.MAZE[state.getY()-1][state.getX()] == 1) { // 移動可能か
				stateDash.setY(stateDash.getY()-1);
			} else {
				reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
			}
			route.add("↑" + "[" + String.valueOf(stateDash.getX()) + "][" + String.valueOf(stateDash.getY()) + "] ");
			break;
		case RIGHT:				
			if (Problem.MAZE[state.getY()][state.getX()+1] == 1) { // 移動可能か
				stateDash.setX(stateDash.getX()+1);
			} else {					
				reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
			}
			route.add("→" + "[" + String.valueOf(stateDash.getX()) + "][" + String.valueOf(stateDash.getY()) + "] ");
			break;
		case DOWN:
			if (Problem.MAZE[state.getY()+1][state.getX()] == 1) { // 移動可能か
				stateDash.setY(stateDash.getY()+1);
			} else {
				reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
			}
			route.add("↓" + "[" + String.valueOf(stateDash.getX()) + "][" + String.valueOf(stateDash.getY()) + "] ");
			break;
		}

		if(isGoal(stateDash.getX(), stateDash.getY())){ // ゴール報酬の設定
			reward = QLearning.GOAL_REWARD;
		}

		return reward;
	}

	// 行動の選択 ε-greedy法
	public Action eGreedy(double[][][] qTable) {
		Action selectedAction = Action.getAction(0);
		Random rand = new Random();

		if (rand.nextDouble() < QLearning.EPSILON) {
			selectedAction = Action.getAction(rand.nextInt(Action.ActionList.size()));
		} else {
			for (int i = 1; i < Action.ActionList.size(); i++) {
				if (qTable[state.getX()][state.getY()][selectedAction.getId()] < qTable[state.getX()][state.getY()][i]) {
					selectedAction = Action.getAction(i);
				}
			}
		}

		return selectedAction;
	}


	// 状態の更新
	public void updateS() {
		state.setX(stateDash.getX());
		state.setY(stateDash.getY());
	}

	public boolean isGoal(int posX, int posY){
		if(posX == Problem.GOAL_X && posY == Problem.GOAL_Y){
			return true;
		}else{
			return false;
		}
	}
}