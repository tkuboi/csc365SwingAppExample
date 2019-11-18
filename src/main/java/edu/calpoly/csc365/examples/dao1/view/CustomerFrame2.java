package edu.calpoly.csc365.examples.dao1.view;

import edu.calpoly.csc365.examples.dao1.dao.CachedDao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.dao.ResultSetDao;
import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFrame2 extends JFrame implements TableModelListener {
  DaoManager daoManager;

  CustomerTableModel2 customerTableModel;

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

  public CustomerFrame2(DaoManager dm) throws SQLException {
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

    //ResultSet resultSet = getContentsOfCustomerTable();
    //CachedDao<Customer> customerCachedDao = getCustomerDao();
    customerTableModel = new CustomerTableModel2(this.daoManager.getCustomerResultSetDao());
    customerTableModel.addTableModelListener(this);

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

        JOptionPane.showMessageDialog(CustomerFrame2.this,
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
          //customerTableModel.customerRowSet.acceptChanges(
          //  daoManager.getConnection());
        } catch (SQLException sqle) {
          System.out.println(sqle.getMessage());
          sqle.printStackTrace();
        }
      }
    });

    buttonUpdate.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          customerTableModel.saveCurrentRow(table.getSelectedRow());
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
        customerTableModel.deleteRow(table.getSelectedRow());
      }
    });
  }

  private CachedDao<Customer> getCustomerDao() {
    CachedDao<Customer> customerDao = this.daoManager.getCustomerCachedDao();
    return customerDao;
  }

  private ResultSet getContentsOfCustomerTable() {
    ResultSetDao<Customer> customerDao = this.daoManager.getCustomerResultSetDao();
    return customerDao.getResultSet();
  }

  private void createNewTableModel() throws SQLException {
    customerTableModel = new CustomerTableModel2(this.daoManager.getCustomerResultSetDao());
    table.setModel(customerTableModel);
    customerTableModel.addTableModelListener(this);
  }

  public static void main(String[] args) {
    CustomerFrame2 frame = null;
    try {
      frame = new CustomerFrame2(
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

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    this.daoManager.close();
    System.out.println("closed");
  }

  /**
   * This fine grain notification tells listeners the exact range
   * of cells, rows, or columns that changed.
   *
   * @param e
   */
  @Override
  public void tableChanged(TableModelEvent e) {
    System.out.println("Table Model changed");
    ResultSet currentRowSet = this.customerTableModel.customerRowSet;

    try {
      //currentRowSet.moveToCurrentRow();
      createNewTableModel();

    } catch (SQLException ex) {

      ex.printStackTrace();
      // Display the error in a dialog box.

      JOptionPane.showMessageDialog(
        CustomerFrame2.this,
        new String[]{ // Display a 2-line message
          ex.getClass().getName() + ": ",
          ex.getMessage()
        }
      );
    }
  }
}
