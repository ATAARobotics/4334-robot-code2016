package acilibj.actuators;

import edu.wpi.first.wpilibj.Relay;

/**
 * @author Jayden Chan
 * Date Created: January 10 2015
 * Last Updated: December 07 2015
 * 
 * Class that simplifies Spike relay modules.
 */

public class Spike 
{

	Relay spike;
	
	public Spike(int port)
	{
		spike = new Relay(port);
	}
	
	public static enum Value
	{
		kOff(0),
		
		kOn(1);
		
		public final int value;
		
		private Value(int value)
		{
			this.value = value;
		}
	}
	
	public void set(Value value)
	{
		
	}
	
}
