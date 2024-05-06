FROM nginx:latest

# Copy default Nginx configuration (make sure you have a valid nginx.conf that supports HTTPS)
COPY nginx.conf /etc/nginx/nginx.conf

#COPY fullchain.pem /etc/letsencrypt/live/asset-management-system-4.norwayeast.cloudapp.azure.com/fullchain.pem

#COPY privkey.pem /etc/letsencrypt/live/asset-management-system-4.norwayeast.cloudapp.azure.com/privkey.pem
COPY build /usr/share/nginx/html

EXPOSE 80 443

# By default, run Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]