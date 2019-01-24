public class Problem {
	// 迷路
	// 0 通れない 壁
	// 1 通れる 道
	// スタートは左上 maze[1][1]
	// ゴールは右下  maze[8][8]
	// 配列は[y座標][x座標]なことに注意
	public static final int MAZE[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 1, 1, 0, 1, 1, 0, 1, 0},
			{0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 1, 0, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	};

	public static final int MAZE_HEIGHT = 10;
	public static final int MAZE_WIDTH = 10;
	public static final int GOAL_X = 8;	//ゴールのX座標
	public static final int GOAL_Y = 8;	//ゴールのY座標
	public static final int BEST_SCORE = 22; //最短ルート
}
