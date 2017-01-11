package org.jointheleague.ecolban.cleverrobot;

import java.io.IOException;

import org.jointheleague.ecolban.rpirobot.IRobotAdapter;
import org.jointheleague.ecolban.rpirobot.IRobotInterface;
import org.jointheleague.ecolban.rpirobot.SimpleIRobot;

public class CleverRobot extends IRobotAdapter implements Runnable {

	// The following measurements are taken from the interface specification

	private boolean running;
	private final boolean debug = true; // Set to true to get debug messages.

	public CleverRobot(IRobotInterface iRobot) {
		super(iRobot);
		if (debug) {
			System.out.println("Hello. I'm CleverRobot");
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			IRobotInterface base = new SimpleIRobot();
			CleverRobot rob = new CleverRobot(base);
			rob.initialize();
			rob.run();
		} catch (InterruptedException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/* This method is executed when the robot first starts up. */
	private void initialize() throws IOException {
		// what would you like me to do, Clever Human?
		if(debug){
			System.out.println("Initializing...");
		}
	}

	public void run() {
		System.out.println("Running...");
		int leftSpeed = -50;
		int rightSpeed = 50;
		running = true;
		while (running) {
			try {
				driveDirect(leftSpeed, rightSpeed);
				Thread.sleep(500);
//				readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
				readSensors(SENSORS_GROUP_ID101);
				getLightBumps();
			} catch (IOException | InterruptedException e) {
				running = false;
				System.out.println(e.getClass());
			}
		}

		try {
			shutDown();
		} catch (IOException e) {
		}

	}

	private void shutDown() throws IOException {
		stop();
		closeConnection();
	}

}
