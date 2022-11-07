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

WORKDIR /root

RUN wget https://github.com/greenplum-db/gpdb/releases/download/${GREENPLUM_VERSION}/greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && apt install -y ./greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && rm -f greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb

RUN mkdir -p "$PWD"/.ssh \
    && ssh-keygen -t rsa -b 4096 -N '123456' -f "$PWD"/.ssh/id_rsa \
    && mkdir "$PWD"/.ssh/authorized_keys \
    && cp "$PWD"/.ssh/id_rsa.pub "$PWD"/.ssh/authorized_keys/id_rsa.pub \
    && echo "    IdentityFile ${PWD}/.ssh/id_rsa" >> /etc/ssh/ssh_config \
    && chmod 700 "$PWD"/.ssh/id_rsa \
    && chmod 600 "$PWD"/.ssh/authorized_keys

COPY /etc/hostfile_gpssh_segonly /root/hostfile_gpssh_segonly
COPY /etc/hostfile_exkeys /root/hostfile_exkeys
COPY /etc/gpinitsystem_config /root/gpinitsystem_config

COPY /sbin/start-master.sh /root/start-master.sh
COPY /sbin/start-primary.sh /root/start-primary.sh
COPY /sbin/start-primary.sh /root/default-ufw.sh

COPY /etc/sshd_config /etc/ssh/sshd_config

RUN chmod 777 /usr/local/greenplum-db-${GREENPLUM_VERSION}/greenplum_path.sh