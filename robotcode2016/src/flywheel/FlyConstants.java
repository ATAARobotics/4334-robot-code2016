package flywheel;

public class FlyConstants {
	final int TICKS_PER_WHEEL_ROTATION = 1;
	final int THREAD_TIME = 100;
	final double kP = 0.05;
	final double kI = 0.01;
	final double kD = 0;
	final double intLim = 10000;
	
	public FlyConstants(){
		
	}
	
	public int getTicksPerWheelRotation(){
		return TICKS_PER_WHEEL_ROTATION;
	}
	public int getThreadTime(){
		return THREAD_TIME;
	}
	public double getkP(){
		return kP;
	}
	public double getkI(){
		return kI;
	}
	public double getkD(){
		return kD;
	}
	public double getintLim(){
		return intLim;
	}
}
