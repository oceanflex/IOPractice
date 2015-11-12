package ioexamples;

/*
 * @(#)ObjectFileHandler.java	1.00 May 5, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF 
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION 
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE 
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */

import java.io.*;
import java.util.*;

/**
 * @author Jim Lombardo
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ObjectFileHandler implements FileHandler {

	/**
	 * Writes an array of objects to a text or binary file. For binary file
	 * access, only serializable objects are supported.
	 * 
	 * @param filePath - the absolute or relative path to the file.
	 * @param objects - an array of objects.
	 * @param fileType - a flag indicating the type of file to be accessed.
	 * are used to indicate the type of file to be created or read.
	 * @param append - true or false when appending text files only. Ignored for objects.
	 * @return null if successfull, an error message if not.
	 * @see FileHandler#writeFile(java.lang.String, java.lang.Object[], int, boolean)
	 */
	public String writeFile(String filePath, Object[] objects, int fileType, boolean append) {
		String errorMessage = null; // if unchanged, method was successfull.
		File file = new File(filePath);
		
		// write to file of type typeCode
		switch(fileType) {
			case FileHandler.BIN_FILE:
//				try {
					return "Standard Binary file access is not currently supported.";
//				} catch(IOException ioe) {
//					errorMessage = ioe.getMessage();
//				}
//				break;
				
			case FileHandler.OBJ_FILE:
				try {
					// serialized objects only
					ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream( new FileOutputStream(file)) );
					
					// Write individual objects sequentially.
					for( int i=0; i<objects.length; i++) {
						out.writeObject( objects[i] );  // objects are written as type Object; cast required on retrieval
					}
					out.close();
				} catch(IOException ioe) {
					errorMessage = ioe.getMessage();
				}
				break;
		}
		return errorMessage; // if null, method was successfull.
	}

	/**
	 * Reads objects from a text or binary file. For binary file access,
	 * only serialized objects are supported.
	 * 
	 * @param filePath - the absolute or relative path to the file.
	 * @return an array objects, where the first object element will be
	 * null and the second object element will contain the data retrieved. If
	 * the first object element is not null, that element will contain an
	 * error message as a String.
	 * @see FileHandler#readFile(java.lang.String)
	 */
	public Object[] readFile(String filePath, int fileType) {
		Object[] objects = { null, null };
		Vector serializedObjects = new Vector();
		File file = new File(filePath);
		
		// write to file of type typeCode
		switch(fileType) {
			case FileHandler.BIN_FILE:
//				try {
			objects[0] = "Standard binary files are not supported. " +
				   "Read file operation is aborted.";
//				} catch(Exception e) {
//					objects[0] = e.toString();
//				}
//				break;
				
			case FileHandler.OBJ_FILE:
				try {
					// serialized objects only
					ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(file));
					
					// Read the individual objects sequentially.
					Object obj = in.readObject();  // objects are written as type Object; cast required on retrieval
					while( obj != null ) {
						serializedObjects.add(obj);
						obj = in.readObject();
					}
					in.close();
				} catch(EOFException eof) {
					// end of file, do nothing
				} catch(Exception e) {
					objects[0] = "Error in readFile(): " + e.toString();
				}
				break;
		}
		// be sure to repopulate array being returned
		objects[1] = serializedObjects;
		return objects;
	}

	public static void main(String[] args) {
		Date date = new Date();
		Integer myInt = new Integer(5);
		Object[] objects = new Object[2];
		objects[0] = date;
		objects[1] = myInt;
		ObjectFileHandler handler = new ObjectFileHandler();
		
		// write some objects
		String errorMessage = handler.writeFile("src/text.out",objects,
								FileHandler.OBJ_FILE,false);

		if(errorMessage != null) System.out.println(errorMessage);
		
		// read them back in
		objects[0] = null; // reset everything
		objects[1] = null;
		Vector v = null; // reset vector to empty
		objects = handler.readFile("src/text.out",FileHandler.OBJ_FILE);
		if(objects[0] != null) {
			System.out.println("Read Problem: " + objects[0]);
			System.exit(1);
		}
		// else
		System.out.println("Reading objects back in....");
		v = (Vector)objects[1];
		for(int i=0; i < v.size(); i++)
			System.out.println(v.get(i));
	}
}
