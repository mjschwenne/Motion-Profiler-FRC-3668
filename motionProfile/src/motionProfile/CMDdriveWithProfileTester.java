package motionProfile;

import motionProfile.CMDdriveWithProfile;
import motionProfile.ProfileSettings;

public class CMDdriveWithProfileTester {
	static CMDdriveWithProfile cmdDrive = new CMDdriveWithProfile(ProfileSettings.testDistance,ProfileSettings.testCruiseSpeed);
	static CMDdriveWithProfileTester driveTester = new CMDdriveWithProfileTester();
	
	public CMDdriveWithProfileTester(){
		//cmdDrive = new CMDdriveWithProfile(ProfileSettings.testDistance,ProfileSettings.testCruiseSpeed);
		test();
	}
	
	void test (){
		while(cmdDrive.isFinished() == false){
			cmdDrive.execute();
			try {
				Thread.sleep((long)10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
     		}
			
	}
		cmdDrive = null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		driveTester.test();
	}
}
