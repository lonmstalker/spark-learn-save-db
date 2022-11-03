````
1. Поднять hadoop, yarn, spark
2. Залить в hdfs csv
3. Поменять ключи в InputUtils
````

````
Для работы с котлином можем использовать https://github.com/Kotlin/kotlin-spark-api
````

--add-exports java.base/sun.nio.ch=ALL-UNNAMED for java 11

*GREENPLUM*
````
1. master. ssh-keygen -t rsa -b 4096 
2. master. ssh-copy-id [name of node]
3. master. ssh [name of node]
4. master gpssh-exkeys -f hostfile_exkeys
4. master. gpssh -f hostfile_exkeys -e ‘ls -l /usr/local/greenplum-db-6.21.3’
````