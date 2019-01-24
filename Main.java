public class Main {
	public static void main(String[] args) {
		int minStepNum = Integer.MAX_VALUE; // 最小ステップ数の記録
		
		QLearning learn = new QLearning();
		
		learn.initQ();               // すべてのQ値の初期化
		for (int i = 0; i < QLearning.EPISODE; i++) {
			Agent agent = new Agent(); // エージェントの初期化
			while (!agent.isGoal(agent.getPositionX(), agent.getPositionY())) {
				agent.incrementStepNum();

				int selectAction = agent.eGreedy(learn.q);   // ε-greedy法で行動を選択
				int reward = agent.Action(selectAction);   // 行動を行い次の状態を観測し報酬を受け取る
				reward -= QLearning.ONE_STEP_PENALTY;     // ステップ経過のペナルティ
				learn.updateQ(reward, selectAction, agent);       // Q値の更新
				agent.updateS();           // 状態の更新 s←s'
			}
			if (agent.getStepNum() < minStepNum) {
				minStepNum = agent.getStepNum();
			}
			if (agent.getStepNum() == Problem.BEST_SCORE) {
				minStepNum = agent.getStepNum();
				
				// すべてのQ値を出力
				learn.printQ();
				for (int j = 0; j < agent.getRoute().size(); j++ ) {
					System.out.println(agent.getRoute().get(j));
				}
				System.out.println("学習回数：" + i + "　ゴールまでのステップ数:" + agent.getStepNum() + "　これまでの最小ステップ数:" + minStepNum);
				break;
			}
			System.out.println("学習回数：" + i + "　ゴールまでのステップ数:" + agent.getStepNum() + "　これまでの最小ステップ数:" + minStepNum);
		}
	}
}