package driverFactory;

import org.testng.annotations.Test;

public class AppTest {
	@Test
	public void KickStart() throws Throwable 
	{
		DriverScript ds = new DriverScript();
		ds.starTest();
	}

}
