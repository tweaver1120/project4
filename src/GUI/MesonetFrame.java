package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import DataCollection.MapData;
import DataCollection.StatsType;

/**
 * Mesonet Frame class holds all of the panels and has the ability to open files, 
 * calculate different parts of the file based on what check boxes are selected, and 
 * exit the program.
 * 
 * @author Tim Weaver
 * @version 2018-12-05
 * 
 */
@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    /**
     * Calls a new Parameter Panel
     */
    private ParameterPanel paramPanel;
    
    /**
     * Calls a new Statistic Panel
     */
    private StatisticsPanel statisticsPanel;
    
    /**
     * Calls a new Table Panel
     */
    private TablePanel tablePanel;
    
    /**
     * Calls a new Bottom Panel
     */
    private BottomPanel bottomPanel;
    
    /**
     * Calls a new Parameter Panel
     */
    private TopBarFileMenu fileMenu;
    
    /**
     * Boolean that flags for the calculate button
     */
    private boolean flag = false;
    
    /**
     * MapData object to hold File Info
     */
    private MapData fileInfo = new MapData(0,0,0,0,0,"fileName");
    
    /**
     * MapData object to hold Data Info
     */
    protected MapData dataInfo;
    
    /**
     * Main frame that calls all panels 
     */
    public MesonetFrame()
    {
        //title of frame
        super("Mesonet - Statistics Calculator");
        setLayout(new BorderLayout());
        
        //create new panels
        paramPanel = new ParameterPanel();
        statisticsPanel = new StatisticsPanel();
        tablePanel = new TablePanel();
        bottomPanel = new BottomPanel();
        fileMenu = new TopBarFileMenu();
        
        //add panels to frame
        add(paramPanel, BorderLayout.WEST);
        add(statisticsPanel, BorderLayout.EAST);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(fileMenu, BorderLayout.NORTH);
        
        //set size of frame
        setSize(1000, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
       
    /**
     * Error Message that displays when you have no file selected
     */
    public static void errorMessage()
    {
        JOptionPane.showMessageDialog(null, "Please open a file", "Error", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Success Message that displays if you have successfully opened a file
     */
    public static void successMessage()
    {
        JOptionPane.showMessageDialog(null, "File successfully opened", "Success", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Bottom Panel class creates the calculate and exit buttons and displays 
     * the calculations on the table based on what check boxes are selected.
     * 
     * @author Tim Weaver
     * @version 2018-12-05
     * 
     */
    class BottomPanel extends JPanel implements ActionListener
    {
        /**
         * Calculate Button
         */
        private JButton calculate;
        
        /**
         * Exit Button
         */
        private JButton exit;
        
        /**
         * Bottom panel constructor
         */
        public BottomPanel()
        {
            //create calculate button
            calculate = new JButton("Calculate");
            //create exit button
            exit = new JButton("Exit");
            
            //add actions to panel
            calculate.addActionListener(this);
            exit.addActionListener(this);
            
            //add buttons to panel
            add(calculate);
            add(exit);
        }
        
        /**
         * Action event for calculate and exit buttons
         */
        public void actionPerformed(ActionEvent e)
        {
            //checks if calculate button is clicked
            if (e.getSource() == calculate && flag == true)
            {      
                //checks if TAIR is selected 
                if (paramPanel.TAIRSelected(e))
                {
                  //checks if MAX is selected
                    if (statisticsPanel.maxSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getUTCDateTimeString()), 0, 5);
                    }     
                    //checks if MIN is selected
                    if (statisticsPanel.minSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getUTCDateTimeString()), 0, 5);
                    } 
                    //checks if AVG is selected
                    if (statisticsPanel.avgSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getUTCDateTimeString()), 0, 5);
                    } 
                }
                //checks if TA9M is selected
                if (paramPanel.TA9MSelected(e))
                {
                    //checks if MAX is selected
                    if (statisticsPanel.maxSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getUTCDateTimeString()), 1, 5);
                    }
                    //checks if MIN is selected
                    if (statisticsPanel.minSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getUTCDateTimeString()), 1, 5);
                    }
                    //checks if AVG is selected
                    if (statisticsPanel.avgSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getUTCDateTimeString()), 1, 5);
                    } 
                }
                //checks if SRAD is selected
                if (paramPanel.SRADSelected(e))
                {
                    //checks if MAX is selected
                    if (statisticsPanel.maxSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getUTCDateTimeString()), 2, 5);
                    }
                    //checks if MIN is selected
                    if (statisticsPanel.minSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getUTCDateTimeString()), 2, 5);
                    }
                    //checks if AVG is selected
                    if (statisticsPanel.avgSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getUTCDateTimeString()), 2, 5);
                    } 
                }
                //checks if WSPD is selected
                if (paramPanel.WSPDSelected(e))
                {
                    //checks if MAX is selected
                    if (statisticsPanel.maxSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "WSPD").getStid()), 3, 0);
                        tablePanel.getTableModel().setTableModel("WSPD", 3, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 3, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "WSPD").getValue()), 3, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "WSPD").getNumberOfReportingStations()), 3, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "WSPD").getUTCDateTimeString()), 3, 5);
                    }
                    //checks if MIN is selected
                    if (statisticsPanel.minSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "WSPD").getStid()), 3, 0);
                        tablePanel.getTableModel().setTableModel("WSPD", 3, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 3, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "WSPD").getValue()), 3, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "WSPD").getNumberOfReportingStations()), 3, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "WSPD").getUTCDateTimeString()), 3, 5);
                    }
                    //checks if AVG is selected
                    if (statisticsPanel.avgSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "WSPD").getStid()), 3, 0);
                        tablePanel.getTableModel().setTableModel("WSPD", 3, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 3, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "WSPD").getValue()), 3, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "WSPD").getNumberOfReportingStations()), 3, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "WSPD").getUTCDateTimeString()), 3, 5);
                    } 
                }
                //checks if PRES is selected
                if (paramPanel.PRESSelected(e))
                {
                    //checks if MAX is selected
                    if (statisticsPanel.maxSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "PRES").getStid()), 4, 0);
                        tablePanel.getTableModel().setTableModel("PRES", 4, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 4, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "PRES").getValue()), 4, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "PRES").getNumberOfReportingStations()), 4, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "PRES").getUTCDateTimeString()), 4, 5);
                    }
                    //checks if MIN is selected
                    if (statisticsPanel.minSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "PRES").getStid()), 4, 0);
                        tablePanel.getTableModel().setTableModel("PRES", 4, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 4, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "PRES").getValue()), 4, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "PRES").getNumberOfReportingStations()), 4, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "PRES").getUTCDateTimeString()), 4, 5);
                    }
                    //checks if AVG is selected
                    if (statisticsPanel.avgSelected())
                    {
                        /*
                         * Adds information to different parts of the table in the order of 
                         * Station id, Parameter, Statistics, Value, Reporting Stations, Date
                         */
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "PRES").getStid()), 4, 0);
                        tablePanel.getTableModel().setTableModel("PRES", 4, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 4, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "PRES").getValue()), 4, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "PRES").getNumberOfReportingStations()), 4, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "PRES").getUTCDateTimeString()), 4, 5);
                    } 
                }
            }
            //Display error message if there is no file selected
            else if (e.getSource() == calculate && flag == false)
            {
                errorMessage();
            } 
            //closes the program if the exit button is selected
            else if (e.getSource() == exit)
            {
                System.exit(0);
            }             
        }       
    }
    
    /**
     * File Menu class creates the top bar with the file menu and has an open file
     * option as well as an exit option. Opening the file takes the file name and
     * directs the appropriate information to the Map Data class where it is parsed 
     * and calculated.
     * 
     * @author Tim Weaver
     * @version 2018-12-05
     * 
     */
    class TopBarFileMenu extends JMenuBar implements ActionListener
    {
        /**
         * Creates a new menu
         */
        private JMenu file;
        
        /**
         * Creates a new menu
         */
        private JMenuItem open;
        
        /**
         * Creates a new menu
         */
        private JMenuItem exit; 
        
        /**
         * Creates a new menu
         */
        private JFileChooser fileChooser;
        
        /**
         * Creates a new menu
         */
        private int option;
        
        /**
         * Creates a new menu
         */
        private File fileOpener;
        
        /**
         * Top Bar File Menu constructor
         */
        public TopBarFileMenu()
        {
            //new menu named file
            file = new JMenu("File");
            //new menu item for opening a data file
            open = new JMenuItem("Open Data File");
            //new menu item for exiting the program
            exit = new JMenuItem("Exit");
            
            //calls the action events
            open.addActionListener(this);
            exit.addActionListener(this);
            
            //adds the menu and menu items to the panel
            add(file);
            file.add(open);
            file.add(exit);      
        }
        
        /**
         * Performs the action of the open data file button and exit button
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //creates new file chooser
            fileChooser = new JFileChooser();
            //sets the director of the file chooser
            fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Owner\\eclipse-workspace\\project4\\data"));
            //Stores the file name
            String fileName = "";
            
            //opens a file
            if (e.getSource() == open)
            {
                option = fileChooser.showOpenDialog(null);
                //when a file is selected 
                if (option == JFileChooser.APPROVE_OPTION)
                {
                    //gets selected file
                    fileOpener = fileChooser.getSelectedFile();
                    //stores the file data in a new Map Data
                    dataInfo = new MapData(fileInfo.splitDate(fileOpener.getName())[0], (fileInfo.splitDate(fileOpener.getName())[1] - 1),
                            fileInfo.splitDate(fileOpener.getName())[2], fileInfo.splitDate(fileOpener.getName())[3],
                            fileInfo.splitDate(fileOpener.getName())[4], "data/");
                    
                    //creates a file name
                    fileName = "data/".concat(fileOpener.getName());  
                    //sets the file name
                    dataInfo.setFileName(fileName);
                    
                    //parses the data in the file
                    try
                    {
                        dataInfo.parseFile(fileName);
                    }
                    catch(Exception e2)
                    {
                        //displays error message if file is not parsed correctly
                        errorMessage();                      
                    }
                    //displays success message if file is successfully opened
                    successMessage();
                }
                //sets flag for display messages
                flag = true;
            }
            //closes the program
            else if (e.getSource() == exit)
            {
                System.exit(0);
            }
        }
    }
}
