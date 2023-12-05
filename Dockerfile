ARG java_version
FROM amazoncorretto:${java_version}

ENV JAVA_NMT_OPTS="-Xint -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary"
ENV JAVA_OPTS=""

WORKDIR app
COPY target/classes .

ENTRYPOINT exec java $JAVA_OPTS $JAVA_NMT_OPTS com.berksoftware.article.jvmstack.MemoryFiller $0 $@
