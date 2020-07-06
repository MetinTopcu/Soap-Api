#!/bin/sh

echo "The example application will start"
exec java ${JAVA_OPTS} -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.apinizer.example.ExampleServiceApplication" "$@"
