package edu.calpoly.csc365.examples.dao1.view;

import edu.calpoly.csc365.examples.dao1.dao.CustomerResultSetDaoImpl;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.ResultSetDao;
import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.event.TableModelEvent.*;

public class CustomerTableModel2 implements TableModel {
  ResultSetDao<Customer> customerResultSetDao;
  ResultSet customerRowSet; // The ResultSet to interpret
  ResultSetMetaData metadata; // Additional information about the results
  List<TableModelListener> listeners = new ArrayList<TableModelListener>();

  int numcols, numrows; // How many rows and columns in the table

  public CustomerTableModel2(ResultSetDao<Customer> customerDao) throws SQLException {
    this.customerResultSetDao = customerDao;
    this.customerRowSet = customerDao.getResultSet();
    this.metadata = this.customerRowSet.getMetaData();
    numcols = metadata.getColumnCount();

    // Retrieve the number of rows.
    this.customerRowSet.beforeFirst();
    this.numrows = 0;
    while (this.customerRowSet.next()) {
      this.numrows++;
    }
    this.customerRowSet.beforeFirst();
  }

  public ResultSet getCustomerRowSet() {
    return this.customerRowSet;
  }

  /**
   * Returns the number of rows in the model. A
   * <code>JTable</code> uses this method to determine how many rows it
   * should display.  This method should be quick, as it
   * is called frequently during rendering.
   *
   * @return the number of rows in the model
   * @see #getColumnCount
   */
  public int getRowCount() {
    return numrows;
  }

  /**
   * Returns the number of columns in the model. A
   * <code>JTable</code> uses this method to determine how many columns it
   * should create and display by default.
   *
   * @return the number of columns in the model
   * @see #getRowCount
   */
  public int getColumnCount() {
    return numcols;
  }

  /**
   * Returns the name of the column at <code>columnIndex</code>.  This is used
   * to initialize the table's column header name.  Note: this name does
   * not need to be unique; two columns in a table can have the same name.
   *
   * @param columnIndex the index of the column
   * @return the name of the column
   */
  public String getColumnName(int columnIndex) {
    try {
      return metadata.getColumnName(columnIndex + 1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns the most specific superclass for all the cell values
   * in the column.  This is used by the <code>JTable</code> to set up a
   * default renderer and editor for the column.
   *
   * @param columnIndex the index of the column
   * @return the common ancestor class of the object values in the model.
   */
  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  /**
   * Returns true if the cell at <code>rowIndex</code> and
   * <code>columnIndex</code>
   * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
   * change the value of that cell.
   *
   * @param rowIndex    the row whose value to be queried
   * @param columnIndex the column whose value to be queried
   * @return true if the cell is editable
   * @see #setValueAt
   */
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex == 0)
      return false;
    return true;
  }

  /**
   * Returns the value for the cell at <code>columnIndex</code> and
   * <code>rowIndex</code>.
   *
   * @param rowIndex    the row whose value is to be queried
   * @param columnIndex the column whose value is to be queried
   * @return the value Object at the specified cell
   */
  public Object getValueAt(int rowIndex, int columnIndex) {
    try {
      this.customerRowSet.absolute(rowIndex + 1);
      Object o = this.customerRowSet.getObject(columnIndex + 1);
      if (o == null)
        return null;
      else
        return o.toString();
    } catch (SQLException e) {
      return e.toString();
    }
  }

  /**
   * Sets the value in the cell at <code>columnIndex</code> and
   * <code>rowIndex</code> to <code>aValue</code>.
   *
   * @param aValue      the new value
   * @param rowIndex    the row whose value is to be changed
   * @param columnIndex the column whose value is to be changed
   * @see #getValueAt
   * @see #isCellEditable
   */
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    System.out.println("Calling setValueAt row " + rowIndex + ", column " + columnIndex);
    try {
      this.customerRowSet.absolute(rowIndex + 1);
      int id = this.customerRowSet.getInt("id");
      int ssn = this.customerRowSet.getInt("ssn");
      String name = this.customerRowSet.getString("name");
      String address = this.customerRowSet.getString("address");
      String phone = this.customerRowSet.getString("phone");
      Customer customer = new Customer(id, ssn, name, address, phone);
      this.customerResultSetDao.update(customer);
      this.notifyChanges(new TableModelEvent(this, rowIndex, ALL_COLUMNS, UPDATE));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteRow(int rowIndex) {
    System.out.println("Calling deleteRow row " + rowIndex);
    try {
      this.customerRowSet.absolute(rowIndex + 1);
      //this.customerRowSet.deleteRow();
      this.notifyChanges(new TableModelEvent(this, rowIndex, ALL_COLUMNS, DELETE));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a listener to the list that is notified each time a change
   * to the data model occurs.
   *
   * @param l the TableModelListener
   */
  public void addTableModelListener(TableModelListener l) {
    this.listeners.add(l);
  }

  /**
   * Removes a listener from the list that is notified each time a
   * change to the data model occurs.
   *
   * @param l the TableModelListener
   */
  public void removeTableModelListener(TableModelListener l) {
    this.listeners.remove(l);
  }

  public void insertRow(String name, int ssn, String address,
                        String phone) throws SQLException {

    try {
      /*this.customerRowSet.moveToInsertRow();
      this.customerRowSet.updateString("name", name);
      this.customerRowSet.updateNull("id");
      this.customerRowSet.updateInt("ssn", ssn);
      this.customerRowSet.updateString("address", address);
      this.customerRowSet.updateString("phone", phone);
      this.customerRowSet.insertRow();
      this.customerRowSet.moveToCurrentRow();*/
      Customer customer = new Customer(ssn, name, address, phone);
      this.customerResultSetDao.insert(customer);
      //this.notifyAll();
      this.notifyChanges(new TableModelEvent(this));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      //System.out.println(e.getSQLState());
      e.printStackTrace();
    }
  }

  public void saveCurrentRow(int rowIndex) throws SQLException {
    this.customerRowSet.absolute(rowIndex + 1);
    this.customerRowSet.updateRow();
  }

  protected void notifyChanges(TableModelEvent e) {
    for (TableModelListener listener : this.listeners) {
      listener.tableChanged(e);
    }
  }
  public void close() {
    try {
      customerRowSet.getStatement().close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      e.printStackTrace();
    }
  }

  /** Automatically close when we're garbage collected */
  protected void finalize() {
    close();
  }
}
