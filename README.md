
JPMorgan - Super Simple Stock Market


COMPILE

    mvn clean compile assembly:single


GENERATE TEST FILE

    param1: Mode of generate test file
    param2: Number of trades to generate
    param3: (optional) Mode of generate dates in trades. The dates are in the test file with this param.


    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar generate 50000 > input.txt 
    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar generate 50000 auto > input.txt 


RUN APP

    param1: (optional) Mode of input dates in trades. The dates are automatically generated with this param, don't provide the date in trades.

    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar < input.txt 
    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar auto < input.txt 
