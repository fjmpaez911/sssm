# sssm
JPMorgan - Super Simple Stock Market




COMPILE

    mvn clean compile assembly:single


GENERATE TEST FILE

    param1: Mode of generate test file
    param2: Number of trades to generate


    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar generate 50000 > input.txt 


RUN APP

    java -jar ./target/sssm-1.0-SNAPSHOT-jar-with-dependencies.jar < input.txt 