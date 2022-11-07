#!/bin/bash

mv "$PWD"/postgresql.conf "$PWD"/data/master/postgresql.conf

mkdir -p "$PWD"/data/master/global/pg_control

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

echo "current greenplum nodes:" \
  && cat "$PWD"/hostfile_exkeys \

gpssh-exkeys -f "$PWD"/hostfile_exkeys

gpssh -f "$PWD"/hostfile_exkeys -e "ls -l /usr/local/greenplum-db-$GREENPLUM_VERSION"

gpinitsystem -c "$PWD"/gpinitsystem_config -h "$PWD"/hostfile_gpssh_segonly -P 88 -p "$PWD"/data/master/postgresql.conf

sleep infinity