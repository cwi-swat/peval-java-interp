import java.util.List;

public class Doors {
	public static void doors(List<String> events, String[] rt) {
		System.out.println("Running " + "Doors");
		for (String event : events) {
			{
				{
					state$2(event, rt);
				}
				{
					state$4(event, rt);
				}
				{
					state$6(event, rt);
				}
			}
			System.out.println(rt[0]);
		}
	}

	private static void state$2(String event, String[] rt) {
		if (rt[0].equals("closed")) {
			{
				{
					trans$0(event, rt);
				}
				{
					trans$1(event, rt);
				}
			}
		}
	}

	private static void trans$0(String event, String[] rt) {
		if (event.equals("opened")) {
			System.out.println("Transitioning to " + "opened");
			rt[0] = "opened";
		}
	}

	private static void trans$1(String event, String[] rt) {
		if (event.equals("locked")) {
			System.out.println("Transitioning to " + "locked");
			rt[0] = "locked";
		}
	}

	private static void state$4(String event, String[] rt) {
		if (rt[0].equals("opened")) {
			{
				{
					trans$3(event, rt);
				}
			}
		}
	}

	private static void trans$3(String event, String[] rt) {
		if (event.equals("closed")) {
			System.out.println("Transitioning to " + "closed");
			rt[0] = "closed";
		}
	}

	private static void state$6(String event, String[] rt) {
		if (rt[0].equals("locked")) {
			{
				{
					trans$5(event, rt);
				}
			}
		}
	}

	private static void trans$5(String event, String[] rt) {
		if (event.equals("closed")) {
			System.out.println("Transitioning to " + "closed");
			rt[0] = "closed";
		}
	}
}