FROM nginx
MAINTAINER Vikram Kumar
ADD . /usr/share/nginx/html/.
#RUN echo "Build Version '1'" > /usr/share/nginx/html/index.html
