#!/bin/bash

# https://www.digitalocean.com/community/tutorials/how-to-set-up-a-firewall-with-ufw-on-ubuntu-20-04
ufw default deny incoming
ufw default allow outgoing
echo "${BUILD_USER}" | sudo -S ufw allow ssh
echo "${BUILD_USER}" | sudo -S ufw enable