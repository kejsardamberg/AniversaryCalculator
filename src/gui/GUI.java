package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import dateCalculator.DateOfImportance;
import testHelpers.AllTestsSuite;


/***
 * This utility is used to produce calendar entries for dates of notice after some event
 * @author jordam
 * 
 */
public class GUI extends javax.swing.JFrame {

	private dateCalculator.AniversaryList aniversaryList = new dateCalculator.AniversaryList();
	private static final long serialVersionUID = 1L;
	private javax.swing.JList<String> jListAniversaryDates; //= new javax.swing.JList<String>();
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JScrollPane scrollPaneAniversaries = new JScrollPane(jListAniversaryDates);
    private UtilDateModel model = new UtilDateModel();
    private JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
    private JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
	private final javax.swing.JTextField textFieldAniversaryName	= new javax.swing.JTextField();
	//private final javax.swing.JTextField textFieldDate	 			= new javax.swing.JTextField();
    private final javax.swing.JTextField textFieldFilePath 			= new javax.swing.JTextField();
	private final javax.swing.JButton buttonCreateAniversaryList 	= new javax.swing.JButton();
	private final javax.swing.JButton buttonHelpAbout			 	= new javax.swing.JButton();
	private final javax.swing.JButton buttonFilePicker				= new javax.swing.JButton();
	private final javax.swing.JButton buttonClearList				= new javax.swing.JButton();
	private final javax.swing.JButton buttonCreateIcsFiles 			= new javax.swing.JButton();
	private final javax.swing.JButton buttonRunSelfTests 			= new javax.swing.JButton();
    private final javax.swing.JLabel labelDateText 					= new javax.swing.JLabel();
    private final javax.swing.JLabel labelAniversaryName			= new javax.swing.JLabel();
    private final javax.swing.JLabel labelOutputFilePath			= new javax.swing.JLabel();
    private final javax.swing.JLabel labelBragText 					= new javax.swing.JLabel();
    private final javax.swing.JCheckBox checkBoxThousandDays		= new javax.swing.JCheckBox( "Every thousand days" );
    private final javax.swing.JCheckBox checkBoxIncludePassed		= new javax.swing.JCheckBox( "Include events in past" );
    private final javax.swing.JCheckBox checkBoxIncludeHalfYears	= new javax.swing.JCheckBox( "Include half years" );
    private final javax.swing.JCheckBox checkBoxEveryYear			= new javax.swing.JCheckBox( "Every year" );
    private final javax.swing.JCheckBox checkBoxSpecial				= new javax.swing.JCheckBox( "Special days" );
    private final javax.swing.GroupLayout layout 					= new javax.swing.GroupLayout(getContentPane());


	public GUI (){
		initComponents();
	}
 
	private void initComponents() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();
		
		this.setStaticTexts();

		this.setupDefaultValuesAndStates();

		this.setLookAndFeel(screenWidth, screenHeight);
		
        setDefaultCloseOperation ( javax.swing.WindowConstants.EXIT_ON_CLOSE );
        setTitle ( "Aniversary reminder calculator" );

