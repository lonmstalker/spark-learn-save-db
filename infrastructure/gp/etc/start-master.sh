#!/bin/sh

echo "source /usr/local/greenplum-db/greenplum_path.sh" >> .bashrc

ssh-copy-id -i "$PWD"/.ssh/id_rsa.pub greenplum-primary1
ssh greenplum-primary1

echo "current greenplum nodes:"
cat hostfile_exkeys

gpssh-exkeys -f hostfile_exkeys

gpssh -f hostfile_exkeys -e "ls -l /usr/local/greenplum-db-$GREENPLUM_VERSION"

. "/usr/local/greenplum-db/greenplum_path.sh"

gpinitsystem -y -c ~/gpinitsystem_config greenplum-primary1

sleep infinity