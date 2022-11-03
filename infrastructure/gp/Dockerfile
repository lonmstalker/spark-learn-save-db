FROM centos:latest as builder
LABEL maintainer="nkochnev@zuzex.com"

ARG GREENPLUM_VERSION="6.21.3"
ARG GREENPLUM_OS="rhel7-x86_64"
ENV MASTER_DATA_DIRECTORY "/home/greenplum/gpdata"

RUN cd /etc/yum.repos.d/  \
    && sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-* \
    && sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

RUN yum -y update  \
    && yum -y upgrade  \
    && yum -y install wget \
    && yum install -y krb5-devel \
    && yum install -y libyaml \
    && yum install -y apr-util \
    && yum install -y bzip2 \
    && yum install -y net-tools \
    && yum install -y openssh \
    && yum install -y openssh-clients \
    && yum install -y openssh-server \
    && yum install -y perl \
    && yum install -y rsync \
    && yum install -y zip

WORKDIR /home/greenplum

RUN wget https://github.com/greenplum-db/gpdb/releases/download/$GREENPLUM_VERSION/open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm \
    && rpm -ivh open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm \
    && rm -f open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm

#ENTRYPOINT ["/bin/sh", "-c", "/usr/local/greenplum-db/greenplum_path.sh"]