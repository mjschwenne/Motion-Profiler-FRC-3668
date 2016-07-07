package motionProfile;

import motionProfile.ProfileSegment;

public class SegmentLinker {

	double _distance;
	double _initVelocity;
	double _cruiseVelocity;
	double _accelleration;
	double _accelTime;
	double _cruiseTime;
	double _deccelTime;
	ProfileSegment testSeg;
	ProfileSegment accelSeg;
	ProfileSegment curiseSeg;
	ProfileSegment deccelSeg;

	public SegmentLinker(double distance, double initVelocity, double cruiseVelocity,double finalVelocity, double accelleration) {
		_distance = distance;
		_initVelocity = initVelocity;
		_cruiseVelocity = cruiseVelocity;
		_accelleration = accelleration;
		testSeg = new ProfileSegment(initVelocity, cruiseVelocity, accelleration);
		if(getProfileShape() == 3){
			accelSeg = new ProfileSegment(initVelocity, cruiseVelocity, accelleration);
			curiseSeg = new ProfileSegment(cruiseVelocity, cruiseVelocity, accelleration);
			deccelSeg = new ProfileSegment(cruiseVelocity, finalVelocity, accelleration);
		}
	}

	public double getProfileShape(){
		double SegNum = 0;
		if((testSeg.getSegmentDistance() * 2) >= _distance){
			SegNum = 2;
		} if ((testSeg.getSegmentDistance() * 2) < _distance){
			SegNum = 3;
		}
		return SegNum;
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
}
