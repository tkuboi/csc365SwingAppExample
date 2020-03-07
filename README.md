# csc365SwingAppExample

create properties.xml file with the following content (replace the xxx with appropriate information) and execute the program by specifying CustomerFrame, which has the main function.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>

<entry key="driver">com.mysql.cj.jdbc.Driver</entry>
<entry key="url">jdbc:mysql://xxx:3306/xxx</entry>
<entry key="user">xxx</entry>
<entry key="pass">xxx</entry>


</properties>
```

In your database create Customer table as follows:
```
CREATE TABLE Customer (
  id int NOT NULL AUTO_INCREMENT,
  ssn char(11) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  phone char(12) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ssn (ssn)
);
```
run edu.calpoly.csc365.examples.dao1.view.CustomerFrame with the filepath/properties.xml as an application argument.
For example,
:$ java edu.calpoly.csc365.examples.dao1.view.CustomerFrame csc365SwingAppExample/properties.xml
