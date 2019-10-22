package module6;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportRouteMarker extends CommonLineMarker {
	
	public AirportRouteMarker(ShapeFeature route) {
		super(route.getLocations(), route.getProperties());
	
	}
	
	public AirportRouteMarker(List<Location> locations, HashMap<String, Object> properties) {
		super(locations, properties);
	
	}

	@Override
	public void drawLineMarker(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		pg.fill(255,0,0);
		
		
	}

	@Override
	public void showLineTitle(PGraphics pg,float x, float y) {
		// TODO Auto-generated method stub
		
		
	}

	
	
	
}
