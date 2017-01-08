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
	double _xa;
	double _xc;
	double _xd;
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
	}
	
	void end(){
		log.write();
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
		// returns the deltaX to change the velocity
		return ((_cruiseVelocity * _cruiseVelocity) - (_initVelocity * _initVelocity)) / 2 / _accelleration;
	}

	public double getProfileCurrVelocity(double time) {
		// given a time calculate the current Velocity
		double currVel = 0;
		String msg = "";
		// we have not started moving yet...
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
		// we are cruising at speed
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
		// we are past when we should stop
		else {
			msg = "stopped";
			currVel = 0;
		}
		log.makeEntry(msg + " Current Velocity: " + currVel + " Distance Travelled: " + getTotalDistanceTraveled());
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
}