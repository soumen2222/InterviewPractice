package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private CommonMarker lastClicked;
	private CommonMarker lastSelected;
	private HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550,new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();		
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	        System.out.println(m.getProperties());
			m.setRadius(5);			
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			//AirportRouteMarker airportRouteMarker = new AirportRouteMarker(route.getLocations(), route.getProperties());
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			int red = color(255, 0, 0);
			sl.setColor(red);
			sl.setHidden(true);
					
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		    routeList.add(sl);
		}
        
		
        map.addMarkers(routeList);
		map.addMarkers(airportList);
		
	}
	
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
		//loop();
	}

	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			AirportMarker marker = (AirportMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}

	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideRoutes();
			lastClicked = null;
			
		}
		else if (lastClicked == null) 
		{
			showRoutes();
		}
	}

	private void showRoutes() {
		// TODO Auto-generated method stub
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : airportList) {
			AirportMarker marker = (AirportMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
																
				//Show only the connecting routes	
				List<Location> desAirportLoc = new ArrayList<Location>();
				//List<SimpleLinesMarker> routes = new ArrayList<SimpleLinesMarker>();
				for (Marker routeM : routeList) {					
					SimpleLinesMarker routeMarker = (SimpleLinesMarker)routeM;
					List<Location> locations = routeMarker.getLocations();
					for (Location location : locations) {
						if (marker.getLocation().equals(location)){
							routeM.setHidden(false);
							int dest = Integer.parseInt((String)routeM.getProperty("destination"));
							System.out.println("Destinationcode: " + dest);
							desAirportLoc.add(airports.get(dest));
							//routes.add(routeMarker);
						}
					}							
				}	
				
				//Show only the connected airports
				
					for (Location location : desAirportLoc) {
						for (Marker mhide : airportList) {
							AirportMarker markerHide = (AirportMarker)mhide;
						if ((location.equals(markerHide.getLocation()))){
							markerHide.setHidden(true);
							}						
					}
													
				}
					for (Marker mhide1 : airportList) {
						AirportMarker markerHide1 = (AirportMarker)mhide1;
					if (!markerHide1.isHidden()){
						markerHide1.setHidden(true);
						}
					else
						markerHide1.setHidden(false);
					}
			
				return;
			}
		}		
	
		
	}

	private void unhideRoutes() {
		// TODO Auto-generated method stub
		for (Marker route : routeList) {
			route.setHidden(true);
			
		}
		
		for (Marker mhide : airportList) {
			mhide.setHidden(false);
			
		}
		
	}
	

}
