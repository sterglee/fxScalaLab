java -server -d64     -XX:+UseNUMA -XX:+UseParallelGC -XX:+UseCompressedOops -XX:+AggressiveOpts -Djava.library.path=.:./lib:./libCUDA -Xss5m -Xms4000m -Xmx23500m -jar fxScalaLab.jar   &
