package edu.calpoly.csc365.examples.dao1.dao;

import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

public interface ResultSetDao<T> extends Dao<Customer> {
  ResultSet getResultSet();
}
