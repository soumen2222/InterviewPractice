package com.boot.soumen;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.soumen.boot.controller.HomeController;

public class AppTest {

	@Test
    public void testApp() {
		HomeController hc = new HomeController();
		String result = hc.home();
        assertEquals( result, "Das Boot ,reporting for today" );
	}
}
