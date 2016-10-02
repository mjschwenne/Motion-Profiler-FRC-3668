package motionProfile;

public class ProfileSegment {

	double _initVel;
	double _finalVel;
	double _accel;
	double _currDist = 0;

	public ProfileSegment(double initVel, double finalVel, double accel) {
		_initVel = initVel;
		_finalVel = finalVel;
		_accel = accel * getSegAccellerationSign();
	}

	public double getSegmentDistance() {
		// returns the distance needed to change the velocity
		return ((_finalVel * _finalVel) - (_initVel * _initVel)) / 2 / _accel;
	}

	public double getSegCurrVel(double time) {
		double currVel = _initVel + (_accel * time);
		_currDist = .5 * (_accel) * time * time;
		return currVel;
	}

	public double getDistenaceTravelled(){
		return _currDist;
	}
	
	public double getSegAccellerationSign() {
		// Return the sign of the acceleration for this motion
		double retValue = 0;

		if (_finalVel > _initVel) {
			retValue = 1;
		}

		if (_initVel > _finalVel) {
			retValue = -1;
		}
		
		if (_initVel == _finalVel){
			retValue = 0;
		}

		return retValue;
	}
	public double getSegTime(){
		//System.out.println(Math.abs(_finalVel-_initVel)/_accel);
		return Math.abs((_finalVel - _initVel)/_accel);
	}
}
