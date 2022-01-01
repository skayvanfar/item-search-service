#!/bin/sh
getPort() {
  echo $1 | cut -d : -f 3 | xargs basename
}

echo "********************************************************"
echo "Starting Item Search Server"
echo "********************************************************"
java -Dspring.profiles.active=$PROFILE -jar /usr/local/itemsearchservice/@project.build.finalName@.jar
