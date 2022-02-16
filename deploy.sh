#! /bin/bash

kill -9 `pgrep -f java`

git fetch --all
git reset --hard origin/master

chmod +x ./gradlew && chmod +x ./deploy.sh

./gradlew build

nohup java -jar ./build/libs/weakness.jar &
