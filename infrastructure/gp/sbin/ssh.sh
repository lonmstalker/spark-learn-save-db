#!/bin/bash
echo "${BUILD_USER}" | sudo -S service ssh start

mkdir -p "$HOME"/.ssh \
    && mkdir "$HOME"/.ssh/authorized_keys \
    && ssh-keygen -t rsa -b 4096 -P "" -f "$HOME"/.ssh/id_rsa

printf "yes\nDocker!" | ssh-copy-id -i "$HOME"/.ssh/id_rsa -p 22 gpadmin@sdw1

echo "${BUILD_USER}" | sudo -S chmod 700 "$HOME"/.ssh \
  && echo "${BUILD_USER}" | sudo -S  chmod 700 "$HOME"/.ssh/id_rsa \
  && echo "${BUILD_USER}" | sudo -S  chmod 640 "$HOME"/.ssh/authorized_keys