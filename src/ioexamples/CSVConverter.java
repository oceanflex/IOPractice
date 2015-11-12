package ioexamples;

/*
 * @(#)CSVConverter.java	1.07 12/21/04
 * author: Jim Lombardo
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
 * This class provides support for general purpose encoding/decoding of
 * data to/from the popular comma-separate values (CSV) text format. It requires,
 * and is limited to, <code>String</code>, Type Wrapper and/or <code>Date</code> 
 * objects for input.
 * <P>
 * Example of an incoming 2D <code>List</code> of objects:<br>
 * <CODE>
 * -------------------------------------------<br>
 * | String  |  Date   | Integer   | Boolean |<br>
 * -------------------------------------------<br>
 * | Integer |  Date   | (missing) | Double  |<br>
 * -------------------------------------------
 * </CODE>
 * <P>
 * Example of how the data is converted to CSV format where
 * {CRLF} indicates an escape-newline character:
 * <CODE>"FirstName","12/02/2003 02:03:04 CST",25,true{CRLF}</CODE>
 * <CODE>44,"12/02/2003 02:03:04 CST",,14.756{CRLF}</CODE>
 * <P>
 * Note that two consecutive commas indicates missing data.
 * Change History:
 * <UL>
 * <LI>v1.01 - mandate 4 date formats for decode.</LI>
 * <LI>v1.02 - allow incoming date values as String or Date objects in decode.</LI>
 * <LI>v1.03 - fixed decode bug where first record was duplicated.</LI>
 * <LI>v1.04 - fixed decode bug where commas in String values where incorrectly
 * interpreted as column delimiters.</LI>
 * <LI>v1.05 - changed signature of encode methods, removing parameter for header
 * row because it was unnecessary. Also changed signature of decode method to 
 * include a second parameter (boolean) that indicates whether csv data should 
 * be decoded and returned as <code>String</code> objects.</LI>
 * <LI>v1.06 - fixed decode bug where if the csv-formatted string parameter
 * did not have a CRLF as it's last character, the last data record would be
 * skipped. Now the code is self-healing. It checks for the CRLF as the last
 * character and adds it if it does not exist.</LI>
 * <LI>v1.07 - fixed decode bug where if the csv-formatted string parameter
 * contained column data that began with a numeric character (e.g. "3abc"), the
 * data was interpreted as a number. Now the rules have been enhanced: column
 * data is numeric if the first character and all successive characters are numeric,
 * or a decimal point is present; otherwise it must be a String.</LI>
 * </UL>
 *
 * @version 1.07, 12/2/2004
 * @author 	Jim Lombardo
 */
public class CSVConverter {
	private static final String ROW_DELIMITER = "\n";
	private static final String COL_DELIMITER = ",";
	private static final char COL_DELIMITER_CHAR = ',';
	private static final String EMPTY_STR = "";
	private static final String DBL_QUOTE = "\"";
	private static final String DELIMITERS = 
			COL_DELIMITER + ROW_DELIMITER + DBL_QUOTE;     //  ",\n\"";
	private Iterator rowIterator;
	private Iterator colIterator;
	private boolean DEBUG = false;
		
	/**
	 * Converts a 2D <code>List</code> of objects to comma-separated value (CSV) string, 
	 * with or without a header row containing column names.
	 * 
	 * @param columnValues - a 2-D List of row/column data, 
	 * e.g., a Vector representing rows, containing Vectors,
	 * representing columns of data. Data is limited to <code>String</code>,
	 * primitive type wrapper and/or <code>Date</code> objects.
	 * @return a CSV-encoded <code>String</code>
	 */
	public String encode(List columnValues) {
		StringBuffer buf = new StringBuffer();
		
		// Encode rows (records); may include header row
		rowIterator = columnValues.iterator();
		while( rowIterator.hasNext() ) {
			List cols = (List)rowIterator.next(); // get columns for this row
			colIterator = cols.iterator();
			
			while( colIterator.hasNext() ) {	
				Object data = colIterator.next();

				// Surround Strings with double quotes
				// Use consecutive commas for missing data
				if( data instanceof String ) {
					buf = data.equals(EMPTY_STR) ? buf.append(COL_DELIMITER) : 
						buf.append(DBL_QUOTE).append(data).append(DBL_QUOTE).append(COL_DELIMITER);
				} else if( data instanceof java.util.Date ) {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm:ss z");
					String formattedDate = sdf.format( (Date)data );
					buf.append(DBL_QUOTE).append(formattedDate).append(DBL_QUOTE).append(COL_DELIMITER);
				} else {
					buf = (data == null) ? buf.append(COL_DELIMITER) : 
						buf.append(data).append(COL_DELIMITER);
				}
			}
			
			// Now strip away garbage at end of row
			buf = buf.charAt( buf.length()-1 ) == COL_DELIMITER_CHAR ? 
				buf.replace( buf.length()-1,buf.length(),EMPTY_STR ) : buf;
			
			// Now add row delimiter if necessary
			buf = rowIterator.hasNext() ? buf.append(ROW_DELIMITER) : buf;
		}
		return buf.toString();
	}
	
