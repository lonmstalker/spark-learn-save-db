FROM centos:latest as builder
LABEL maintainer="nkochnev@zuzex.com"

ENV GREENPLUM_VERSION="6.21.3"
ARG GREENPLUM_OS="rhel7-x86_64"

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

COPY /etc/hostfile_gpssh_segonly /home/greenplum/hostfile_gpssh_segonly
COPY /etc/hostfile_exkeys /home/greenplum/hostfile_exkeys
COPY /etc/gpinitsystem_config /home/greenplum/gpinitsystem_config
COPY /etc/start-master.sh /home/greenplum/start-master.sh

RUN wget https://github.com/greenplum-db/gpdb/releases/download/$GREENPLUM_VERSION/open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm \
    && rpm -ivh open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm \
    && rm -f open-source-greenplum-db-${GREENPLUM_VERSION}-$GREENPLUM_OS.rpm