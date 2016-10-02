package motionProfile;

import java.util.Scanner;

import motionProfile.ProfileSettings.*;

public class CMDturnWithProfileTester {
	private int turn;
	private double degrees;
	private Scanner input = new Scanner(System.in);
	static CMDturnWithProfileTester driveTester = new CMDturnWithProfileTester();
	
	public CMDturnWithProfileTester(){
		//cmdDrive = new CMDdriveWithProfile(ProfileSettings.testDistance,ProfileSettings.testCruiseSpeed);
		test();
	}
	
	void test (){
		getTurnStats();
		CMDturnWithProfile cmdTurn = new CMDturnWithProfile(degrees, ProfileSettings.testTurnCruiseSpeed, turn);
		while(cmdTurn.isFinished() == false){
			cmdTurn.execute();
			try {
				Thread.sleep((long)100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		cmdTurn = null;
	}
	
	public void getTurnStats(){
		System.out.println("How many degrees?");
		degrees = input.nextDouble();
		input.nextLine();
		System.out.println("What type of turn?");
		String TT = input.nextLine();
		if(TT.equalsIgnoreCase("point, right")){
			turn = 1;
		}
		else if(TT.equalsIgnoreCase("point, left")){
			turn = 2;
		}
		else if(TT.equalsIgnoreCase("swing, right")){
			turn = 3;
		}
		else if(TT.equalsIgnoreCase("swing, left")){
			turn = 4;
		}
		else {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		driveTester.test();
	}
}