	/**
	 * Converts a 2D array of <code>String</code> objects to a comma-separated value (CSV) 
	 * string, with or without a header row containing column names.
	 * 
	 * @param columnValues - a 2-D <code>String</code> array of row/column data. 
	 * @return a CSV-encoded <code>String</code>
	 */
	public String encode(String[][] columnValues) {
		StringBuffer buf = new StringBuffer();
				
		// Encode data rows
		for(int i=0; i < columnValues.length; i++) {
			for(int j=0; j < columnValues[i].length; j++ ) {
				Object data = columnValues[i][j];

				// Surround Strings with double quotes
				// Use consecutive commas for missing data
				if( data instanceof String ) {
					buf = data.equals(EMPTY_STR) ? buf.append(COL_DELIMITER) : 
						buf.append(DBL_QUOTE).append(
							data).append(DBL_QUOTE).append(COL_DELIMITER);
				} else {
					buf = (data == null) ? buf.append(COL_DELIMITER) : 
						buf.append(data).append(COL_DELIMITER);
				}
			}
			
			// Now strip away garbage at end of row
			buf = buf.charAt( buf.length()-1 ) == COL_DELIMITER_CHAR ? 
				buf.replace( buf.length()-1,buf.length(),EMPTY_STR ) : buf;
			
			// Now add row delimiter if necessary
			buf.append(ROW_DELIMITER);
		}
		return buf.toString();
	}
	
	/**
	 * Decodes a CSV-encoded <code>String</code> into a 2-D Vector of row-column data. 
	 * Data is returned as <code>String, Date, Long, Double and/or Boolean</code> objects 
	 * only, depending on the boolean value of the outputStrings argument. 
	 * 
	 * <P>Note: dates must be in one of the following formats, or they will
	 * be treated as Strings:</P>
	 * <UL><LI>12/02/2003 02:03:04 CST</LI>
	 * <LI>12/02/03 02:03:04 CST</LI>
	 * <LI>12/02/2003</LI>
	 * <LI>12/02/03</LI>
	 * </UL>
	 * 
	 * @param csv - a <code>String</code> containing csv-encoded data
	 * @param outputStrings - if <code>true</code>, all data will be decoded
	 * as <code>String</code> objects regardless of origin; if <code>false</code> 
	 * data will be converted to <code>String, Date, Boolean and/or Long objects. 
	 * Users will need to further process these objects to produce the actual results needed.
	 * @return a 2-D Vector of row-column data, or null if not available
	 * @throws <code>Exception</code> if csv String cannot be decoded due
	 * to unknown formats, such as date formats that don't match specification.
	 */
	public Vector decode(String csv, boolean outputStrings) {
		// Make sure incoming string has some content
		if(csv == null) return null;
		// Make sure string ends with a carriage return
		if( !csv.endsWith("\n") ) csv += "\n";
		// OK, now create parent Vector for rows (records)
		Vector rows = new Vector();
		
		// Each row has a Vector of columns (fields)
		Vector col = new Vector();
		
		String colValue = "";  // scratch variable
		boolean isRowDelim = false;
		boolean isText = false;
		boolean isBeginCol = false;
		
		for( int i=0; i < csv.length(); i++ ) {
			
			if( csv.charAt(i) == '"' && !isText ) {
				isText = true;
				isBeginCol = true;
				continue;
			} else if( csv.charAt(i) == '"' && isText ) {
				// end of col; colValue is complete
				isText = false;
				isBeginCol = false;
				continue;
			} else if ( !isText && csv.charAt(i) == ',' ) {
				// end of col; colValue is complete
				isBeginCol = false;
			} else if ( !isText && csv.charAt(i) == '\n' ) {
				// end of col, end of row; col and row are complete
				isBeginCol = false;
				isRowDelim = true;
			} else {
				colValue += csv.charAt(i);
				continue;
			}
			
			// setup for new row
			if( isRowDelim && !isBeginCol ) {
				// strip off escape-newline char
				colValue = colValue.substring(0,colValue.length());
			}
				
				if( outputStrings ) {
					col.add( colValue );
					
				} else if( colValue.equals("true") || colValue.equals("false") ) {
						col.add( new Boolean(colValue) );
						
				} else if( colValue.indexOf("GMT") >= 0 ) { // unhandled Date format -- treat as string
					col.add( colValue );
					
				// handle mm/dd/yyyy dates with time and region encoding
				} else if( (colValue.length() == 23 || colValue.length() == 19) && colValue.charAt(2) == '/' ) {
					Calendar cal = Calendar.getInstance();
					int mm = Integer.parseInt( colValue.substring(0,2) );
					int dd = Integer.parseInt( colValue.substring(3,5) );
					int yyyy = Integer.parseInt( colValue.substring(6,10) );
					int hr = Integer.parseInt( colValue.substring(11,13) );
					int min = Integer.parseInt( colValue.substring(14,16) );
					int sec = Integer.parseInt( colValue.substring(17,19) );
					cal.set( yyyy,mm,dd,hr,min,sec );
					Date date = cal.getTime();
					col.add( date );
					
				// handle mm/dd/yy dates with time and region encoding
				} else if( (colValue.length() == 21 || colValue.length() == 17) && colValue.charAt(2) == '/' ) {
					Calendar cal = Calendar.getInstance();
					int mm = Integer.parseInt( colValue.substring(0,2) );
					int dd = Integer.parseInt( colValue.substring(3,5) );
					int yy = Integer.parseInt( colValue.substring(6,8) );
					int hr = Integer.parseInt( colValue.substring(9,11) );
					int min = Integer.parseInt( colValue.substring(12,14) );
					int sec = Integer.parseInt( colValue.substring(15,17) );
					cal.set( yy+2000,mm,dd,hr,min,sec );
					Date date = cal.getTime();
					col.add( date );
					
				// handle mm/dd/yyyy dates without time and region encoding
				} else if( colValue.length() == 10 && colValue.charAt(2) == '/' ) {
					Calendar cal = Calendar.getInstance();
					int mm = Integer.parseInt( colValue.substring(0,2) );
					int dd = Integer.parseInt( colValue.substring(3,5) );
					int yyyy = Integer.parseInt( colValue.substring(6,10) );
					cal.set( yyyy,mm,dd );
					Date date = cal.getTime();
					col.add( date );
					
				// handle mm/dd/yy dates without time and region encoding
				} else if( colValue.length() == 8 && colValue.charAt(2) == '/' ) {
					Calendar cal = Calendar.getInstance();
					int mm = Integer.parseInt( colValue.substring(0,2) );
					int dd = Integer.parseInt( colValue.substring(3,5) );
					int yy = Integer.parseInt( colValue.substring(6,8) );
					cal.set( yy+2000,mm,dd );
					Date date = cal.getTime();
					col.add( date );
				
				// This else if block was updated in v1.07 to more accurately determine numeric values.
			    // Rule: for this column data to be interpreted as a number, the first character
				// must be a number. If first character is a number, others might be too.
				} else if( colValue.charAt(0) >= '0' && colValue.charAt(0) <= '9' ) {
				    // loop through all characters -- if all numbers, the string is a number
				    // decimal point is OK, too, because it's used in floating point
				    boolean numericFlag = true;
				    for(int j=0; j < colValue.length(); j++) {
				        // if it's not a number and not a decimal point, must be a letter
				        if( colValue.charAt(j) > '9' && colValue.charAt(j) != '.' ) {
				            numericFlag = false;
				        	break;
				        }
				    }
				    //	if numeric, do additional interrogation
				    if( numericFlag ) {
				        // if decimal point found it's a double, else a long
						if( colValue.indexOf(".") == -1 ) { 
							if(DEBUG) System.out.println(colValue);
							col.add( new Long(colValue) );
						} else {  // must be a double
							col.add( new Double( colValue) );
						}
				    }
                //	must be a String
				} else {
					col.add( (String)colValue );
				} // end inner if
				colValue = "";
			
			if (isRowDelim) {
				rows.add(col);
				col = new Vector();
				isRowDelim = false;
			}
			
		} // end for
		return rows;
	}
		
