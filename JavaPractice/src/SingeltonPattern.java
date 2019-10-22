
public class SingeltonPattern {

	
	private volatile static SingeltonPattern instance;
	
	private SingeltonPattern() {
		
	}
	
	public static SingeltonPattern getInstance()
	{		
		if(instance==null) {
			synchronized (SingeltonPattern.class) {
				if(instance==null) {
					instance = new SingeltonPattern();
				}
			}		
		}		
		return instance;
	}
	
}