        buttonClearList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	setupDefaultValuesAndStates();
            }
        });

        buttonRunSelfTests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	//Test();
            	StringBuilder resultText = new StringBuilder();
            	int runned = 0;
            	int failed = 0;
            	int ignore = 0;
            	long runtime = 0;
        		//org.junit.runner.JUnitCore.main("suneido.AllTestsSuite");
            	Class<?>[] classes = AllTestsSuite.AllTestsRunner.findClasses();
            	for (Class<?> eachClass : classes) {
            		Result result = org.junit.runner.JUnitCore.runClasses(eachClass);
        			runned += result.getRunCount();
        			failed += result.getFailureCount();
        			ignore += result.getIgnoreCount();
        			runtime += result.getRunTime();
            		if(result.getFailureCount() > 0 ){
            			resultText.append( result.getFailureCount() + " errors encountered while executing " + eachClass.toString() + "." + System.lineSeparator());
            			List<Failure> failures = result.getFailures();
            			for(Failure failure : failures){
            				resultText.append("   " + failure.toString() + System.lineSeparator());
            			}
            			resultText.append(System.lineSeparator());
            		} else {
            			resultText.append( result.getRunCount() + " tests in " + eachClass.toString() + " tested ok." + System.lineSeparator());
            		}
            	}
            	resultText.append(System.lineSeparator() + "Test summary" + System.lineSeparator());
            	resultText.append("===============================================" + System.lineSeparator());
            	resultText.append("Total number of test classes: " + classes.length + System.lineSeparator());
            	resultText.append("Runned tests: " + runned + System.lineSeparator());
            	resultText.append("Passed tests: " + (runned - failed - ignore) + System.lineSeparator());
            	resultText.append("Failed tests: " + failed + System.lineSeparator());
            	resultText.append("Ignored tests: " + ignore + System.lineSeparator());
            	resultText.append("Total runtime: " + runtime + " milliseconds" + System.lineSeparator() );
            	GuiRectangle rect = new GuiRectangle(screenWidth/4, screenHeight/50, (runned-failed-ignore), failed, ignore);
            	System.out.println(resultText.toString());
            	JPanel resultPanel = new JPanel();
            	resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
            	JTextArea resultArea = new JTextArea();
            	resultArea.setText(resultText.toString());
            	resultArea.setFont(new Font(Font.SANS_SERIF, 3, screenHeight/70));
            	JFrame resultFrame = new JFrame("Test results");
            	resultPanel.add(resultArea);
            	resultPanel.add(rect);
            	resultFrame.add(resultPanel);
            	resultFrame.pack();
            	resultFrame.setVisible(true);
            }
        });

        buttonCreateIcsFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String folderName = textFieldFilePath.getText();
            	if (folderName.substring(folderName.length(), folderName.length()) != "\\"){
            		folderName = folderName + "\\";
            	}
            	//System.out.println("Creating ICS files for " + jListAniversaryDates.getSelectedIndices().length + " chosen calendar entries.");
            	if (textFieldFilePath.getText().length() > 0){
                   	for(int day : jListAniversaryDates.getSelectedIndices()){
                   		aniversaryList.aniversaries.get(day).toIcsFile(folderName);
                	}
            	}
            }
        });

        buttonFilePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame frame = new JFrame("Select output folder");
                frame.setSize(500, 700);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                String filePath = filePicker(frame);
                textFieldFilePath.setText ( filePath );
            }
        });
 
        buttonHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame frame = new JFrame("Help");
                String helpText = 
                		"This utility aims at providing means for creating aniversary " + System.lineSeparator() +
                		"reminders for any event." + System.lineSeparator() + System.lineSeparator() +
                		"It also includes some unusual dates, like counting thousand " + System.lineSeparator() + 
                		"days and so forth." + System.lineSeparator() + System.lineSeparator() +
                		"Use this utility by entering an event name and a date (make " + System.lineSeparator() +
                		"sure the year is the right one), and pay attention to the " + System.lineSeparator() +
                		"check boxes before clicking the button to generate reminder events." + System.lineSeparator() + System.lineSeparator() +
                		"After generation event reminders will be shown (unless no " + System.lineSeparator() + 
                		"one is found within search scope). Mark the ones you find " + System.lineSeparator() +
                		"interresting, and click the button for creation of calendar " + System.lineSeparator() + 
                		"reminders. These are produced for marked events, and placed " + System.lineSeparator() + 
                		"as .ICS files in the folder path provided." + System.lineSeparator() + System.lineSeparator() +
                		"The folder path is updated by the folder picker invoked by " + System.lineSeparator() +
                		"the 'Browse...' button." + System.lineSeparator() + System.lineSeparator() +
                		"For command line usage; use argument '/help' for guidance." + System.lineSeparator() + System.lineSeparator() +
                		"Backlogg and known bugs and limitations:" + System.lineSeparator() + 
                		"----------------------------------------" + System.lineSeparator() + 
                		"* Include differentiated aniversaries (every year the first X years, then every 10 years)" + System.lineSeparator() + 
                		"* Check if it's possible to make date picker dialogue to scale for resolution" + System.lineSeparator() + 
                		"* Check if it's possible to make file picker dialogue to scale for resolution, or remove button" + System.lineSeparator() + 
                		"* Add (optional) date reminder in calendar notification" + System.lineSeparator() + 
                		"* Make sure ics file creation is regional settings agnostic" + System.lineSeparator() + 
                		"* Test utility for numerous environments (calendars, screen setups, OSes)" + System.lineSeparator() + 
                		"* Check if it's possible to check if entry already is in calendar" + System.lineSeparator() + 
                		"* Maybe add all chosen reminders in the same ics file?" + System.lineSeparator() + 
                		"* Refactor code" + System.lineSeparator() + 
                		"* Trailing \\in folder path should be ensured" + System.lineSeparator() + 
                		"* Export as runable jar file" + System.lineSeparator() + 
                		"* Increase tooltip text size and background contrast" + System.lineSeparator() + 
                		"* Include in version management system" + System.lineSeparator() + 
                		"* Create CLI help text" + System.lineSeparator() +
                		"* Make scroll bars in help window scale by resolution like in aniversaryList" + System.lineSeparator() +
                		"* Make sure CLI usage includes all the functionality of the GUI" + System.lineSeparator() +
                		"* Publish code and product" + System.lineSeparator() + System.lineSeparator() +
                		"Error reporting, suggestions and comments to \"kejsardamberg@hotmail.com\"";
                
                javax.swing.JTextArea panel = new javax.swing.JTextArea(helpText);
                JScrollPane scrollPanel = new JScrollPane(panel);
                panel.setFont(new Font(Font.SANS_SERIF, 3, screenHeight/70));
                frame.add(scrollPanel);
                frame.setSize(screenWidth/3, screenHeight/2);
                frame.setVisible(true);
            }
        });

        
        buttonCreateAniversaryList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	//DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            	Date aniveraryDate = new Date();
				//try {
				//	aniveraryDate = format.parse(textFieldDate.getText());
				//} catch (ParseException e) {
				//	e.printStackTrace();
				//}
				aniveraryDate = (Date) datePicker.getModel().getValue();
            	aniversaryList.specialDays = checkBoxSpecial.isSelected();
            	aniversaryList.everyThousandDays = checkBoxThousandDays.isSelected();
            	aniversaryList.everyYear = checkBoxEveryYear.isSelected();
            	aniversaryList.includeEventsInThePast = checkBoxIncludePassed.isSelected();
            	aniversaryList.dateOfImportance = new DateOfImportance(textFieldAniversaryName.getText(), aniveraryDate);
            	aniversaryList.CreateAniversaryList();
            	ArrayList<String> dateStrings = new ArrayList<String>();
            	for(dateCalculator.AniversaryDay aniveraryDay : aniversaryList.aniversaries){
            		listModel.addElement(aniveraryDay.toString());
            		dateStrings.add(aniveraryDay.toString());
            	}
            	//System.out.println("Added entries for " + listModel.size() + " calculated elements.");
            	if(listModel.size() > 0 ){
            		buttonCreateIcsFiles.setEnabled(true);
            	}
            	jListAniversaryDates.setListData((String[]) dateStrings.toArray(new String[0]));
            }
        });
        
 
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup ( layout.createSequentialGroup()
                .addContainerGap()
                .addGroup ( layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		               .addGroup(layout.createSequentialGroup()
		                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                       .addComponent(labelAniversaryName)
		                       .addComponent(textFieldAniversaryName)
		                       .addComponent(labelDateText)
	//	                       .addComponent(textFieldDate)
		                       .addComponent(datePicker)
		               )
		               .addGroup(layout.createSequentialGroup()
		                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                       .addComponent(buttonCreateAniversaryList)
		                       .addComponent(buttonCreateIcsFiles)
		               )
		               .addGroup(layout.createSequentialGroup()
		            		   .addComponent(labelOutputFilePath)
		            		   .addComponent(textFieldFilePath)
		            		   .addComponent(buttonFilePicker)
		                       		    		   )
		               .addGroup(layout.createSequentialGroup()
		            		   .addComponent(checkBoxEveryYear)
		            		   .addComponent(checkBoxSpecial)
		            		   .addComponent(checkBoxThousandDays)
		            		   .addComponent(checkBoxIncludePassed)
		            		   .addComponent(checkBoxIncludeHalfYears)
		    		   )
		               .addGroup(layout.createSequentialGroup()
		                       .addComponent(scrollPaneAniversaries)
		               )
		               .addGroup(layout.createSequentialGroup()
		                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                       .addComponent(labelBragText)
		                       .addComponent(buttonClearList)
		                       .addComponent(buttonHelpAbout)
		                       .addComponent(buttonRunSelfTests)
		               )
	              )
              )
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonFilePicker});

        layout.setVerticalGroup(
            layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelAniversaryName)
                        .addComponent(textFieldAniversaryName)
                        .addComponent(labelDateText)
    //                    .addComponent(textFieldDate)
                        .addComponent(datePicker)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonCreateAniversaryList)
                .addComponent(buttonCreateIcsFiles)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelOutputFilePath)
                        .addComponent(textFieldFilePath)
                        .addComponent(buttonFilePicker)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkBoxEveryYear)
                        .addComponent(checkBoxSpecial)
                        .addComponent(checkBoxThousandDays)
                        .addComponent(checkBoxIncludePassed)
                        .addComponent(checkBoxIncludeHalfYears)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scrollPaneAniversaries)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBragText)
                    .addComponent(buttonClearList)
                    .addComponent(buttonHelpAbout)
                    .addComponent(buttonRunSelfTests)
                )
            )
        );
 
        setSize(screenWidth/2,screenHeight/2);
	}
	

	private void setupDefaultValuesAndStates(){
    	//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
        textFieldFilePath.setText ("");
        textFieldAniversaryName.setText("");
//        textFieldDate.setText(format.format(new Date()));
        
        // Get the temporary directory as default path
        textFieldFilePath.setText 			( System.getProperty("java.io.tmpdir") );
        
        buttonCreateIcsFiles.setEnabled(false);
        listModel.removeAllElements();
        checkBoxEveryYear.setSelected(true);
        checkBoxIncludePassed.setSelected(false);
        checkBoxSpecial.setSelected(true);
        checkBoxThousandDays.setSelected(true);
        checkBoxIncludeHalfYears.setSelected(true);
        jListAniversaryDates.setListData(new String[0]);
        aniversaryList.aniversaries.clear();
        model.setSelected(true);
	}
	
	private void setStaticTexts(){
        labelDateText.setText				( "Original aniversary date" );
        labelAniversaryName.setText			( "Aniverary reason" );
        labelOutputFilePath.setText			( "Output folder for calendar reminder files" );
        buttonFilePicker.setText 			( "Browse for output folder..." );
        buttonClearList.setName				("btnClearList");
        buttonCreateAniversaryList.setText	( "Create aniversary dates" );
        buttonCreateIcsFiles.setText 		( "Create reminder files (marked entries)" );
        buttonClearList.setText				( "Clear/reset" );
        buttonHelpAbout.setText				( "Help" );
        buttonRunSelfTests.setText			( "Run self test" );
        labelBragText.setText 				( "Utility created by Jörgen Damberg. Use at your own risk." );
        textFieldAniversaryName.setToolTipText( "Set the name of the event of interrest. E.g. 'John's birth'");
        datePicker.setToolTipText			( "Set the date of the original event, in the date format '2015-03-26'");
        //textFieldDate.setToolTipText("Set the date of the original event, in the date format '2015-03-26'");
        textFieldFilePath.setToolTipText	( "Set the folder for the optional output files. E.g. 'C:\\Temp\\'");
        checkBoxEveryYear.setToolTipText	( "Check if events for yearly aniversary should be created");
        checkBoxIncludeHalfYears.setToolTipText( "Check to make half year aniversaries apply" );
        listModel = new DefaultListModel<String>();
        jListAniversaryDates = new JList<String>(listModel);
        jListAniversaryDates.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListAniversaryDates.setSelectedIndex(0);
        jListAniversaryDates.addListSelectionListener(null);
        jListAniversaryDates.setVisibleRowCount(15);
        scrollPaneAniversaries = new JScrollPane(jListAniversaryDates);
        
        //For testability
        buttonCreateAniversaryList.setName	("btnCreateAniversaryList");
        buttonCreateIcsFiles.setName		("btnCreateIcsFiles");
        buttonFilePicker.setName			("btnFilePicker");
        buttonHelpAbout.setName				("btnHelp");
        textFieldAniversaryName.setName		("txtAniversaryName");
        textFieldFilePath.setName			("txtFilePath");
        datePicker.setName					("datePicker");
        datePanel.setName					("datePanel");
        jListAniversaryDates.setName		("listAniversaryDates");
        scrollPaneAniversaries.setName		("scrollPaneAniversaries");
        checkBoxEveryYear.setName			("checkboxYear");
        checkBoxIncludePassed.setName		("checkboxPassedDates");
        checkBoxSpecial.setName				("checkboxSpecialDays");
        checkBoxIncludeHalfYears.setName	("checkboxHalfYears");
        checkBoxThousandDays.setName		("checkboxThousandDays");
        buttonRunSelfTests.setName			("buttonRunSelfTests");
	}
	
	private void setLookAndFeel(int screenWidth, int screenHeight){
		Font f = new Font(Font.SANS_SERIF, 3, screenHeight/70);

		datePicker.setForeground(Color.white);
		datePicker.setBackground(Color.white);
		datePicker.setTextEditable(true);
		datePicker.setFont(f);
        datePanel.setFont(f);
        
		JFormattedTextField textField = datePicker.getJFormattedTextField();
		textField.setFont(f);

		labelAniversaryName.setFont(f);
        labelBragText.setFont(f);
        labelBragText.setForeground(Color.GRAY);
        labelDateText.setFont(f);
        labelOutputFilePath.setFont(f);

        checkBoxEveryYear.setFont(f);
        scaleCheckBoxIcon(checkBoxEveryYear);

        checkBoxIncludeHalfYears.setFont(f);
        scaleCheckBoxIcon(checkBoxIncludeHalfYears);
        
        checkBoxIncludePassed.setFont(f);
        scaleCheckBoxIcon(checkBoxIncludePassed);
        
        checkBoxSpecial.setFont(f);
        scaleCheckBoxIcon(checkBoxSpecial);
        
        checkBoxThousandDays.setFont(f);
        scaleCheckBoxIcon(checkBoxThousandDays);
        
        textFieldAniversaryName.setFont(f);
        textFieldFilePath.setFont(f);
        
        buttonClearList.setFont(f);
        buttonHelpAbout.setFont(f);
        buttonCreateAniversaryList.setFont(f);
        buttonCreateIcsFiles.setFont(f);
        buttonRunSelfTests.setFont(f);
        buttonFilePicker.setFont(f);
        
        jListAniversaryDates.setFont(f);

        scrollPaneAniversaries.getVerticalScrollBar().setPreferredSize(new Dimension(screenWidth/90,0));
        scrollPaneAniversaries.getHorizontalScrollBar().setPreferredSize(new Dimension(screenWidth/90,0));
	}
	
	
	public static void scaleCheckBoxIcon(JCheckBox checkbox){
	    boolean previousState = checkbox.isSelected();
	    checkbox.setSelected(false);
	    FontMetrics boxFontMetrics =  checkbox.getFontMetrics(checkbox.getFont());
	    Icon boxIcon = UIManager.getIcon("CheckBox.icon");
	    BufferedImage boxImage = new BufferedImage(
	        boxIcon.getIconWidth(), boxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
	    );
	    Graphics graphics = boxImage.createGraphics();
	    try{
	        boxIcon.paintIcon(checkbox, graphics, 0, 0);
	    }finally{
	        graphics.dispose();
	    }
	    ImageIcon newBoxImage = new ImageIcon(boxImage);
	    Image finalBoxImage = newBoxImage.getImage().getScaledInstance(
	        boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH
	    );
	    checkbox.setIcon(new ImageIcon(finalBoxImage));

	    checkbox.setSelected(true);
	    Icon checkedBoxIcon = UIManager.getIcon("CheckBox.icon");
	    BufferedImage checkedBoxImage = new BufferedImage(
	        checkedBoxIcon.getIconWidth(),  checkedBoxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
	    );
	    Graphics checkedGraphics = checkedBoxImage.createGraphics();
	    try{
	        checkedBoxIcon.paintIcon(checkbox, checkedGraphics, 0, 0);
	    }finally{
	        checkedGraphics.dispose();
	    }
	    ImageIcon newCheckedBoxImage = new ImageIcon(checkedBoxImage);
	    Image finalCheckedBoxImage = newCheckedBoxImage.getImage().getScaledInstance(
	        boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH
	    );
	    checkbox.setSelectedIcon(new ImageIcon(finalCheckedBoxImage));
	    checkbox.setSelected(false);
	    checkbox.setSelected(previousState);
	}
	
   private String filePicker ( JFrame frame ) {
        String filePath = textFieldFilePath.getText();
	    JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Select output folder");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      //System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
	      //System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
	      //filePath = chooser.getCurrentDirectory().toString();
		    if(chooser.getSelectedFile().isDirectory()){
		    	filePath = chooser.getSelectedFile().toString(); 
		    } else {
		    	filePath = chooser.getCurrentDirectory().toString();
		    }
        }
	    else {
	      //System.out.println("No Selection ");
	      }
        return filePath;
    }

   /*
   private void Test(){
	   testHelpers.GuiTesting gt = new testHelpers.GuiTesting();
	   gt.Click(buttonClearList);
	   gt.Click(textFieldAniversaryName);
	   gt.SetText(textFieldAniversaryName, "dummyEvent");
	   gt.Click(buttonCreateAniversaryList);
	   gt.Click(buttonClearList);
   }
   */
   
   
   
}
