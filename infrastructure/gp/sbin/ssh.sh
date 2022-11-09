#!/bin/bash
echo "${BUILD_USER}" | sudo -S service ssh start

mkdir -p "$HOME"/.ssh &&
  mkdir "$HOME"/.ssh/authorized_keys &&
  ssh-keygen -t rsa -b 4096 -P "" -f "$HOME"/.ssh/id_rsa &&
  ssh-keygen -R sdw1

sleep infinity