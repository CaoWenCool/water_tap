cd /usr/local/work-place/water_tap/watertap
git pull
mvn clean package  -Dmaven.test.skip=true
cd /usr/local/work-place/water_tap/watertap/deploy
sh start.sh