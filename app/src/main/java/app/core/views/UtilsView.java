package app.core.views;

public class UtilsView {

	public static void printSeparator(boolean large, int len) {
		char sep = large ? '=' : '-';
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; i++) {
			sb.append(sep);
		}
		System.out.println(sb.toString());
	}
}
