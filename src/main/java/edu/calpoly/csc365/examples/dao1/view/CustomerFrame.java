package edu.calpoly.csc365.examples.dao1.view;

import edu.calpoly.csc365.examples.dao1.dao.CachedDao;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class CustomerFrame extends JFrame implements RowSetListener {
  DaoManager daoManager;

  CustomerTableModel customerTableModel;

  JTable table; // The table for displaying data

  JLabel labelName;
  JLabel labelSsn;
  JLabel labelAddress;
  JLabel labelPhone;

  JTextField textFieldName;
  JTextField textFieldSsn;
  JTextField textFieldAddress;
  JTextField textFieldPhone;

  JButton buttonAdd;
  JButton buttonUpdate;
  JButton buttonDelete;
  JButton buttonCancel;

  public CustomerFrame(DaoManager dm) throws SQLException {
    super("Customer Table Form");

    daoManager = dm;

    // Close connections exit the application when the user
    // closes the window
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {

        try {
          daoManager.close();
        } catch (SQLException sqle) {
          sqle.printStackTrace();
        }
        System.exit(0);
      }
    });

    CachedRowSet cachedRowSet = getContentsOfCustomerTable();
    //CachedDao<Customer> customerCachedDao = getCustomerDao();
    customerTableModel = new CustomerTableModel(cachedRowSet);
    customerTableModel.addEventHandlersToRowSet(this);
    customerTableModel.addTableModelListener(this.table);

    table = new JTable(); // Displays the table
    table.setModel(customerTableModel);

    labelName = new JLabel();
    labelSsn = new JLabel();
    labelAddress = new JLabel();
    labelPhone = new JLabel();

    textFieldName = new JTextField(10);
    textFieldSsn = new JTextField(10);
    textFieldAddress = new JTextField(10);
    textFieldPhone = new JTextField(10);

    buttonAdd = new JButton();
    buttonUpdate = new JButton();
    buttonDelete = new JButton();
    buttonCancel = new JButton();

    labelName.setText("Name:");
    labelSsn.setText("SSN:");
    labelAddress.setText("Address:");
    labelPhone.setText("Phone:");

    buttonAdd.setText("Add");
    buttonUpdate.setText("Update");
    buttonDelete.setText("Delete");
    buttonCancel.setText("Cancel");

    Container contentPane = getContentPane();
    contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    contentPane.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.CENTER;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    contentPane.add(new JScrollPane(table), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    contentPane.add(labelName, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 1;
    contentPane.add(textFieldName, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    contentPane.add(labelSsn, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 1;
    contentPane.add(textFieldSsn, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 1;
    contentPane.add(labelAddress, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 4;
    c.gridwidth = 1;
    contentPane.add(textFieldAddress, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 1;
    contentPane.add(labelPhone, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 5;
    c.gridwidth = 1;
    contentPane.add(textFieldPhone, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    contentPane.add(buttonAdd, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 1;
    contentPane.add(buttonUpdate, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    contentPane.add(buttonDelete, c);

    buttonAdd.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        JOptionPane.showMessageDialog(CustomerFrame.this,
          new String[] {
            "Adding the following row:",
            "name: [" + textFieldName.getText() + "]",
            "ssn: [" + textFieldSsn.getText() + "]",
            "address: [" + textFieldAddress.getText() + "]",
            "phone: [" + textFieldPhone.getText() + "]"});


        try {
          customerTableModel.insertRow(textFieldName.getText(),
            Integer.parseInt(textFieldSsn.getText().trim()),
            textFieldAddress.getText().trim(),
            textFieldPhone.getText().trim());
          customerTableModel.customerRowSet.acceptChanges(
            daoManager.getConnection());
        } catch (SQLException sqle) {
          System.out.println(sqle.getMessage());
          sqle.printStackTrace();
        }
      }
    });

    buttonUpdate.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          customerTableModel.customerRowSet.acceptChanges(
            daoManager.getConnection());
        } catch (SQLException sqle) {
          sqle.printStackTrace();
          // Now revert back changes
          try {
            createNewTableModel();
          } catch (SQLException sqle2) {
            sqle2.printStackTrace();
          }
        }
      }
    });

    buttonDelete.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          customerTableModel.deleteRow(table.getSelectedRow());
          customerTableModel.customerRowSet.acceptChanges(
            daoManager.getConnection());
        } catch (SQLException sqle) {
          sqle.printStackTrace();
          // Now revert back changes
          try {
            createNewTableModel();
          } catch (SQLException sqle2) {
            sqle2.printStackTrace();
          }
        }
      }
    });
  }

  private CachedDao<Customer> getCustomerDao() {
    CachedDao<Customer> customerDao = this.daoManager.getCustomerCachedDao();
    return customerDao;
  }

  private CachedRowSet getContentsOfCustomerTable() {
    CachedDao<Customer> customerDao = this.daoManager.getCustomerCachedDao();
    return customerDao.getCachedRowSet();
  }

  private void createNewTableModel() throws SQLException {
    customerTableModel = new CustomerTableModel(getContentsOfCustomerTable());
    customerTableModel.addEventHandlersToRowSet(this);
    table.setModel(customerTableModel);
  }

  public static void main(String[] args) {
    CustomerFrame frame = null;
    try {
      frame = new CustomerFrame(
        DaoManager.getInstance().setProperties(args[0]));
      frame.pack();
      frame.setVisible(true);
    } catch (SQLException e) {
      System.out.println("Problem connecting to the database.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Problem reading the properties file.");
      e.printStackTrace();
    }
  }

  /**
   * Notifies registered listeners that a <code>RowSet</code> object
   * in the given <code>RowSetEvent</code> object has changed its entire contents.
   * <p>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * @param event a <code>RowSetEvent</code> object that contains
   *              the <code>RowSet</code> object that is the source of the event
   */
  public void rowSetChanged(RowSetEvent event) {
    System.out.println("rowset changed");
    CachedRowSet currentRowSet = this.customerTableModel.customerRowSet;

    try {
      currentRowSet.moveToCurrentRow();
      createNewTableModel();

    } catch (SQLException ex) {

      ex.printStackTrace();
      // Display the error in a dialog box.

      JOptionPane.showMessageDialog(
        CustomerFrame.this,
        new String[]{ // Display a 2-line message
          ex.getClass().getName() + ": ",
          ex.getMessage()
        }
      );
    }
  }

  /**
   * Notifies registered listeners that a <code>RowSet</code> object
   * has had a change in one of its rows.
   * <p>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * @param event a <code>RowSetEvent</code> object that contains
   *              the <code>RowSet</code> object that is the source of the event
   */
  public void rowChanged(RowSetEvent event) {
    System.out.println("row changed");
  }

  /**
   * Notifies registered listeners that a <code>RowSet</code> object's
   * cursor has moved.
   * <p>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * @param event a <code>RowSetEvent</code> object that contains
   *              the <code>RowSet</code> object that is the source of the event
   */
  public void cursorMoved(RowSetEvent event) {
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    this.daoManager.close();
    System.out.println("closed");
  }
}
