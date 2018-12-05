package GUI;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ParameterPanel extends JPanel
{
    private JCheckBox TAIRBox = new JCheckBox("TAIR");
    private JCheckBox TA9MBox = new JCheckBox("TA9M");
    private JCheckBox SRADBox = new JCheckBox("SRAD");
    private JCheckBox WSPDBox = new JCheckBox("WSPD");
    private JCheckBox PRESBox = new JCheckBox("PRES");
    
    public ParameterPanel()
    {
        TitledBorder paramBorder = new TitledBorder("Parameter");
        GridLayout paramLayout = new GridLayout(0,2);
        
        setBorder(paramBorder);
        setLayout(paramLayout);
        
        add(TAIRBox);
        add(TA9MBox);
        add(SRADBox);
        add(WSPDBox);
        add(PRESBox);  
    }   
    
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
