package motionProfile;

public class ProfileSettings {
	public static final double testDistance = 100;
	public static final double testCruiseSpeed = 75;
	public static final double testTurnDregees = 90;
	public static final double testTurnCruiseSpeed = 75;
	public static final double testRobotCirDia = 36;
	public static final double driveAccelration = 10;
	public static final double MAXSPEED = 75;
	public static final double initVelocity = 0;
	public static final String motionProfileLogName = "motionProfile";
	public static final String motionProfileTestLogName = "motionProfileTestResults";
	public static final String logLogName = "logTest";
	
	public enum TurnType{
		piontR, piontL, SwingR, SwingL
	}
	
}
