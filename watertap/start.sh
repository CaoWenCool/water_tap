cd /home/java/water_tap/watertap
git pull
mvn clean package  -Dmaven.test.skip=true
cd /home/java/water_tap/watertap/deploy
sh start.sh

python3 /home/java/water_tap/watertap/src/main/resources/python/transfer.py