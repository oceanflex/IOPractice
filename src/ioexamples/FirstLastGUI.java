package ioexamples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * An example of a main GUI window that does a user login operation.
 * When the "Submit" button is clicked, this window is hidden, and then
 * it opens a result window. When the result window is closed (hidden)
 * it redisplays this window by making it visible again.
 * 
 * @author Jim Lombardo
 */
public class FirstLastGUI extends JFrame implements ActionListener {
	private JLabel labFirst, labLast;
	private JTextField txtFirst, txtLast;
	private JButton btnLoad, btnSave, btnSaveAs, btnCancel;
	private JPanel fieldPanel, btnPanel;
	private Container c;
	private GridLayout gridLayout;
	private FlowLayout flowLayout;
        private FirstLastFileManager fileMgr;
	
	public FirstLastGUI() {
		super("Manage User Name");
		initGUI();
	}

	private void initGUI() {
		// Set default look and feel for system
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (Exception e) {
            // Likely the L&F class is not in the class path; ignore.
        }
        // Center window on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point(d.width / 2 - getSize().width / 2,
            d.height / 2 - getSize().height / 2));
        
        // Get root window content pane
        c = getContentPane();

        // fieldPanel holds labels and text fields
        // Put this in center of content pane
        labFirst = new JLabel("First: ");
        labFirst.setFont( new Font("Arial",Font.BOLD, 12));
        labFirst.setHorizontalAlignment(JLabel.TRAILING);
        txtFirst = new JTextField(20);
        txtFirst.requestFocus();
        labLast = new JLabel("Last Name: ");
        labLast.setFont( new Font("Arial",Font.BOLD, 12));
        labLast.setHorizontalAlignment(JLabel.TRAILING);
        txtLast = new JTextField(20);
        fieldPanel = new JPanel();
        gridLayout = new GridLayout(2,1);
        gridLayout.setVgap(5);
        fieldPanel.setLayout(gridLayout);
        fieldPanel.add(labFirst);
        fieldPanel.add(txtFirst);
        fieldPanel.add(labLast);
        fieldPanel.add(txtLast);
        // Create some whitespace around panel
        fieldPanel.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        c.add(fieldPanel, BorderLayout.CENTER);
        
        // btnPanel holds all buttons
        // Put this in the south of the content pane
        btnLoad = new JButton(" Load... ");
        btnLoad.setActionCommand("1");
        btnLoad.addActionListener(this);
        btnSave = new JButton("Save");
        btnSave.setActionCommand("2");
        btnSave.addActionListener(this);
        btnSaveAs = new JButton("Save As...");
        btnSaveAs.setActionCommand("3");
        btnSaveAs.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.setActionCommand("4");
        btnCancel.addActionListener(this);
        btnPanel = new JPanel();
        flowLayout = new FlowLayout(FlowLayout.TRAILING);
        btnPanel.setLayout( flowLayout );
        btnPanel.add(btnLoad);
        btnPanel.add(btnSave);
        btnPanel.add(btnSaveAs);
        btnPanel.add(btnCancel);
        // create some whitespace around buttons
        btnPanel.setBorder( BorderFactory.createEmptyBorder(0,0,5,5) );
        c.add(btnPanel, BorderLayout.SOUTH);
        
        // set default behaviors
        this.getRootPane().setDefaultButton(btnSave);
		JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(340,140);
        this.setVisible(true);
	}

	// A generic event handler
	public void actionPerformed(ActionEvent ae) {
                final int LOAD = 1;
                final int SAVE = 2;
                final int SAVEAS = 3;
                boolean isSaveAs = false;
                
		String cmd = ae.getActionCommand();
		
		switch( Integer.parseInt(cmd) ) {
			case LOAD:
				// Load from file
				try {
					fileMgr = new FirstLastFileManager(this);
					fileMgr.loadDataFromFile();
					txtFirst.setText(fileMgr.getFirstName());
					txtLast.setText(fileMgr.getLastName());
				} catch(Exception e) {
					String msg = "Error loading text from file: " + e.getMessage();
					JOptionPane.showMessageDialog(this,msg,"File Load Eror", JOptionPane.WARNING_MESSAGE);
				}
				break;
                            
			case SAVE:
				// save edits
                                isSaveAs = false;
                                
				try {
					if(fileMgr == null) throw new Exception();
					fileMgr.saveDataToFile(txtFirst.getText(), txtLast.getText(), isSaveAs);
                                        JOptionPane.showConfirmDialog(this, "Changes saved successfully!");
				} catch(IllegalArgumentException ile) {
					JOptionPane.showMessageDialog(this,ile.getMessage(),
							"Invalid Entry", JOptionPane.WARNING_MESSAGE);
				} catch(Exception e) {
					String msg = "Error saving text to file: " + e.getMessage();
					JOptionPane.showMessageDialog(this,msg,"File Save Eror", JOptionPane.WARNING_MESSAGE);
				}
				break;
                            
                        case SAVEAS:
				// save as 
                                isSaveAs = true;
				try {
					if(fileMgr == null) throw new Exception();
					fileMgr.saveDataToFile(txtFirst.getText(), txtLast.getText(), isSaveAs);
				} catch(IllegalArgumentException ile) {
					JOptionPane.showMessageDialog(this,ile.getMessage(),
							"Invalid Entry", JOptionPane.WARNING_MESSAGE);
				} catch(Exception e) {
					String msg = "Error saving text to file: " + e.getMessage();
					JOptionPane.showMessageDialog(this,msg,"File Save Eror", JOptionPane.WARNING_MESSAGE);
				}
				break;
                            
			default:		
				System.exit(0);
		}
	}

}
