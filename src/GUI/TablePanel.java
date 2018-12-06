package GUI;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Table Panel class displays the table and 
 * creates the border layout
 * 
 * @author Tim Weaver
 * @version 2018-12-05
 * 
 */
@SuppressWarnings("serial")
public class TablePanel extends JPanel
{
    /**
     * Table Model named tableModel
     */
    private TableModel tableModel = new TableModel();
    /**
     * JTable named table; references Table Model
     */
    private JTable table = new JTable(tableModel);   
    
    /**
     * Table Panel constructor adds border layout and table
     */
    public TablePanel()
    {
        //adds border layout
        super(new BorderLayout());
        //adds scroll pane
        add(new JScrollPane(table));       
    }
    
    /**
     * Table Model class sets up the columns 
     * and rows and holds the column id's
     * 
     * @author Tim Weaver
     * @version 2018-12-05
     * 
     */
    public static class TableModel extends AbstractTableModel
    {
        /**
         * String array that holds column id's
         */
        private String[] columnID = {"Station", "Parameter", "Statistics", "Value", "Reporting Stations", "Date"};
        /**
         * Object that holds the tables cells
         */
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
        
        /**
         * returns column id
         * @param column
         * @return columnID 
         */
        public String getColumnName(int column) 
        {
            return columnID[column];
        }
        
        /**
         * returns cell with row and columns
         * @param column
         * @param row
         * @return cell 
         */
        public Object getValueAt(int row, int column) 
        {
            return cell[row][column];
        }
        
        /**
         * returns column count
         * @return columnID Length of column id 
         */
        public int getColumnCount() 
        {
            return columnID.length;
        }

        /**
         * returns row count
         * @return cell Length of cell
         */
        public int getRowCount() 
        {
            return cell.length;
        }           
        
        /**
         * Sets the table model
         * @param value 
         * @param row
         * @param col
         */
        public void setTableModel(Object value, int row, int col) 
        {           
            cell[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
    
    /**
     * returns the table model
     * @return tableModel
     */
    public TableModel getTableModel()
    {
        return tableModel;
    }    
}
