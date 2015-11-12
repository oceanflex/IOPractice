package ioexamples;

/*
 * @(#)BinaryWriterApp.java	1.00 Nov 25, 2003
 *
 * A class that writes binary data to a file, with buffering.
 * Note that this is much, much harder than using object serialization.
 * (See the ObjectSerialization examples)
 */
import java.io.*;

public class BinaryWriterApp{
	
   public static void main(String[] args) throws IOException{
   	// The '.dat' extension is optional, but common for binary files
   	// (short for 'data')
	File data = new File("src/example.dat");

	  DataOutputStream out = new DataOutputStream(
							 new BufferedOutputStream(
							 new FileOutputStream(data)));
	  out.writeInt(5);
	  out.writeChar('c');
	  out.writeBoolean(true);
	  out.writeUTF("Java");
	  out.writeChar('\n');
	  out.writeChars("End of file");
	  out.close();
   }
}
