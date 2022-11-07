FROM ecpe4s/ubuntu18.04-spack:latest
LABEL maintainer="nkochnev@zuzex.com"

ENV GREENPLUM_VERSION="6.21.3"
ARG GREENPLUM_OS="ubuntu18.04-amd64"

RUN apt -y update \
    && apt -y upgrade  \
    && apt install -y software-properties-common \
    && apt install -y python3 \
    && apt install -y ufw \
    && apt install -y openssh-server

WORKDIR /home/root

RUN wget https://github.com/greenplum-db/gpdb/releases/download/${GREENPLUM_VERSION}/greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && apt install -y ./greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && rm -f greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb

RUN mkdir -p "$PWD"/.ssh \
      && ssh-keygen -t rsa -b 4096 -N '123456' -f "$PWD"/.ssh/id_rsa \
      && chmod 700 "$PWD"/.ssh/id_rsa

COPY /etc/hostfile_gpssh_segonly /home/root/hostfile_gpssh_segonly
COPY /etc/hostfile_exkeys /home/root/hostfile_exkeys
COPY /etc/gpinitsystem_config /home/root/gpinitsystem_config

COPY /sbin/start-master.sh /home/root/start-master.sh
COPY /sbin/start-primary.sh /home/root/start-primary.sh
COPY /sbin/start-primary.sh /home/root/default-ufw.sh

COPY /etc/postgresql.conf /home/root/postgresql.conf
COPY /etc/sshd_config /etc/ssh/sshd_config

RUN chmod 777 /usr/local/greenplum-db-${GREENPLUM_VERSION}/greenplum_path.sh