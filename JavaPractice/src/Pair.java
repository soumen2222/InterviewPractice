import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

public class Pair implements Serializable {
	private final int number;
	private final String name;


	public Pair(int number, String name) {
		this.number = number;
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		writeObject();
		readData();
	}

	private static void writeObject() throws FileNotFoundException, IOException {
		final FileOutputStream fos = new FileOutputStream("C:\\TEMP\\new.txt");
		final ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeInt(101);
		oos.writeBoolean(false);
		oos.writeUTF("Writing a string");
		final Pair pair = new Pair(42, "Forty two");
		oos.writeObject(pair);
		oos.flush();
		oos.close();
		fos.close();
	}

	public static void readData() throws IOException, ClassNotFoundException {
		final FileInputStream fis = new FileInputStream("C:\\\\TEMP\\\\new.txt");
		final ObjectInputStream ois = new ObjectInputStream(fis);
		final int number = ois.readInt();
		final boolean bool = ois.readBoolean();
		final String string = ois.readUTF();
		final Pair pair = (Pair) ois.readObject();
		System.out.println("Print class data" + pair.getName() + pair.getNumber());
		
		}
	
}