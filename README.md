#sqlbridge

Large

Offload DB Techniques
Validation
Batch
Optimize Dialects
Cache

Fantastic 4

SQL
Preventive - Wrong Queries
Functions (String)

- Build Time
- Startup
- Runtime
- JDBC

Clean Code
= Opensource / Checkstyles/ Documented

# reference
1. https://dev.mysql.com/doc/index-other.html
2. https://blog.timescale.com/blog/why-sql-beating-nosql-what-this-means-for-future-of-data-time-series-database-348b777b847a/
3. https://blogs.oracle.com/oraclemagazine/deliver-oracle-database-18c-express-edition-in-containers
4. https://www.tutorialspoint.com/jdbc/jdbc-data-types.htm
5. https://www.progress.com/blogs/jdbc-tutorial-extracting-database-metadata-via-jdbc-driver
6. http://techdoc.kvindesland.no/java/docs/guide/jdbc/getstart/mapping.doc.html
7. https://www.progress.com/tutorials/jdbc/designing-performance
8. https://jdbc.postgresql.org/documentation/81/geometric.html
9. https://jdbc.postgresql.org/documentation/head/escapes.html#escape-use-example
10. https://github.com/bwajtr/java-persistence-frameworks-comparison
    
From Directory sqlcomponents

On JDK 8 it will not work
Min JDK needed is : 11
Max JDK Tested is : 18 

docker-compose up -d [launch in docker postgres on port  5432]
docker-compose down --volumes  [optional only when sql error is seen to recreate the default tables]
mvn clean package  
mvn clean package -Dmaven.surefire.debug  -Dmaven.failsafe.debug verify  
mvn clean package -Dmaven.test.skip=true  
mvn versions:use-latest-releases  
mvn help:active-profiles