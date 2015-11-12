package ioexamples;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FirstLastFileManager {
	private String firstName;
	private String lastName;
	private Component comp;
        private File selectedFile;
	
	public FirstLastFileManager(Component parentComponent) {
		comp = parentComponent;
	}
	
	public void loadDataFromFile() throws Exception {
	   	// Build a file chooser component that displays files only
	   	JFileChooser fileChooser = new JFileChooser();
	   	fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
	   	fileChooser.addChoosableFileFilter(new TxtFilter());
	   	
	   	// check if user clicked Cancel button on dialog, if so, return
	   	int result = fileChooser.showOpenDialog( comp );
	   	if( result == JFileChooser.CANCEL_OPTION ) {
	   		throw new Exception("Load operation cancelled.");
	   	}
	   	
		// Display all files available in FileChooser dialog
		selectedFile = fileChooser.getSelectedFile();  
                
	    if (selectedFile != null && selectedFile.exists()){
	 	   BufferedReader in = new BufferedReader(
	 						 new FileReader(selectedFile));
	 	   String line = in.readLine();
	 	   String[] tokens = line.split("=");
	 	   if( tokens[0].equalsIgnoreCase("name") ) {
	 		   String[] names = tokens[1].split(" ");
	 		   firstName = names[0];
	 		   lastName = names[1];
	 	   }
	 	   in.close();
	     }
	}
	
	public void saveDataToFile(String first, String last, boolean isSaveAs)
	throws IllegalArgumentException, Exception {
		setFirstName(first);
		setLastName(last);
                
                // If no selected file from load operation then let user
                // choose wehre to save file, otherwise us Save As ...
                if(selectedFile == null || !selectedFile.exists() || isSaveAs) {
		
                    // Build a file chooser component that displays files only
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
                    fileChooser.setApproveButtonText("Save");
                    fileChooser.setDialogTitle("Save Name Changes");
                    fileChooser.addChoosableFileFilter(new TxtFilter());

                    // check if user clicked Cancel button on dialog, if so, return
                    int result = fileChooser.showOpenDialog( comp );
                    if( result == JFileChooser.CANCEL_OPTION ) {
                            throw new Exception("Save operation cancelled.");
                    }
                    selectedFile = fileChooser.getSelectedFile();
                }
		  
                PrintWriter out = new PrintWriter(
                                     new BufferedWriter(
                                     new FileWriter(selectedFile, false)));
                out.println("name=" + firstName + " " + lastName);
                out.close();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setFirstName(String value) throws IllegalArgumentException {
		if( value.length() == 0 || value.length() == 0 ) {
			String msg = "First name is a required field. Please try again.";
			throw new IllegalArgumentException(msg);
		}
		firstName = value;
	}
	
	public void setLastName(String value) throws IllegalArgumentException {
		if( value.length() == 0 || value.length() == 0 ) {
			String msg = "Last name is a required field. Please try again.";
			throw new IllegalArgumentException(msg);
		}
		lastName = value;
	}
}
