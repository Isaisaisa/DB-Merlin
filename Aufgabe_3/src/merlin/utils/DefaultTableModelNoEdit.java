package merlin.utils;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DefaultTableModelNoEdit extends DefaultTableModel implements TableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 363580136344802873L;

	public DefaultTableModelNoEdit() {
		// TODO Auto-generated constructor stub
	}

	public DefaultTableModelNoEdit(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DefaultTableModelNoEdit(Vector arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DefaultTableModelNoEdit(Object[] arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DefaultTableModelNoEdit(Vector arg0, Vector arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DefaultTableModelNoEdit(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }

}
