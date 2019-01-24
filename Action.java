import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Action{
		UP(0), DOWN(1), LEFT(2), RIGHT(3);
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
