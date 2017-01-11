package motionProfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {
	// Logger to write data to the hard drive
	static ArrayList<String> entries = new ArrayList<String>();
	StringBuilder sb = new StringBuilder();
	String name;
	BufferedWriter writer = null;
	static String timeLog = new SimpleDateFormat("_MM,dd,yyyy_HH,mm,ss").format(Calendar.getInstance().getTime());
	static Logger log = new Logger(ProfileSettings.logLogName);
	static int number = 1;

	public Logger(String Name){
		name = Name;
	}
	
//	public static void main(String[] args) {
//		log.makeEntry("test");
//		try {
//			log.write();
//		} finally {
//			System.exit(0);
//		}
//	}

	void makeEntry(String line) {
		String time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		entries.add(time+ "\t" +  line);
		number = number + 1;
	}

	void write() {
		for (String s : entries) {
			sb.append(s);
			sb.append("\n");
		}
		try {
			File logFile = new File(name + timeLog);
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(sb.toString());
			System.out.println(logFile.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}finally{
				System.exit(0);
			}
		}
	}
}