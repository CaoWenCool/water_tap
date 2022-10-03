cd /home/java/water_tap/watertap
git pull
mvn clean package  -Dmaven.test.skip=true
cd /home/java/water_tap/watertap/deploy
sh start.sh