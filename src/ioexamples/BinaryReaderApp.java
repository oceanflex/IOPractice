package ioexamples;

/*
 * @(#)BinaryReaderApp.java	1.00 Nov 25, 2003
 *
 * A class that reads binary data from a file, with buffering.
 * Note that this is much, much harder than using object serialization.
 * (See the ObjectSerialization examples)
 */
import java.io.*;

public class BinaryReaderApp{
   public static void main(String[] args) throws IOException{
	// The '.dat' extension is optional, but common for binary files
	// (short for 'data')
	File data = new File("src/example.dat");
		  
	  if (data.exists()){
		 DataInputStream in = new DataInputStream(
							  new BufferedInputStream(
							  new FileInputStream(data)));

		 int number = in.readInt();
		 System.out.println(number);

		 char letter = in.readChar();
		 System.out.println(letter);

		 boolean value = in.readBoolean();
		 System.out.println(value);

		 String string = in.readUTF();
		 System.out.println(string);

		 in.readChar();

		 // each character is represented by 2 bytes, so we
		 // must divide available bytes by 2 to get each character
		 int numberOfChars = in.available()/2;
		 String characters = "";
		 for (int i = 0; i < numberOfChars; i++){
			char c = in.readChar();
			characters += c;
		 }
		 System.out.println(characters);

		 in.close();

	  }
   }
}
