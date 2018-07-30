数据库问文档生成工具

Auto create mysql doc to markdown or html

工具使用：
使用参数说明

```
 --db VAL       : mysql-database
 --help (-h)    : show this message (default: false)
 --host VAL     : mysql-host
 --password VAL : mysql-password
 --user VAL     : mysql-user
```

直接下载生成好的jar 包运行以下命令：

```
java -jar mysqldoc-1.0-SNAPSHOT.jar --host localhost --user root --password root --db testdb
```


######  打包成jar 包 
```
 mvn package

```

生成数据库文档
```
java -jar target/mysqldoc-1.0-SNAPSHOT.jar --host localhost --user root --password root --db testdb
```


