### If you would like to use your local MySQL:
commands to install mysql:

Installation command:

`brew install mysql`

Check if mysql is listed:

`brew services list`   

Start mysql:

`brew services start mysql`

Stop mysql:

`brew services stop mysql`

Start mysql in safe mode:

`sudo mysqld_safe --skip-grant-tables --skip-networking &`

Access mysql:

`mysql -u root`

Set new password:

`USE mysql;`

`ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';`

`FLUSH PRIVILEGES;`

Stop safe mode and exit:

`mysqladmin -u root -p shutdown`

Restart:
`brew services start mysql`

Login using the following command:
`mysql -u root -p`  

While mysql is running, type in `CREATE DATABASE IF NOT EXISTS stone;` to create the database.  

If connection fails, check that your mysql password is set to 'mysql'  


### If you would like to use a Docker image:  
Open Docker and make sure the engine is running.  
`docker pull mysql:8` 

`docker run --name=mysql-container -e MYSQL_ROOT_PASSWORD=mysql -d mysql:8`

`docker exec -it mysql-container mysql -uroot -p`  

While mysql is running, type in 1CREATE DATABASE IF NOT EXISTS stone;` to create the database.   


When you have finished running the project, stop and remove the mysql container (can be done in the docker GUI too):  
`docker stop mysql-container`  

`docker rm mysql-container`  
