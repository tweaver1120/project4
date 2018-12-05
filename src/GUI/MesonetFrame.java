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
import javax.swing.JPanel;
import DataCollection.MapData;
import DataCollection.StatsType;


@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    private ParameterPanel paramPanel;
    private StatisticsPanel statisticsPanel;
    private TablePanel tablePanel;
    private BottomPanel bottomPanel;
    private TopBarFileMenu fileMenu;
    
    private MapData fileInfo = new MapData(0,0,0,0,0,"fileName");
    protected MapData dataInfo;
    
    public MesonetFrame()
    {
        super("Mesonet - Statistics Calculator");
        setLayout(new BorderLayout());
        
        paramPanel = new ParameterPanel();
        statisticsPanel = new StatisticsPanel();
        tablePanel = new TablePanel();
        bottomPanel = new BottomPanel();
        fileMenu = new TopBarFileMenu();
        
        add(paramPanel, BorderLayout.WEST);
        add(statisticsPanel, BorderLayout.EAST);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(fileMenu, BorderLayout.NORTH);
        
        setSize(1000, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
    
    class BottomPanel extends JPanel implements ActionListener
    {
        private JButton calculate;
        private JButton exit;
        
        public BottomPanel()
        {
            calculate = new JButton("Calculate");
            exit = new JButton("Exit");
            
            calculate.addActionListener(this);
            exit.addActionListener(this);
            
            add(calculate);
            add(exit);
        }
        
        public void actionPerformed(ActionEvent e)
        {
            
            if (e.getSource() == calculate)
            {               
                if (paramPanel.TAIRSelected(e))
                {
                    if (statisticsPanel.maxSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TAIR").getUTCDateTimeString()), 0, 5);
                    }     
                    
                    if (statisticsPanel.minSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TAIR").getUTCDateTimeString()), 0, 5);
                    } 
                    
                    if (statisticsPanel.avgSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getStid()), 0, 0);
                        tablePanel.getTableModel().setTableModel("TAIR", 0, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 0, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getValue()), 0, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getNumberOfReportingStations()), 0, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TAIR").getUTCDateTimeString()), 0, 5);
                    } 
                }
                
                if (paramPanel.TA9MSelected(e))
                {
                    if (statisticsPanel.maxSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "TA9M").getUTCDateTimeString()), 1, 5);
                    }
                    
                    if (statisticsPanel.minSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "TA9M").getUTCDateTimeString()), 1, 5);
                    }
                    
                    if (statisticsPanel.avgSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getStid()), 1, 0);
                        tablePanel.getTableModel().setTableModel("TA9M", 1, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 1, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getValue()), 1, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getNumberOfReportingStations()), 1, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "TA9M").getUTCDateTimeString()), 1, 5);
                    } 
                }
                
                if (paramPanel.SRADSelected(e))
                {
                    if (statisticsPanel.maxSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("MAXIMUM", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MAXIMUM, "SRAD").getUTCDateTimeString()), 2, 5);
                    }
                    
                    if (statisticsPanel.minSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("MINIMUM", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.MINIMUM, "SRAD").getUTCDateTimeString()), 2, 5);
                    }
                    
                    if (statisticsPanel.avgSelected())
                    {
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getStid()), 2, 0);
                        tablePanel.getTableModel().setTableModel("SRAD", 2, 1);
                        tablePanel.getTableModel().setTableModel("AVERAGE", 2, 2);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getValue()), 2, 3);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getNumberOfReportingStations()), 2, 4);
                        tablePanel.getTableModel().setTableModel((dataInfo.getStatistics(StatsType.AVERAGE, "SRAD").getUTCDateTimeString()), 2, 5);
                    } 
                }
            }
            else if (e.getSource() == exit)
            {
                System.exit(0);
            }             
        }       
    }
    
    class TopBarFileMenu extends JMenuBar implements ActionListener
    {
        private JMenu file;
        private JMenuItem open;
        private JMenuItem exit;   
        private JFileChooser fileChooser;
        private int option;
        private File fileOpener;
        
        public TopBarFileMenu()
        {
            file = new JMenu("File");
            open = new JMenuItem("Open Data File");
            exit = new JMenuItem("Exit");
            
            open.addActionListener(this);
            exit.addActionListener(this);
            
            add(file);
            file.add(open);
            file.add(exit);      
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Owner\\eclipse-workspace\\project4\\data"));
            
            String fileName = "";
            
            if (e.getSource() == open)
            {
                option = fileChooser.showOpenDialog(null);
                
                if (option == JFileChooser.APPROVE_OPTION)
                {
                    fileOpener = fileChooser.getSelectedFile();
                    dataInfo = new MapData(fileInfo.splitDate(fileOpener.getName())[0], (fileInfo.splitDate(fileOpener.getName())[1] - 1),
                            fileInfo.splitDate(fileOpener.getName())[2], fileInfo.splitDate(fileOpener.getName())[3],
                            fileInfo.splitDate(fileOpener.getName())[4], "data/");
                    
                    fileName = "data/".concat(fileOpener.getName());                   
                    dataInfo.setFileName(fileName);
                    
                    try
                    {
                        dataInfo.parseFile(fileName);
                    }
                    catch(Exception e2)
                    {
                        e2.printStackTrace();
                        System.out.println("Error reading from main file!\n");
                    }
                }
            }
            else if (e.getSource() == exit)
            {
                System.exit(0);
            }
        }
    }
}
