#!/bin/bash

# shellcheck source=/usr/local/greenplum-db-6.21.3/greenplum_path.sh
. /usr/local/greenplum-db-"$GREENPLUM_VERSION"/greenplum_path.sh

mkdir .ssh \
  && cd .ssh \
  && touch known_hosts \
  && echo 'mdw' > known_hosts

sleep infinity