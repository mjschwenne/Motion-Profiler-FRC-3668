package motionProfile;

import motionProfile.MotionProfiler;
import motionProfile.ProfileSettings;
import motionProfile.ProfileSettings.TurnType;
import motionProfile.Logger;

public class CMDdriveWithProfile {
	// DRIVE THE ROBOT WITH MOTION PROFILER
	double MAXSPEED = ProfileSettings.MAXSPEED;
	double _distance;
	double _cruiseSpeed;
	boolean _finished = false;
	double _accerlation = ProfileSettings.driveAccelration; //inches/sec/sec
	double _startTime;
	MotionProfiler mp;
	Logger log = new Logger(ProfileSettings.motionProfileLogName);
	
	
	public CMDdriveWithProfile(double distance, double cruiseSpeed) {
		// given distance (inches) and cruise speed (inches per second) drive
		// with nice profile!
		_distance = distance;
		_cruiseSpeed = cruiseSpeed;
		mp = new MotionProfiler(_distance, ProfileSettings.initVelocity, _cruiseSpeed, _accerlation);
		_startTime = getTime();
		System.out.println("Projected Accelration Time:\t" + mp._accelTime + "\tProjected Cruise Time:\t" + mp._cruiseTime
				+ "\tProjected Deccelration Time:\t" + mp._deccelTime + "\tProjected Length of Drive:\t" + mp._stopTime);
	}

	void execute() {
		// execute the drive
		String msg;
		double deltaTime = getTime() - _startTime;
		double profileVelocity = mp.getProfileCurrVelocity(deltaTime);
		// are are in the accel time segment of the motion
		msg = "throttle-pos = " + (profileVelocity / MAXSPEED);
		log.makeEntry("Current Velocity: " + profileVelocity + "\t" + msg + "\t deltaTime: " + deltaTime + "\t Total Disantce Travelled: "+mp.getTotalDistanceTraveled());
		if (deltaTime > mp._stopTime) {
			_finished = true;
			end();
		}
	}
	
	public double getTime(){
		return (System.nanoTime() / Math.pow(10,9));
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
