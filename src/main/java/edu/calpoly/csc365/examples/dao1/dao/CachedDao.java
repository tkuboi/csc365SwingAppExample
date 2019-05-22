package edu.calpoly.csc365.examples.dao1.dao;

import javax.sql.rowset.CachedRowSet;

public interface CachedDao<T> extends Dao {
  CachedRowSet getCachedRowSet();
}
