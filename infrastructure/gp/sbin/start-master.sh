#!/bin/bash

# shellcheck source=home/gpadmin/sbin/ssh.sh
. "$HOME"/sbin/ssh.sh

mkdir -p "$HOME"/data/master/global/pg_control

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

echo "current greenplum nodes:" \
  && cat "$HOME"/hostfile_exkeys \
  && echo

gpssh -f "$HOME"/hostfile_exkeys -e "ls -l /usr/local/greenplum-db-$GREENPLUM_VERSION"

gpinitsystem -c "$HOME"/gpinitsystem_config -h "$HOME"/hostfile_gpssh_segonly -P 88 -p "$HOME"/data/master/postgresql.conf

sleep infinity