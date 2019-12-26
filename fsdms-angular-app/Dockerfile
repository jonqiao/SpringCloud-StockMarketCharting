FROM node as node-builder
MAINTAINER Jon <dlqiaoxy@cn.ibm.com>
WORKDIR /fsdms
COPY . .
RUN npm install
RUN npm run build

FROM nginx
MAINTAINER Jon <dlqiaoxy@cn.ibm.com>
COPY --from=node-builder /fsdms/dist/fsdms-angular-app /usr/share/nginx/html
COPY ./nginx-angular.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
