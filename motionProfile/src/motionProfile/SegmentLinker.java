package motionProfile;

import motionProfile.ProfileSegment;

public class SegmentLinker {

	double _distance;
	double _initVelocity;
	double _cruiseVelocity;
	double _accelleration;
	double _accelTime = 0; //time to hit cruise speed
	double _deccelTime = 0;//time to start slowing
	double _stopTime = 0;  //time to stop
	ProfileSegment testSeg;
	ProfileSegment accelSeg;
	ProfileSegment cruiseSeg;
	ProfileSegment deccelSeg;

	public SegmentLinker(double distance, double cruiseVelocity, double accelleration) {
		_distance = distance;
		_initVelocity = 0;
		_cruiseVelocity = cruiseVelocity;
		_accelleration = accelleration;
		testSeg = new ProfileSegment(_initVelocity, _cruiseVelocity, _accelleration);
		if(getProfileShape() == 3){
			accelSeg = new ProfileSegment(_initVelocity, _cruiseVelocity, _accelleration);
			cruiseSeg = new ProfileSegment(_cruiseVelocity, _cruiseVelocity, _accelleration);
			deccelSeg = new ProfileSegment(_cruiseVelocity, 0, _accelleration);
		}
		if(getProfileShape() == 2){
			_cruiseVelocity = Math.sqrt(2 * _accelleration * (_distance / 2));
//			_cruiseVelocity =  _cruiseVelocity * 100;
//			_cruiseVelocity = (int)_cruiseVelocity;
//			_cruiseVelocity = _cruiseVelocity / 100;
			
			accelSeg = new ProfileSegment(_initVelocity, _cruiseVelocity, _accelleration);
			cruiseSeg = null;
			deccelSeg = new ProfileSegment(_cruiseVelocity, _initVelocity, _accelleration);
		}
		getProfileTimes();
	}
	
	public double getProfileDist(){
		if(cruiseSeg != null){
		return accelSeg.getSegmentDistance() + cruiseSeg.getSegmentDistance() + deccelSeg.getSegmentDistance();
		} else {
			return accelSeg.getSegmentDistance() + deccelSeg.getSegmentDistance();
		}
	}
	
	public double getTotalDistTravelled(){
		if(cruiseSeg != null){
			return accelSeg.getDistenaceTravelled() + cruiseSeg.getDistenaceTravelled() + deccelSeg.getDistenaceTravelled();
		} else {
			return accelSeg.getDistenaceTravelled() + deccelSeg.getDistenaceTravelled();
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
	
	public void getProfileTimes(){
		_accelTime = accelSeg.getSegTime();
		if(cruiseSeg != null){
			_deccelTime = cruiseSeg.getSegTime() + _accelTime;
		}
		if(_deccelTime != 0){
			_stopTime = deccelSeg.getSegTime() + _deccelTime;
		} else {
			_stopTime = deccelSeg.getSegTime() + _accelTime;
		}
	}
	
	public double readAccelTime(){
		return _accelTime;
	}
	
	public double readDeccelTime(){
		return _deccelTime;
	}
	
	public double readStopTime(){
		return _stopTime;
	}
	
	public double getCurrProfileVelocity(double time){
		double currVel = 0;
		if(time < _accelTime){
			currVel = accelSeg.getSegCurrVel(time);
		} else if((time > _accelTime)&&(cruiseSeg == null)){
			currVel = deccelSeg.getSegCurrVel(time - _accelTime);
		} else if ((_accelTime<time) && (time < _deccelTime) && (cruiseSeg != null)){
			currVel = cruiseSeg.getSegCurrVel(time - _accelTime);
		} else if ((_deccelTime < time) && (time > _stopTime)){
			currVel = deccelSeg.getSegCurrVel(time - _deccelTime);
		} else {
			currVel = 0; //we have stopped
		}
		return currVel;
	}
}