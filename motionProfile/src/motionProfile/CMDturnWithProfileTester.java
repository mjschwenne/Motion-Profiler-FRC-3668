package motionProfile;

import motionProfile.ProfileSettings.*;

public class CMDturnWithProfileTester {
	static CMDturnWithProfileTester driveTester = new CMDturnWithProfileTester();
	
	public CMDturnWithProfileTester(){
		//cmdDrive = new CMDdriveWithProfile(ProfileSettings.testDistance,ProfileSettings.testCruiseSpeed);
		test();
	}
	
	void test (){
		CMDturnWithProfile cmdTurn = new CMDturnWithProfile(ProfileSettings.testTurnDregees, ProfileSettings.testTurnCruiseSpeed, ProfileSettings.TurnType.SwingR);
		while(cmdTurn.isFinished() == false){
			cmdTurn.execute();
			try {
				Thread.sleep((long)10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		cmdTurn = null;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		driveTester.test();
	}
}
