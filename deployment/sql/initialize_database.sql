CREATE ROLE product LOGIN PASSWORD 'product' SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
CREATE DATABASE coffee_shop WITH OWNER = product ENCODING = 'UTF8' TABLESPACE = pg_default;
GRANT ALL ON DATABASE coffee_shop TO product;


CREATE ROLE sonarqube LOGIN PASSWORD 'sonarqube' SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
CREATE DATABASE sonarqubedb WITH OWNER = sonarqube ENCODING = 'UTF8' TABLESPACE = pg_default;
GRANT ALL ON DATABASE sonarqubedb TO sonarqube;
