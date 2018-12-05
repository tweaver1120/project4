package GUI;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel
{
    private JRadioButton maxButton = new JRadioButton("MAXIMUM");
    private JRadioButton minButton = new JRadioButton("MINIMUM");
    private JRadioButton avgButton = new JRadioButton("AVERAGE");
    
    private ButtonGroup bGroup;
    
    public StatisticsPanel()
    {
        TitledBorder statsBorder = new TitledBorder("Statistics");
        GridLayout statsLayout = new GridLayout(0,1);
        
        setBorder(statsBorder);
        setLayout(statsLayout);
        
        add(maxButton);
        add(minButton);
        add(avgButton);
        
        bGroup = new ButtonGroup();
        bGroup.add(maxButton);
        bGroup.add(minButton);
        bGroup.add(avgButton);      
    }
}
