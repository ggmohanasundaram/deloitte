
CREATE TABLE customer
(
	customer_id number Not null primary key,
	first_name varchar(100) Not null,
	last_name varchar(100) Not null,
	address_id number

);

CREATE TABLE address
(
	address_id number Not null primary key,
	unit_number number,
	street_name varchar(100) Not null,
	suburb varchar(50) Not null,
	state varchar(5) Not null,
	postal_code number Not null

);

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY auto_increment,
    username VARCHAR(128) UNIQUE,
    password VARCHAR(256),
    user_group VARCHAR(256),
    enabled BOOL,
)   CHARSET=utf8;