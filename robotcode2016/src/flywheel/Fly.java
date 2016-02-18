package flywheel;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;

public class Fly {
	private long lastTime;
	private Counter hallEffect;
	private int rpm;
	private int lastTicks; 
	private FlyConstants constants;
	private SpeedController s1;
	private int desiredRPM;
	private FlywheelBangBang bangBang;
	
	public Fly(Counter hallE, FlyConstants con, FlywheelBangBang bang) {
		hallEffect = hallE;
		constants = con;
		lastTime = System.currentTimeMillis();
		lastTicks = hallEffect.get();
		bangBang = bang;
	}
	
	private void bangSetSpeed(){
		int rpm = getRpm();
		int status = bangBang.getStatus(rpm);
		if(status == 1){
			//motor is running faster than desired
			setFlyPow(0.0);
		}
		if(status == 0){
			//do nothing because motor is perfect just the way it is <3
		}
		if(status == -1){
			//motor is running slower than desired
			setFlyPow(1.0);
		}
	}
	
	private void setFlyPow(double pow){
		//we don't want to run the flywheel backwards things will explode
		if(pow < 0){
			pow = 0; 
		}
		s1.set(pow);
		
	}
	
	public int getRpm() {
		// I've never used this
		// if we're using
		// get rate of encoder ticks (ticks/sec)

		double changeInTime = (System.currentTimeMillis() - lastTime);
		// ticks / ms
		int currentTicks = hallEffect.get();
		try{
		double rate = (currentTicks - lastTicks) / changeInTime;
		lastTicks = currentTicks;
		// convert to rotations / s
		int speed = (int) (rate / constants.getTicksPerWheelRotation());
		// convert to rpm
		speed = (speed * 60) * 1000;
		System.out.println("fly speed = " + speed);
		return speed;
		}
		catch(ArithmeticException e){
			System.out.println(e);
			return 0;
			
		}
	}
}
