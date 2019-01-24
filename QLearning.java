import java.util.Random;

public class QLearning {	
	// パラメータ
	public static final double EPSILON = 0.30; // ε-greedy法のε
	public static final double ALPHA = 0.10;   // 学習率α
	public static final double GAMMA = 0.90;   // 割引率γ
	public static final int EPISODE = 10000;    // 学習回数
	public static final int INIT_Q_MAX = 30;   // Qの初期値の最大値(乱数の最大値)

	public static final int GOAL_REWARD = 100;    // ゴール時の報酬
	public static final int HIT_WALL_PENALTY = 5; // 壁にぶつかった時の報酬のペナルティ
	public static final int ONE_STEP_PENALTY = 1; // 1ステップ経過のペナルティ

	public double q[][][];

	QLearning(){
		// Q値の初期化
		q = new double[Problem.MAZE_HEIGHT][Problem.MAZE_WIDTH][Agent.Action.ActionList.size()];
	}

	public void initQ() {
		// 0~INIT_Q_MAXの乱数で初期化
		Random rand = new Random();
		for (int x = 0; x < Problem.MAZE_HEIGHT; x++) {
			for (int y = 0; y < Problem.MAZE_WIDTH; y++) {
				for (int a = 0; a < Agent.Action.ActionList.size(); a++) {
					int randNum = rand.nextInt(INIT_Q_MAX+1);
					q[x][y][a] = randNum;
				}
			}
		}
	}

	public void updateQ(int reward, Agent.Action action, Agent agent) {
		// 状態s'で行った時にQ値が最大となるような行動
		int maxA = 0;
		for (int i = 0; i < Agent.Action.ActionList.size(); i++) {
			if (q[agent.getSDPositionX()][agent.getSDPositionY()][maxA] < q[agent.getSDPositionX()][agent.getSDPositionY()][i]) {
				maxA = i;	
			}
		}

		// Q値の更新
		q[agent.getPositionX()][agent.getPositionY()][action.getId()] 
				= (1.0 - ALPHA) * q[agent.getPositionX()][agent.getPositionY()][action.getId()] 
						+ ALPHA * (reward + GAMMA * q[agent.getSDPositionX()][agent.getSDPositionY()][maxA]);		
	}

	public void printQ() {
		for (int x = 0; x < Problem.MAZE_HEIGHT; x++) {
			for (int y = 0; y < Problem.MAZE_WIDTH; y++) {
				for (int a = 0; a < Agent.Action.ActionList.size(); a++) {
					System.out.println("x:" + x + " y:" + y + " a:" + a + " Q:" + q[x][y][a]);
				}
			}
		}
	}

}