#!/bin/bash

# Ejecutar contenedores de MySQL
docker run -p 3306:3306 --name milk-collection-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=milk-collection-db -d mysql
docker run -p 3307:3306 --name persons-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=persons-db -d mysql
docker run -p 3308:3306 --name financial-management-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=financial-management-db -d mysql
