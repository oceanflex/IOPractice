package ioexamples;

/*
 * Created on Dec 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.io.File;

/**
 * A simple FileChooser filter class
 * @author Jlombardo
 *
 */
public class TxtFilter extends javax.swing.filechooser.FileFilter {
	// accept directory and clp files
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		if ( f.getName().endsWith(".txt") ) {
			return true;
		} else {
			return false;
		}
	}

	// description of this filter
	public String getDescription() {
		return "Text files (*.txt)";
	}
}

