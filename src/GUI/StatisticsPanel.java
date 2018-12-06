package GUI;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/**
 * Statistics Panel creates three radio buttons for
 * MAX, MIN, and AVG. These are used to determine which statistics
 * to display to the table
 * 
 * @author Tim Weaver
 * @version 2018-12-05
 * 
 */
@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel
{
    /**
     * Radio button for MAX 
     */
    private JRadioButton maxButton = new JRadioButton("MAXIMUM");
    /**
     * Radio button for MIN 
     */
    private JRadioButton minButton = new JRadioButton("MINIMUM");
    /**
     * Radio button for AVG 
     */
    private JRadioButton avgButton = new JRadioButton("AVERAGE");
    /**
     * Button Group for all stats 
     */
    private ButtonGroup statsGroup;
    
    /**
     * Statistics Panel constructor adds all buttons to the panel
     */
    public StatisticsPanel()
    {
        //create new border
        TitledBorder statsBorder = new TitledBorder("Statistics");
        //create grid layout
        GridLayout statsLayout = new GridLayout(0,1);
        
        //set border and layout
        setBorder(statsBorder);
        setLayout(statsLayout);
        
        //add buttons to panel
        add(maxButton);
        add(minButton);
        add(avgButton);
        
        //add buttons to group so not more than one button can be selected at a time
        statsGroup = new ButtonGroup();
        statsGroup.add(maxButton);
        statsGroup.add(minButton);
        statsGroup.add(avgButton);      
    }
    
    /**
     * Returns true if MAX is selected 
     * @return maxButton.isSelected()
     */
    public boolean maxSelected()
    {
        return maxButton.isSelected();
    }
    /**
     * Returns true if MIN is selected 
     * @return minButton.isSelected()
     */
    public boolean minSelected()
    {
        return minButton.isSelected();
    }
    /**
     * Returns true if AVG is selected 
     * @return avgButton.isSelected()
     */
    public boolean avgSelected()
    {
        return avgButton.isSelected();
    }
}
