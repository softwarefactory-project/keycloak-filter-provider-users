ARG KEYCLOAK_VERSION=21.0

FROM docker.io/maven:3.8.6-jdk-11 as mvn_builder
COPY . /tmp
RUN cd /tmp && mvn clean install

FROM quay.io/keycloak/keycloak:$KEYCLOAK_VERSION as builder

COPY --from=mvn_builder /tmp/target/*.jar /opt/keycloak/providers/

USER 1000

RUN /opt/keycloak/bin/kc.sh build --health-enabled=true

FROM quay.io/keycloak/keycloak:$KEYCLOAK_VERSION
COPY --from=builder /opt/keycloak/ /opt/keycloak/
WORKDIR /opt/keycloak


ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]