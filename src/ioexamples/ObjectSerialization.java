package ioexamples;

/*
 * Simple Demo of Object Serialization
 * 
 * by Jim Lombardo, WCTC Lead Java Instructor
 */
 
 import java.io.*;
 import java.util.*;

// You MUST implement the Serializable interface! 
public class ObjectSerialization implements Serializable {
	// Create a bunch of instance variables to see if we can save/restore
	//  ... First, make some objects
	Hashtable hash;
	String s1;
	Date d1;
	Integer objInt;
	//  ... Next, make some primitives
	int x;
	double dbl;
	boolean flag;
	transient double doNotSerialize = 2.15;  // transient prevents save
	
	// Finally, reate a class variable field to see if we can save/restore
	// Note: it's a known fact that static fields do not serialize (see notes below)
	static final int MAX = 100;
	
	// Constructor initializes intance variables
	public ObjectSerialization() {
		hash = new Hashtable();
		s1 = "Hello World";
		d1 = new Date();
		objInt = new Integer(5);
		hash.put("String value",s1);
		hash.put("Date value", d1);
		hash.put("Integer value (object)", objInt);
		x = 8;
		dbl = 21.25897d;
		flag = true;
	}

	public static void main(String[] args) {
		// Now create two instances of this class
		ObjectSerialization test = new ObjectSerialization(); // this one prevents static context problems in main
		ObjectSerialization test2 = null;		// this one stores the deserialized object from file
		
		System.out.println("Step 1: Serializing objects to \'test.out\' ...");
		
		// Modified 5-7-03 by JGL. Added buffering and close() operation.
		try {
			ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream( new FileOutputStream("src/test.out")) );
			// Serialize two objects
			out.writeObject(test);
			out.writeObject(test.hash);
			out.close();  // don't forget to do this! I have.
		} catch (IOException ioe) {
			System.out.println("Serialization FAILED due to " +
				"\n" + ioe.toString());
			System.exit(1);
		} finally {
			System.out.println(" >2 objects successfully serialized.");
		}
		
		System.out.println("\nStep 2: Deserializing objects from \'src/test.out\' ...");

		try {
			ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("src/test.out"));
			// Read the objects back in (don't forget to downcast from Object type)
			test2 = (ObjectSerialization)in.readObject();
			Hashtable hash2 = (Hashtable)in.readObject();
			test2.hash = hash2;  // stupid, but proves it works on both test and hash objects
		} catch (Exception e) {
			System.out.println("Deserialization FAILED due to " +
				"\n" + e.toString());
			System.exit(1);
		} finally {
			System.out.println(" >2 objects successfully deserialized.");			
		}
		
		// Display results of object reconstitution
		System.out.println("\nExtracted key/value pairs from objects:");
		System.out.println("-----------------------------");
		System.out.println("Value of String s1: " + test2.s1);
		System.out.println("Value of Date d1: " + test2.d1);
		System.out.println("Value of Integer object objInt: " + test2.objInt);
		System.out.println("Value of int x: " + test2.x);
		System.out.println("Value of double dbl: " + test2.dbl);
		System.out.println("Value of boolean flag: " + test2.flag);
		System.out.println("Values in Hashtable... ");
		Enumeration values = test2.hash.elements();
		Enumeration keys = test2.hash.keys();
		while( keys.hasMoreElements() ) {
			System.out.println(" Key: " + keys.nextElement() + 
				"; Value: " + values.nextElement());
		}
		/* Now try to retrieve static field...
		 * This field is NEVER serialized because it's static, which means
		 * that it's in the class definition in memory. You cannot serialize
		 * static fields because the fields do not exist in the object instances.
		 * Therefore, we can retrieve it, but only from the static context, i.e.,
		 * Test.MAX (Classname.Fieldname). And since this is loaded into memory
		 * on startup each time, it really doesn't need to be saved to disk anyway.
		 */ 
		System.out.println("\nValue of static int field MAX: " + ObjectSerialization.MAX + 
			" (retrieved from class definition, never serialized)");

	}
}
