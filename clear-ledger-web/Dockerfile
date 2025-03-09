FROM caddy:2.9-alpine

ARG APP_VERSION=''
RUN echo "Building App Version ${APP_VERSION}"

COPY ./dist/ /usr/share/caddy
COPY ./conf/Caddyfile /etc/caddy/Caddyfile