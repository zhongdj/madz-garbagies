#!/bin/bash

ant -f install.xml pre-resource-provisioning

echo "starting glassfish server ..."
. start-glassfish.sh
echo "glassfish server started"

ant -f install.xml configure-server

. restart-glassfish.sh

ant deploy import-data
