package edu.calpoly.csc365.examples.dao1.dao;

import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class CustomerResultSetDaoImpl implements ResultSetDao<Customer> {
  private Connection conn;

  public CustomerResultSetDaoImpl(Connection conn) {
    this.conn = conn;
    try {
      this.conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Customer getById(int id) {
    return null;
  }

  public Set getAll() {
    return null;
  }

  public Boolean insert(Customer obj) {
    return null;
  }

  public Boolean update(Customer obj) {
    Boolean successful = false;
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = this.conn.prepareStatement(
        "UPDATE Customer SET ssn=?, name=?, address=?, phone=? WHERE id=?");
      preparedStatement.setInt(1, obj.getSsn());
      preparedStatement.setString(2, obj.getName());
      preparedStatement.setString(3, obj.getAddress());
      preparedStatement.setString(4, obj.getPhone());
      preparedStatement.setInt(5, obj.getId());
      successful = preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return successful;
  }

  public Boolean delete(Customer obj) {
    return null;
  }

  @Override
  public ResultSet getResultSet() {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    RowSetFactory aFactory = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Customer");
      resultSet = preparedStatement.executeQuery();
      return resultSet;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
