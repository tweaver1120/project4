package GUI;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;

/**
 * Parameter Panel class creates 5 check boxes that are used to 
 * filter different parts of the calculations in a data file
 * 
 * @author Tim Weaver
 * @version 2018-12-05
 * 
 */
@SuppressWarnings("serial")
public class ParameterPanel extends JPanel
{
    /**
     * Check box name TAIR
     */
    private JCheckBox TAIRBox = new JCheckBox("TAIR");
    /**
     * Check box name TA9M
     */
    private JCheckBox TA9MBox = new JCheckBox("TA9M");
    /**
     * Check box name SRAD
     */
    private JCheckBox SRADBox = new JCheckBox("SRAD");
    /**
     * Check box name WSPD
     */
    private JCheckBox WSPDBox = new JCheckBox("WSPD");
    /**
     * Check box name PRES
     */
    private JCheckBox PRESBox = new JCheckBox("PRES");
    
    /**
     * Parameter Panel constructor adds all check boxes to the panel
     */
    public ParameterPanel()
    {
        //creates new border and grid layout
        TitledBorder paramBorder = new TitledBorder("Parameter");
        GridLayout paramLayout = new GridLayout(0,2);
        
        //sets border and grid layout
        setBorder(paramBorder);
        setLayout(paramLayout);
        
        //adds all check boxed to the panel
        add(TAIRBox);
        add(TA9MBox);
        add(SRADBox);
        add(WSPDBox);
        add(PRESBox);         
    }   
    
    /**
     * Returns true if check box is selected, otherwise returns false
     * @param e
     * @return true If check box is selected 
     * @return false If check box is not selected
     */
    public boolean TAIRSelected(ActionEvent e) 
    {
        if (TAIRBox.isSelected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns true if check box is selected, otherwise returns false
     * @param e
     * @return true If check box is selected 
     * @return false If check box is not selected
     */
    public boolean TA9MSelected(ActionEvent e) 
    {
        if (TA9MBox.isSelected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns true if check box is selected, otherwise returns false
     * @param e
     * @return true If check box is selected 
     * @return false If check box is not selected
     */
    public boolean SRADSelected(ActionEvent e) 
    {
        if (SRADBox.isSelected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns true if check box is selected, otherwise returns false
     * @param e
     * @return true If check box is selected 
     * @return false If check box is not selected
     */
    public boolean WSPDSelected(ActionEvent e) 
    {
        if (WSPDBox.isSelected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns true if check box is selected, otherwise returns false
     * @param e
     * @return true If check box is selected 
     * @return false If check box is not selected
     */
    public boolean PRESSelected(ActionEvent e) 
    {
        if (PRESBox.isSelected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
