package GUI;
import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MesonetFrame extends JFrame
{
    public MesonetFrame()
    {
        super("Mesonet");
        setLayout(new BorderLayout());        
        
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
}
