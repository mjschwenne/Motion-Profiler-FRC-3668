package motionProfile;

import motionProfile.ProfileSettings.TurnType;

public class CMDturnWithProfile {
	double MAXSPEED = ProfileSettings.MAXSPEED;
	double _distance;
	double _cruiseSpeed;
	boolean _finished = false;
	double _accerlation = ProfileSettings.driveAccelration; //inches/sec/sec
	double _startTime;
	double rightMotorScalar;
	double leftMotorScalar;
	double _degrees;
	MotionProfiler mp;
	Logger log = new Logger(ProfileSettings.motionProfileLogName);
	TurnType turn;
	
	public CMDturnWithProfile(double degrees, double cruiseSpeed, TurnType type) {
		// given distance (inches) and cruise speed (inches per second) turn
		// with motion profiler
		_degrees = degrees;
		_distance = calcTurnDist(type);
		System.out.println("Total distance to travel: " + _distance);
		_cruiseSpeed = Math.min(Math.sqrt(2*_accerlation*_distance), cruiseSpeed);
		mp = new MotionProfiler(_distance, ProfileSettings.initVelocity, _cruiseSpeed, _accerlation);
		_startTime = getTime();
		calcTurnScalar(type);
		System.out.println("Projected Accelration Time:\t" + mp._accelTime + "\tProjected Cruise Time:\t" + mp._cruiseTime
				+ "\tProjected Deccelration Time:\t" + mp._deccelTime + "\tProjected Length of Drive:\t" + mp._stopTime + "\t Matching Turn Type?: "+type.compareTo(ProfileSettings.TurnType.piontR));
	}

	void execute() {
		// execute the drive
		String msg;
		double deltaTime = getTime() - _startTime;
		double profileVelocity = mp.getProfileCurrVelocity(deltaTime);
		msg = "Right-throttle-pos = " + rightMotorScalar*(profileVelocity / MAXSPEED) + " Left-throttle-pos = " + leftMotorScalar*(profileVelocity / MAXSPEED);
		//System.out.println(msg);
		log.makeEntry("Current Velocity: " + profileVelocity + "\t" + msg + "\t deltaTime: " + deltaTime + "\t Total Disantce Travelled: "+mp.getTotalDistanceTraveled());
		if (deltaTime > mp._stopTime) {
			_finished = true;
			end();
		}
	}
	
	public double getTime(){
		return (System.nanoTime() / Math.pow(10,9));
	}
	
	public double calcTurnDist(TurnType type){
		double retVal = 0;
		if(type.compareTo(ProfileSettings.TurnType.piontL) == 0 || type.compareTo(ProfileSettings.TurnType.piontR) == 0){
			retVal = (ProfileSettings.testRobotCirDia/2) * Math.PI * (_degrees / 360);
		}
		if(type.compareTo(ProfileSettings.TurnType.SwingL) == 0 || type.compareTo(ProfileSettings.TurnType.SwingR) == 0){
			retVal = ProfileSettings.testRobotCirDia * Math.PI * (_degrees / 360);
		}
		return retVal;
	}
	
	public void calcTurnScalar(TurnType type){
		if(type.compareTo(ProfileSettings.TurnType.piontR) == 0){
			rightMotorScalar = -1;
			leftMotorScalar = 1;	
		}
		if(type.compareTo(ProfileSettings.TurnType.piontL) == 0){
			rightMotorScalar = 1;
			leftMotorScalar = -1;
		}
		if(type.compareTo(ProfileSettings.TurnType.SwingR) == 0){
			rightMotorScalar = 0;
			leftMotorScalar = 1;
		}
		if(type.compareTo(ProfileSettings.TurnType.SwingL) == 0){
			rightMotorScalar = 1;
			leftMotorScalar = 0;
			
		}
	}
	
	boolean isFinished(){
		//ARE WE DONE?
		return _finished;
	}
	
	void end(){
		//STOP THINGS THAT NEED TO BE STOPPED
		_finished = true;
		log.write();
		log = null;
		System.out.println("Accelration Time:\t" + mp._accelTime + "Cruise Time:\t" + mp._cruiseTime
				+ "Deccelration Time:\t" + mp._deccelTime + "Length of Drive:\t" + mp._stopTime);
	}
}
