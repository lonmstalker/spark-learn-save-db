FROM ecpe4s/ubuntu18.04-spack:latest
LABEL maintainer="nkochnev@zuzex.com"

ENV BUILD_USER="gpadmin"
ENV GREENPLUM_VERSION="6.21.3"
ARG GREENPLUM_OS="ubuntu18.04-amd64"

RUN apt -y update \
    && apt -y upgrade  \
    && apt install -y software-properties-common \
    && add-apt-repository ppa:deadsnakes/ppa \
    && apt -y update \
    && apt install -y python3.9 \
    && apt install -y ufw \
    && apt install -y openssh-server \
    && apt install -y iptables sudo \
    && apt install --reinstall linux-modules-5.4.0-42-generic

RUN wget https://github.com/greenplum-db/gpdb/releases/download/${GREENPLUM_VERSION}/greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && apt install -y ./greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb \
    && rm -f greenplum-db-${GREENPLUM_VERSION}-${GREENPLUM_OS}.deb

RUN useradd -m -N -s /bin/bash -u 1280 $BUILD_USER \
    && echo $BUILD_USER:$BUILD_USER | chpasswd \
    && usermod -aG sudo $BUILD_USER \
    && chmod 0440 /etc/sudoers \
    && chmod g+w /etc/passwd

USER $BUILD_USER
ENV LOGNAME=$BUILD_USER
ENV HOME=/home/$BUILD_USER
WORKDIR /home/$BUILD_USER

COPY /etc/hostfile_gpssh_segonly $HOME/etc/hostfile_gpssh_segonly
COPY /etc/hostfile_exkeys $HOME/etc/hostfile_exkeys
COPY /etc/gpinitsystem_config $HOME/etc/gpinitsystem_config

COPY /sbin $HOME/sbin

COPY /etc/sshd_config /etc/ssh/sshd_config

RUN echo $BUILD_USER | sudo -S chmod 777 /usr/local/greenplum-db-${GREENPLUM_VERSION}/greenplum_path.sh