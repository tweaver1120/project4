package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    private ParameterPanel paramPanel;
    private StatisticsPanel statisticsPanel;
    private TablePanel tablePanel;
    private BottomPanel bottomPanel;
    private TopBarFileMenu fileMenu;
    
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
            
            exit.addActionListener(this);
            
            add(calculate);
            add(exit);
        }
        
        public void actionPerformed(ActionEvent e)
        {
            
            if (e.getSource() == calculate)
            {
                //TODO calculate depending on which parameters are selected
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
            
            if (e.getSource() == open)
            {
                option = fileChooser.showOpenDialog(null);
                //TODO open data file
            }
            else if (e.getSource() == exit)
            {
                System.exit(0);
            }
        }
    }
}
