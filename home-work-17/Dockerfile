FROM azul/zulu-openjdk-alpine:11.0.16-11.58.15

ENV SPRING_APP_PROFILE=prod

RUN apk --no-cache add curl
ADD ./target/application.jar /app/

CMD java $JAVA_OPTIONS -jar -Dspring.profiles.active=$SPRING_APP_PROFILE /app/application.jar

EXPOSE 8080