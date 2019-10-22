package com.honeywell.thread;

import java.util.HashMap;
import java.util.Map;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String, SafePoint> locations = new HashMap<String, SafePoint>();
		SafePoint value = new SafePoint(5,6);
		locations.put("Soumen", value );
		PublishingVehicleTracker p = new PublishingVehicleTracker(locations);
		p.setLocation("Soumen", 7, 8);
		SafePoint Variable = p.getLocation("Soumen");
		Variable.set(11, 12);
		int a[] = Variable.get();
		System.out.println(a[0] + "  " + a[1]);
		
		Map<String, Point> points = new HashMap<String, Point>();
		points.put("ghosh1", new Point(5,6) );
		points.put("ghosh2", new Point(4,6) );	
		
		DelegatingVehicleTracker p1 = new DelegatingVehicleTracker(points);
		p1.setLocation("ghosh1", 9, 8);
		Map<String, Point> var = p1.getLocations();
			
		for(String id:var.keySet())
		{
			System.out.println("Values are " + var.get(id).get());
		}
		
		Map<String, MutablePoint> locations1 = new HashMap<String, MutablePoint>();
		MutablePoint val1 = new MutablePoint();
		locations1.put("ghosh3", val1 );
		
		MonitorVehicleTracker m = new MonitorVehicleTracker(locations1);
		m.setLocation("ghosh3", 9, 10);
		Map<String, MutablePoint> locresult = m.getLocations();
		MutablePoint val2 = locresult.get("ghosh3");
		MutablePoint val3 = m.getLocation("ghosh3");
		System.out.println("Values of ghosh3 are  " + val3.x + " " +val3.y );
		val2.x = 5;
		val2.y = 9;
		for(String id:locresult.keySet())
		{
			System.out.println(id+ ":" + locresult.get(id).x + " " + locresult.get(id).y );
		}

		
	}

}
