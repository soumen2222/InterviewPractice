package com.honeywell.exercise;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class InputStreamExp {
   private static String SOURCE_FILE = "D:\\test\\Hello.txt";
   private static String TARGET_FILE = "D:\\test\\Hello.zip";

   public static void main(String[] args) {
      try {
 
         createZipFile();
 
         FileInputStream fin= new FileInputStream(TARGET_FILE);
         CheckedInputStream checksum = new CheckedInputStream(fin, new Adler32());
byte[] buffer = new byte[1024];
         while(checksum.read(buffer, 0, buffer.length) >= 0){        
 
      } 

         System.out.println("Checksum: " + checksum.getChecksum().getValue());      
      } catch(IOException ioe) {
         System.out.println("IOException : " + ioe);
      }
   }
   
   private static void createZipFile() throws IOException{
      FileOutputStream fout = new FileOutputStream(TARGET_FILE);
      CheckedOutputStream checksum = new CheckedOutputStream(fout, new Adler32());
      ZipOutputStream zout = new ZipOutputStream(checksum);

      FileInputStream fin = new FileInputStream(SOURCE_FILE);
      zout.putNextEntry(new ZipEntry(SOURCE_FILE));
      int length;
      byte[] buffer = new byte[1024];
      while((length = fin.read(buffer)) > 0) {
         zout.write(buffer, 0, length);
      }

      zout.closeEntry();
      fin.close();
      zout.close();
   }
}