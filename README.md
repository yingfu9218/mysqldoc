Aotu create mysql doc to markdown or html

######  打包成jar 包 
```
 mvn package

```

生成数据库文档
```
java -jar target/mysqldoc-1.0-SNAPSHOT.jar --host localhost --user root --password root --db testdb
```

