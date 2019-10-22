package demos;

public class ArrayLocation {
	
	private double coords[];
	
	public ArrayLocation(double[] coords )
	{
		this.coords = coords;
	}
	
		
	public static void main(String[] args)
	{
		double[] coords ={5.0 ,4.0};
		ArrayLocation arr = new ArrayLocation(coords);
		coords[0] = 55;
		coords[1] = 45;
		System.out.println(arr.coords[0]);
	}

}
