import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatFinder {

	private static final String[] formats = { "yyyy-MM-dd'T'HH:mm:ss'Z'",
			"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss",
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss",
			"MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ",
			"MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ",
			"MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss", "yyyyMMdd", };

	/*
	 * @param args
	 */
	public static void main(String[] args) {
		String yyyyMMdd = "2015-05-27T08:50:00.000";
		parse(yyyyMMdd);

		String s = changeDateFormat(yyyyMMdd, "yyyy-MM-dd'T'HH:mm:ss",
				"yyyy-MM-dd'T'HH:mm");
		System.out.println("rk format=" + s);

		System.out.println(trim("Raja Firoz Khan", 8));
	}

	static String trim(String s, int maxCharLimit) {
		if (isEmpty(s) || maxCharLimit < 0) {
			return s;
		}

		s = s.substring(0, Math.min(s.length(), maxCharLimit));
		return s;
	}

	static void parse(String d) {
		if (d != null) {
			for (String parse : formats) {
				SimpleDateFormat sdf = new SimpleDateFormat(parse);
				try {
					sdf.parse(d);
					System.out.println("Printing the value of " + parse);
				} catch (ParseException e) {

				}
			}
		}
	}

	static String changeDateFormat(String dateString, String oldFormat,
			String newFormat) {
		if (isEmpty(dateString, oldFormat, newFormat)) {
			return "";
		}

		String retValue = "";

		try {
			SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
			Date parsedDate = oldSdf.parse(dateString);
			String newDateStr = getFormatedDate(parsedDate, newFormat);

			if (!isEmpty(newDateStr)) {
				retValue = newDateStr;
			}
		} catch (ParseException e) {
			// e.printStackTrace();
		}

		return retValue;
	}

	static boolean isEmpty(String... stringArray) {
		for (String s : stringArray) {
			if (isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	static boolean isEmpty(String s) {
		return (s == null) || s.trim().equals("");
	}

	static String getFormatedDate(Date date, String format) {
		String result = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}
		return result;
	}
}
