#!/bin/bash

echo "${BUILD_USER}" | ssh-copy-id -i "$HOME"/.ssh/id_rsa -p 22 gpadmin@mdw
echo "${BUILD_USER}" | ssh-copy-id -i "$HOME"/.ssh/id_rsa -p 22 sdw1

echo "${BUILD_USER}" | sudo -S chmod 700 "$HOME"/.ssh &&
  echo "${BUILD_USER}" | sudo -S chmod 700 "$HOME"/.ssh/id_rsa &&
  echo "${BUILD_USER}" | sudo -S chmod 640 "$HOME"/.ssh/authorized_keys

mkdir -p "$HOME"/data/master/global/pg_control

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

echo "current greenplum nodes:" &&
  cat "$HOME"/etc/hostfile_exkeys &&
  echo

gpssh -f "$HOME"/etc/hostfile_exkeys -e "ls -l /usr/local/greenplum-db-$GREENPLUM_VERSION"

gpinitsystem -c "$HOME"/etc/gpinitsystem_config -h "$HOME"/etc/hostfile_gpssh_segonly -P 88 -p "$HOME"/data/master/postgresql.conf
