package module6;


import java.util.List;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

/** Implements a common marker for cities and earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 *
 */
public abstract class CommonLineMarker extends SimpleLinesMarker {

	// Records whether this marker has been clicked (most recently)
	protected boolean clicked = false;
	
	public CommonLineMarker(List<Location> locations) {
		super(locations);
	}
	
	public CommonLineMarker(List<Location> locations, java.util.HashMap<java.lang.String,java.lang.Object> properties) {
		super(locations, properties);
	}
	
	// Getter method for clicked field
	public boolean getClicked() {
		return clicked;
	}
	
	// Setter method for clicked field
	public void setClicked(boolean state) {
		clicked = state;
	}
	
	// Common piece of drawing method for markers; 
	// YOU WILL IMPLEMENT. 
	// Note that you should implement this by making calls 
	// drawMarker and showTitle, which are abstract methods 
	// implemented in subclasses
	public void draw(PGraphics pg, float x, float y) {
		// For starter code just drawMaker(...)
		if (!hidden) {
			drawLineMarker(pg,x,y);
			if (selected) {
				showLineTitle(pg,  x,  y);
			}
		}
	}
	public abstract void drawLineMarker(PGraphics pg, float x, float y);
	public abstract void showLineTitle(PGraphics pg, float x, float y);
}