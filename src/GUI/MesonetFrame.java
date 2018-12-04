package GUI;
import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    private ParameterPanel paramPanel;
    
    public MesonetFrame()
    {
        super("Mesonet");
        setLayout(new BorderLayout());
        
        paramPanel = new ParameterPanel();
        
        add(paramPanel, BorderLayout.WEST);
        
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
}
