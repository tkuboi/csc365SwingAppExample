package edu.calpoly.csc365.examples.dao1.dao;

public interface DaoCommand {
  Object execute(DaoManager daoManager);
}
