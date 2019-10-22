

public class PatternDemo {

	public static void main(String[] args) {

		// builder pattern demo;
		
		BuilderPattern b1 = new BuilderPattern.Builder().setVar1("Soumen").setVar2("Ghosh").build();
		BuilderPattern b2 = new BuilderPattern.Builder().setVar1("Soumen").setVar2("Ghosh1").build();
		
		System.out.println(b1);
		System.out.println(b2);
		
		// Singleton Pattern Demo
		
		SingeltonPattern p1 = SingeltonPattern.getInstance();
		
		Thread t = new Thread ( () -> {
			SingeltonPattern p2 = SingeltonPattern.getInstance();
			System.out.println("Inside differnt thread" + p2+ " " + Thread.currentThread().getName());
		});
		
		t.start();

		System.out.println(p1);
		
		
		
	}

}
