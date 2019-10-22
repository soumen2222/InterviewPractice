package demos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy1 extends PApplet {
	
UnfoldingMap map;
Map<String , Float> lifeExpByCountry;
List<Feature> countries ;
List<Marker> countryMarkers;
	
public void setup()
{
	size(800,600,OPENGL);
	map= new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
	MapUtils.createDefaultEventDispatcher(this, map);
	
	
	//load the life expectancy data from csv
	lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
	println("Loaded " + lifeExpByCountry.size() + " data entries");
	

	// Load country polygons and adds them as markers
	countries = GeoJSONReader.loadData(this, "countries.geo.json");
	countryMarkers = MapUtils.createSimpleMarkers(countries);
	map.addMarkers(countryMarkers);
	
	// Country markers are shaded according to life expectancy (only once)
			shadeCountries();
                   
}


private void shadeCountries() {
	for (Marker marker : countryMarkers) {
		// Find data for country of the current marker
		String countryId = marker.getId();
		if (lifeExpByCountry.containsKey(countryId)) {
			float lifeExp = lifeExpByCountry.get(countryId);
			// Encode value as brightness (values range: 40-90)
			int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
			marker.setColor(color(255-colorLevel, 100, colorLevel));
		}
		else {
			marker.setColor(color(150,150,150));
		}
	}
}

private Map<String, Float> loadLifeExpectancyFromCSV(String fileName) {
	// TODO Auto-generated method stub
	
	Map<String, Float> lifeExpByMap = new HashMap<String, Float>();
	String[] rows = loadStrings(fileName);
	for (String row : rows) {
		String[] columns = row.split(",");
		if (columns.length==6 && !columns[5].equals(".."))
		{
			Float value = Float.parseFloat(columns[5]);
			lifeExpByMap.put(columns[4], value);
		}
		
	}
	return lifeExpByMap;
}

public void draw()

{
	
	map.draw();
	
}
	

}
