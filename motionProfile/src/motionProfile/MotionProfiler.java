package motionProfile;

import motionProfile.Logger;

public class MotionProfiler {
	double _distance;
	double _initVelocity;
	double _cruiseVelocity;
	double _accelleration;
	double _accelTime;
	double _cruiseDistance;
	double _cruiseTime;
	double _deccelTime;
	double _stopTime;
	double _xa; //distance travelled during the accellration part
	double _xc; //distance travelled during the cruising part
	double _xd; //distance travelled during the deccelleration part
	Logger log = new Logger(ProfileSettings.motionProfileLogName);

	public MotionProfiler(double distance, double initVelocity, double cruiseVeloctiy, double accelleration) {
		_distance = distance;
		_initVelocity = initVelocity;
		_cruiseVelocity = cruiseVeloctiy;
		_accelleration = accelleration;
		_accelTime = getProfileAccellTimes(); 
		_cruiseDistance = _distance - (2 * getProfileDeltaX()); 
		_cruiseTime = _cruiseDistance / _cruiseVelocity; 
		_deccelTime = _accelTime + _cruiseTime;
		_stopTime = _deccelTime + _accelTime;
		_xa = 0;
		_xc = 0;
		_xd = 0;
		//System.out.println("Old Cruise Distance: " + _cruiseDistance + "\t Old Cruise Velocity: " + _cruiseVelocity + "\t Old Accel Time: " + _accelTime+ "\t Old Cruise Time: " + _cruiseTime + "\t Old Deccel Time: "+_deccelTime+ "\t Old Stop Time: " + _stopTime);
		calcProfileShape();
		//System.out.println("New Cruise Distance: " + _cruiseDistance + "\t New Cruise Velocity: " + _cruiseVelocity + "\t New Accel Time: " + _accelTime+  "\t New Cruise Time: " + _cruiseTime + "\t New Deccel Time: "+_deccelTime+ "\t New Stop Time: " + _stopTime);
	}
	
	void end(){
		//log.write();
	}

	public double getProfileAccellTimes() {
		// Given a velocity determine the accel times
		double retvalue = 0.0;
		try {
			retvalue = (_cruiseVelocity - _initVelocity) / _accelleration;
		} catch (ArithmeticException e) {
			// Uncomment to print error message
			// e.printStackTrace();
		}
		return retvalue;
	}

	public double getProfileDeltaX() {
		// returns the distance needed to change the velocity
		//(Vf*Vf0 = (Vi*Vi) + 2ax
		return ((_cruiseVelocity * _cruiseVelocity) - (_initVelocity * _initVelocity)) / 2 / _accelleration;
	}

	public double getProfileCurrVelocity(double time) {
		// at any given, time calculate the current Velocity
		//Vf = Vi + at
		double currVel = 0;
		String msg = "error";
		// we are stopped
		if (time < 0) {
			currVel = 0;
		}
		// we are accellerating
		else if (time < _accelTime) {
			msg = "accelerating";
			//System.out.println(msg);
			currVel = _initVelocity + (_accelleration * time);
			_xa = .5 * (_accelleration) * time * time;
		}
		// we are cruising
		else if ((time > _accelTime) && (time < _deccelTime)) {
			msg = "cruising";
			//System.out.println(msg);
			currVel = _cruiseVelocity;
			_xc = (_cruiseVelocity) * (time - _accelTime);
		}
		// we are slowing down
		else if ((time > _deccelTime) && (time < _stopTime)) {
			msg = "slowing";
			//System.out.println(msg);
			currVel = _cruiseVelocity - (_accelleration * (time - _deccelTime));
			_xd = (_cruiseVelocity * (time - _deccelTime))
					- (.5 * (_accelleration) * (time - _deccelTime) * (time - _deccelTime));
		}
		// we have/need to stop
		else {
			msg = "stopped";
			currVel = 0;
		}
		//log.makeEntry(msg + " Current Velocity: " + currVel + " Distance Travelled: " + getTotalDistanceTraveled());
		return currVel;
	}

	public double getTotalDistanceTraveled() {
		return _xa + _xc + _xd;
	}

	public double getProfileAccellerationSign() {
		// Return the sign of the acceleration for this motion
		double retValue = 0;

		if (_cruiseVelocity > _initVelocity) {
			retValue = 1;
		}

		if (_initVelocity > _cruiseVelocity) {
			retValue = -1;
		}

		return retValue;
	}
	
	public void calcProfileShape(){
		if(_cruiseDistance < 0){
			_cruiseVelocity = (_initVelocity + Math.sqrt(2*_accelleration * (_distance/2))) * 0.9;
			_accelTime = getProfileAccellTimes(); 
			_cruiseDistance = _distance - (2 * getProfileDeltaX()); 
			_cruiseTime = _cruiseDistance / _cruiseVelocity; 
			_deccelTime = _accelTime + _cruiseTime;
			_stopTime = _deccelTime + _accelTime;
		}
	}
}