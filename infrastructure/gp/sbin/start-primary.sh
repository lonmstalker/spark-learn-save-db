#!/bin/bash

# just now so wait master
sleep 10

echo "${BUILD_USER}" | sudo -S service ssh start

# shellcheck source=/home/gpadmin/sbin/default-ufw.sh
#. "$HOME"/sbin/default-ufw.sh

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

touch known_hosts \
      && ssh-keyscan mdw > known_hosts

sleep infinity