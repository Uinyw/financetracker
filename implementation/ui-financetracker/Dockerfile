FROM node:latest as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install
RUN npm run build --prod

FROM nginx:latest
COPY --from=build /usr/local/app/dist/ui-financetracker /usr/share/nginx/html
EXPOSE 80