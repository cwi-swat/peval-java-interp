import java.util.List;

public class Doors {
	public static void doors(List<String> events, String[] rt) {
		System.out.println("Running " + "Doors");
		for (String event : events) {
			{
				{
					state_1(event, rt);
				}
				{
					state_3(event, rt);
				}
			}
			System.out.println(rt[0]);
		}
	}

	private static void state_1(String event, String[] rt) {
		if (rt[0].equals("closed")) {
			{
				{
					trans_0(event, rt);
				}
			}
		}
	}

	private static void trans_0(String event, String[] rt) {
		if (event.equals("opened")) {
			System.out.println("Transitioning to " + "opened");
			rt[0] = "opened";
		}
	}

	private static void state_3(String event, String[] rt) {
		if (rt[0].equals("opened")) {
			{
				{
					trans_2(event, rt);
				}
			}
		}
	}

	private static void trans_2(String event, String[] rt) {
		if (event.equals("closed")) {
			System.out.println("Transitioning to " + "closed");
			rt[0] = "closed";
		}
	}
}