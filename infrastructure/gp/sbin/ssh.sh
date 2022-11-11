#!/bin/bash
echo "${BUILD_USER}" | sudo -S service ssh start

mkdir -p "$HOME"/.ssh &&
  ssh-keygen -t rsa -b 4096 -P "" -f "$HOME"/.ssh/id_rsa

sleep infinity