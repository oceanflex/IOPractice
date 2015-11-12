package ioexamples;

import java.lang.Object;
/*
 * @(#)FileHandler.java	1.00 May 5, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF 
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION 
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE 
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */

/**
 * The public interface for all file handler classes (adapters)
 * 
 * @author Jim Lombardo
 */
public interface FileHandler {
	/** The file type code for reading/writing standard binary files **/
	public static final int BIN_FILE = 0;
	
	/** The file type code for reading/writing serialized object files **/
	public static final int OBJ_FILE = 1;
	
	/** The file type code for reading/writing text files **/
	public static final int TEXT_FILE = 2;

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
	 */
	public String writeFile(String filePath, Object[] objects, int fileType, boolean append);

	/**
	 * Reads objects from a text or binary file. For binary file access,
	 * only serialized objects are supported.
	 * 
	 * @param filePath - the absolute or relative path to the file.
	 * @param fileType - a flag indicating the type of file to be accessed.
	 * @return an array objects, where the first object element will be
	 * null and the second object element will contain the data retrieved. If
	 * the first object element is not null, that element will contain an
	 * error message as a String.
	 */
	public Object[] readFile(String filePath, int fileType);
}
