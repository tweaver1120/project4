package GUI;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    private ParameterPanel paramPanel;
    private StatisticsPanel statisticsPanel;
    private TablePanel tablePanel;
    private BottomPanel bottomPanel;
    
    public MesonetFrame()
    {
        super("Mesonet");
        setLayout(new BorderLayout());
        
        paramPanel = new ParameterPanel();
        statisticsPanel = new StatisticsPanel();
        tablePanel = new TablePanel();
        bottomPanel = new BottomPanel();
        
        add(paramPanel, BorderLayout.WEST);
        add(statisticsPanel, BorderLayout.EAST);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setSize(1000, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
    
    class BottomPanel extends JPanel
    {
        private JButton calculate;
        private JButton exit;
        
        public BottomPanel()
        {
            calculate = new JButton("Calculate");
            exit = new JButton("Exit");
            
            add(calculate);
            add(exit);
        }
    }
}
