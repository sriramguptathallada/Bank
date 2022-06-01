create database project;
use project;
create table new_user(id int not null primary key auto_increment,name varchar(100),email varchar(100),pin int,available_balance int,account_number int); 

create table logindetails(loginid int not null primary key auto_increment,name varchar(100),new_userid int ,action varchar(100),datetime datetime,
foreign key(new_userid) references new_user(id));