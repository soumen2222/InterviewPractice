package com.soumen.generic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;

public class MultipleBound {

	/**
	 * @param args
	 */

	public static <S extends Readable & Closeable, T extends Appendable & Closeable> void copy(
			S src, T trg, int size) throws IOException {
		try {
			CharBuffer buf = CharBuffer.allocate(size);
			int i = src.read(buf);
			while (i >= 0) {
				buf.flip(); // prepare buffer for writing
				trg.append(buf);
				buf.clear(); // prepare buffer for reading
				i = src.read(buf);
			}
		} finally {
			src.close();
			trg.close();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int size = 32;
		FileReader r = null;
		FileWriter w = null;
		try {
			r = new FileReader("C:\\fine.in.txt");
			w = new FileWriter("C:\\fine.out.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			copy(r, w, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = null;
		BufferedWriter bw = null; 
		try {
			br = new BufferedReader(new FileReader("C:\\fine.in.txt"));
			bw = new BufferedWriter(new FileWriter("C:\\fine.out.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			copy(br, bw, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
