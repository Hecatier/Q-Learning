import java.util.ArrayList;
import java.util.Random;

public class Agent{
	// 方向 エージェントの行動
	public static final int LEFT  = 0;
	public static final int UP    = 1;
	public static final int RIGHT = 2;
	public static final int DOWN  = 3;
	public static final int ACTION_SIZE = 4;

	// エージェントの状態
	private int sPosX;     // x座標
	private int sPosY;     // y座標
	private int sdPosX;    // 観測したx座標
	private int sdPosY;    // 観測したy座標
	private int stepNum; // 経過ステップ数

	private ArrayList<String> route = new ArrayList<>(); // 経路保存用配列

	Agent(){
		// エージェントをスタートにセット
		sPosX = 1;
		sPosY = 1;
		sdPosX = 1;
		sdPosY = 1;
		stepNum = 0;
	}
	
	public int getPositionX(){
		return sPosX;
	}
	
	public int getPositionY(){
		return sPosY;
	}
	
	public int getSDPositionX(){
		return sdPosX;
	}
	
	public int getSDPositionY(){
		return sdPosY;
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
	public int Action(int direction) {
		int reward = 0;
		
		// 観測後の状態に現在の状態を設定
		// この関数内で観測後のものに書き換える
		sdPosX = sPosX;
		sdPosY = sPosY;
				
		switch (direction) {
			case LEFT:
				if (Problem.MAZE[sPosY][sPosX-1] == 1) { // 移動可能か
					sdPosX--;
				} else {					
					reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
				}
				route.add("←" + "[" + String.valueOf(sdPosX) + "][" + String.valueOf(sdPosY) + "] ");
				break;
			case UP:
				if (Problem.MAZE[sPosY-1][sPosX] == 1) { // 移動可能か
					sdPosY--;
				} else {
					reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
				}
				route.add("↑" + "[" + String.valueOf(sdPosX) + "][" + String.valueOf(sdPosY) + "] ");
				break;
			case RIGHT:				
				if (Problem.MAZE[sPosY][sPosX+1] == 1) { // 移動可能か
					sdPosX++;
				} else {					
					reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
				}
				route.add("→" + "[" + String.valueOf(sdPosX) + "][" + String.valueOf(sdPosY) + "] ");
				break;
			case DOWN:
				if (Problem.MAZE[sPosY+1][sPosX] == 1) { // 移動可能か
					sdPosY++;
				} else {
					reward -= QLearning.HIT_WALL_PENALTY; // 壁にぶつかった時
				}
				route.add("↓" + "[" + String.valueOf(sdPosX) + "][" + String.valueOf(sdPosY) + "] ");
				break;
		}
		
		if(isGoal(sdPosX, sdPosY)){ // ゴール報酬の設定
			reward = QLearning.GOAL_REWARD;
		}
		
		return reward;
	}
	
	// 行動の選択 ε-greedy法
	public int eGreedy(double[][][] qTable) {
		int selectedAction = 0;
		
        Random rand = new Random();
        
        if (rand.nextDouble() < QLearning.EPSILON) {
        	selectedAction = rand.nextInt(ACTION_SIZE);
        } else {
			for (int a = 0; a < ACTION_SIZE; a++) {
				if (qTable[sPosX][sPosY][selectedAction] < qTable[sPosX][sPosY][a]) {
					selectedAction = a;	
				}
			}
        }
        
        return selectedAction;
	}

	
	// 状態の更新
	public void updateS() {
		sPosX = sdPosX;
		sPosY = sdPosY;
	}

	public boolean isGoal(int posX, int posY){
		if(posX == Problem.GOAL_X && posY == Problem.GOAL_Y){
			return true;
		}else{
			return false;
		}
	}
}