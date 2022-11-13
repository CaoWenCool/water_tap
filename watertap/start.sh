cd /home/work/watertap
git pull
mvn clean package  -Dmaven.test.skip=true
cd /home/work/watertap/deploy
sh start.sh