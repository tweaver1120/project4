package GUI;
import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    private ParameterPanel paramPanel;
    private StatisticsPanel statisticsPanel;
    
    public MesonetFrame()
    {
        super("Mesonet");
        setLayout(new BorderLayout());
        
        paramPanel = new ParameterPanel();
        statisticsPanel = new StatisticsPanel();
        
        add(paramPanel, BorderLayout.WEST);
        add(statisticsPanel, BorderLayout.EAST);
        
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
}
