package motionProfile;

public class ProfileSettings {
	public static final double testDistance = -5;
	public static final double testCruiseSpeed = 75;
	public static final double testTurnDregees = 90;
	public static final double testTurnCruiseSpeed = 75;
	public static final double testRobotCirDia = 36;
	public static final double driveAccelration = 10;
	public static final double MAXSPEED = 75;
	public static final double initVelocity = 0;
	public static final String motionProfileLogName = "logs\\motionProfile";
	public static final String motionProfileTestLogName = "logs\\motionProfileTestResults";
	public static final String logLogName = "logTest";
	public static final String logFileExtension = ".txt";
	
	public enum TurnType{
		piontR, piontL, SwingR, SwingL
	}
	
}
