package GUI;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TablePanel extends JPanel
{
    private TableModel tableModel = new TableModel();
    private JTable table = new JTable(tableModel);   
    
    public TablePanel()
    {
        super(new BorderLayout());
        add(new JScrollPane(table));
    }
    
    public static class TableModel extends AbstractTableModel
    {
        private String[] columnID = {"Station", "Parameter", "Statistics", "Value", "Reporting Stations", "Date"};
        
        private Object[][] cell = 
        {
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},   
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
           {"", "", "", "", "", ""},
        };
        
        public String getColumnName(int column) 
        {
            return columnID[column];
        }
        
        public Object getValueAt(int row, int column) 
        {
            return cell[row][column];
        }
        
        public int getColumnCount() 
        {
            return columnID.length;
        }

        public int getRowCount() 
        {
            return cell.length;
        }           
        
        public void setTableModel(Object value, int row, int col) 
        {           
            cell[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
    
    public TableModel getTableModel()
    {
        return tableModel;
    }    
}
