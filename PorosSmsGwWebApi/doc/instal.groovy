import groovy.sql.Sql


/**** Create Database  ****/

db = new java.util.Properties();
db.load( new FileInputStream('datasource.properties') );

def sql = Sql.newInstance(db.getProperty("url"), db.getProperty("username"),
                      db.getProperty("password"), db.getProperty("driverClassName"))


   sql.execute(db.getProperty("sql"))



/**** Copy War File to Tomcat For Installation***/



/**** Start Tomcat Process ***/

/**** Stop Tomcat Process ***/

/**** Clear Logs ***/


/**** Test Restfull Web Api ***/

/**** Test Web Service Web Api ***/