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