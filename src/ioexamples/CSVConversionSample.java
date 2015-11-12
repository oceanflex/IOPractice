package ioexamples;

/*
 * @(#)CSVConversionSample.java	1.00 May 12, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF 
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION 
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE 
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */
 
 import java.util.*;

/**
 * This class demonstrates the use of the CSVConverter class in
 * the com.bmpr.util package (bmpr.jar). You install the jar file
 * in the directory <jdk home>/jre/lib/ext on your computer. Then you must
 * make sure your eclipse project has a reference to it. Select
 * Project -> Properties. At the Properties dialog box, click on the Libraries
 * tab. Now click on the "Add External JARS..." button and select the bmpr.jar
 * file where you stored it.
 * 
 * Note that this demonstration calls on the services of the CSVConverter
 * class directly. In your News Release project you will do this by delegating
 * from MasterController to DataConverter (Controller) to CSVConverterAdapter to
 * CSVConverter.
 * 
 * @author Jim Lombardo
 */
public class CSVConversionSample {

	public static void main(String[] args) {
		/* 
		 * According to the javadoc for CSVConverter you will need a 
		 * 2D List or 2D array of Objects when performing an encode. To
		 * encode means to convert data to another form. What you will
		 * need to do is gather all of the data for all of the news items
		 * that you want to encode and create a 2D list from that data.
		 * 
		 * Here's how you do that:
		 *   Imagine a spreadsheet program where each row represented one
		 *   news item (a record, in database terms). Now imagine that each
		 *   column represents a property in a NewsItem object (recordID,
		 *   head, body, releaseDate). As the documentation points out, you
		 *   have several options for the kinds of objects you can put in
		 *   you 2D list (read it carefully).
		 * 
		 *   So what we'll do is create two list objects -- one for the rows
		 *   and the other for the columns. First, we'll create releaseRecords,
		 *   which is a list object that will store all of the news items (one
		 *   entry or row for each news item).
		 */
		List releaseRecords = new Vector();
		/* 
		 * Now create the the columns list, we'll call it releaseFields.
		 * You'll need to repopulate this for each news item.
		 */
		List releaseFields = new Vector();
		/*
		 * Now populate one news item. The fields are the properties in the
		 * NewsItem object. So releaseFields represents one news item. Notice
		 * that you can use different types of objects -- see the javadoc for
		 * CSVConverter. Also note that on output, only strings are surrounded
		 * by quotation marks.
		 */
		releaseFields.add( new Integer(1) );  // example with ReleaseID as Integer
		releaseFields.add("Headline 1");
		releaseFields.add("Body 1");
		releaseFields.add( "10/11/2003" ); // Release Date as String
		/*
		 * Now put the first news item in the releaseRecords list.
		 */
		releaseRecords.add( releaseFields );
		/*
		 * OK, let's do another news item. If this were used in your
		 * News Release project, you would need some kind of looping
		 * mechanism to extract all the items from your hashtable. Note
		 * that we are resetting releaseFields to an empty list. Why?
		 * Because we are repopulating it with data for the next news item.
		 */
		releaseFields = new Vector();
		releaseFields.add("WCTC-2");  // example with ReleaseID as String
		releaseFields.add("Headline 2");
		releaseFields.add("Body 2");
		releaseFields.add( "12/08/2003" ); // example with data as an object
		/*
		 * Now put the second news item in the releaseRecords list.
		 */
		releaseRecords.add( releaseFields );
		/*
		 * OK now to use the CSVConverter, we need to instantiate it.
		 */
		CSVConverter converter = new CSVConverter();
		/*
		 * Then just call the encode method and retrieve the
		 * converted string. Notice that we are not using the header
		 * in this example.
		 */
		String csvString = converter.encode(releaseRecords);
		// output the converted string to the console to see it.
		System.out.println( "Here are two news item records (CSV encoded) WITHOUT a " +
								"header row:\n\n" + csvString );
		/*
		 * Let's do it again, but this time with a header. The
		 * header is another List -- strings representing the field names.
		 * Make this the first record in releaseRecords.
		 */
		List header =new Vector();
		header.add("releaseID");
		header.add("headline");
		header.add("body");
		header.add("releaseDate");
		releaseRecords.add(0,header);
		csvString = converter.encode(releaseRecords);
		// output the converted string to the console to see it.
		System.out.println( "\nHere are two news item records (CSV encoded) PRECEEDED BY a " +
							"header row:\n\n" + csvString );
							
		// SEE HOW SIMLE THAT WAS! NOW LET'S DECODE...
		
		/*
		 * In this example we are going to recreate our 2D List
		 * and output its contents to prove that the decode method
		 * recreated all of the original objects. Again, read the javadoc
		 * information for CSVConverter decode method carefully.
		 * So let's begin by passing the csvString we just created to
		 * the decode method. By the way, did you notice that the
		 * javadoc states that decode throws a checked exception?
		 */
		 List original = null; // first declare it
		 try {
			original = converter.decode(csvString, true); // now populate it
		 } catch(Exception e) {
		 	System.out.println("Decode failed due to: " + e);
		 }
		 /*
		  * OK, if it worked we can print out the list to the
		  * console, taking advantage of the built-in toString method
		  * to output its contents.
		  */
		 System.out.println("\nHere's the contents " +
		 					"of the original list (CSV decoded):\n\n" + original);
	}
}