	/*
	 * Unit test harness
	 * @param args - an array of Strings: NOT USED
	 */
//	public static void main(String[] args) {
//		// Encoding...
//		ArrayList rows = new ArrayList();
//		ArrayList cols = new ArrayList();
//		cols.add(new Integer(1));
//		cols.add("head");
//		cols.add("body");
//		cols.add( new Date() );
//		rows.add(cols);
//		
//		cols = new ArrayList();
//		cols.add("Pete");
//		cols.add("Best");
//		cols.add(new Double(2.54)); //new Double(64));
//		cols.add(new Boolean(false)); //new Double(64));
//		rows.add(cols);
//		
//		CSVConverter conv = new CSVConverter();
//		String csv = conv.encode(rows);
//		System.out.println( csv );
//		
//		String[][] rowMatrix = { {"Jim","Lombardo","25"}, {"Pete","Best",null} };
//				
//		conv = new CSVConverter();
//		csv = conv.encode(rowMatrix);
//		System.out.println( csv );
//
//		// Decoding...
//		Vector dataset;
//		String s = "1,\"WCTC-3\",\"head3,jim\",2.54,false,\"12/08/2003 12:00:00 CST\"\n\"WCTC-2\",\"head2\",\"body2\",\"12/08/2003 12:00:00 CST\"\n\"WCTC-1\",\"head1\",\"body1\",\"12/08/2003 12:00:00 GMT-0800\"\n";
//			dataset = (Vector)conv.decode(s,false);
//			for(int i=0; i<dataset.size(); i++) {
//				Vector row = (Vector)dataset.get(i);
//				for(int j=0 ; j < row.size(); j++) {
//					Object col = row.get(j);
//					System.out.println(col.getClass().getName() + ": " + col.toString() );
//				}
//				System.out.println("----- end record -----------" );
//			}
//
//	}
	
}

