drop database if exists jingxiny_cinemate;
create database jingxiny_cinemate;
use jingxiny_cinemate;


create table Users(
	username varchar(255)binary primary key,
    user_password varchar(255) binary not null,
    fname varchar(255) not null,
    lname varchar(255) not null,
    image varchar(255) default 'img/profile_image_default.png'
);

create table FollowingTable(
	id int(255) primary key auto_increment,
	username varchar(255) binary not null,
    follower varchar(255) binary not null,
    foreign key (username) references Users(username),
    foreign key (follower) references Users(username)
);

create table Movies(
	imdbID varchar(255) binary primary key,
	title varchar(255)binary not null,
	totalRating int(255) not null,
	ratingCount int(255) not null
);

create table UserEvents(
	eventId int(255) primary key auto_increment,
    username varchar(255) binary not null,
    user_action varchar(255) not null,
    movieName varchar(255) binary not null,
    imdbID varchar(255)binary not null,
    rating int(255) default 0,
    event_time varchar(255) not null,
    foreign key(username) references Users(username)
    #foreign key(movie) references Movies(title)
);

