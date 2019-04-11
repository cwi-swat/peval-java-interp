import java.util.List;

public class RunStm {
	

	static class Machine {
		private final String name;
		private final List<State> states;
		
		public Machine(String name, List<State> states) {
			this.name = name;
			this.states = states;
		}
		
		public void eval(List<String> events, String[] rt) {
			System.out.println("Running " + this.name);
			for (String event: events) {
				for (State s: this.states) {
					s.eval(event, rt);
				}
				System.out.println(rt[0]);
			}
		}
		
	}
	
	static class State {
		private final String name;
		private final List<Trans> trans;
		
		public State(String name, List<Trans> trans) {
			this.name = name;
			this.trans = trans;
		}
		
		public void eval(String event, String[] rt) {
			if (rt[0].equals(this.name)) {
				for (Trans t: this.trans) {
					t.eval(event, rt);
				}
			}
		}
	}
	
	static class Trans {
		private final String event, target;
		
		public Trans(String event, String target) {
			this.event = event;
			this.target = target;
		}
		
		public void eval(String event, String[] rt) {
			if (event.equals(this.event)) {
				System.out.println("Transitioning to " + this.target);
				rt[0] = this.target;
			}
		}
	}
	
	
}