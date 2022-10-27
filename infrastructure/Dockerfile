FROM centos:latest
LABEL maintainer="nkochnev@zuzex.com"

ARG HADOOP_NAME="hadoop-3.2.1"

RUN cd /etc/yum.repos.d/  \
    && sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-* \
    && sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

RUN yum -y update  \
    && yum -y upgrade  \
    && yum -y install wget \
    && yum -y install java-1.8.0-openjdk  \
    && java -version

RUN groupadd heartbeat && useradd hadoop -G heartbeat

RUN wget https://archive.apache.org/dist/hadoop/common/$HADOOP_NAME/$HADOOP_NAME.tar.gz  \
    && tar -xvzf $HADOOP_NAME.tar.gz  \
    && mv $HADOOP_NAME hadoop \
    && rm $HADOOP_NAME.tar.gz

ENV JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk/
ENV HADOOP_HOME=/home/hadoop
ENV HADOOP_INSTALL=$HADOOP_HOME
ENV HADOOP_MAPRED_HOME=$HADOOP_HOME
ENV HADOOP_COMMON_HOME=$HADOOP_HOME
ENV HADOOP_HDFS_HOME=$HADOOP_HOME
ENV HADOOP_YARN_HOME=$HADOOP_HOME
ENV HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
ENV PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
ENV HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib/native"

RUN cd /home/hadoop && ls
RUN mkdir -p ~/hadoopdata/hdfs/namenode && mkdir -p ~/hadoopdata/hdfs/datanode
RUN chmod -R 777 /home/hadoop/libexec/hdfs-config.sh

ADD /hadoop/*.xml /$HADOOP_HOME/etc/hadoop/

EXPOSE 9000 9021 50070 2222 8966

ENTRYPOINT ["sh", "-c", "/hadoop/bin/hdfs namenode -format && /hadoop/sbin/start-dfs.sh"]