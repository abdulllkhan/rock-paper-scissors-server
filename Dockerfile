# Use the official MySQL 8 image from Docker Hub
FROM mysql:8

# Set the root password for MySQL (change it to your desired password)
ENV MYSQL_ROOT_PASSWORD=mysql

# Set the default character set and collation
ENV MYSQL_CHARSET=utf8mb4
ENV MYSQL_COLLATION=utf8mb4_unicode_ci

# Set the name of the database to be created
ENV MYSQL_DATABASE=stone

# Set the MySQL data directory permissions
RUN chown -R mysql:root /var/lib/mysql/
RUN chmod -R 777 /var/lib/mysql/

# Expose the MySQL port
EXPOSE 3306

# Start MySQL when the container starts
CMD ["mysqld"]
