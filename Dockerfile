FROM amazoncorretto:8

ENV JAVA_NMT_OPTS="-Xint -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary"
ENV JAVA_OPTS=""

WORKDIR app
COPY target/classes .

ENTRYPOINT exec java $JAVA_OPTS $JAVA_NMT_OPTS com.berksoftware.article.jvmstack.MemoryFiller $0 $@
