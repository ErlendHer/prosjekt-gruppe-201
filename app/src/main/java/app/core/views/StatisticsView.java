package app.core.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsView {

	public static void printStatistics(ResultSet rs) throws SQLException {
		UtilsView.printSeparator(true, 100);
		String format = "%-30s| %-30s| %-30s";
		System.out.println(String.format(format, "Username", "Number of posts read", "Number of posts created"));
		UtilsView.printSeparator(false, 100);
		while (rs.next()) {
			System.out.println(String.format(format, rs.getString(1), rs.getString(2), rs.getString(3)));
		}
		UtilsView.printSeparator(true, 100);
		System.out.println();
	}
}
