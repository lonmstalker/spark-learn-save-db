#!/bin/bash

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

touch known_hosts \
      && ssh-keyscan mdw > known_hosts

sleep infinity