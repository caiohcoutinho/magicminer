CREATE SEQUENCE gamenumber_id_seq;

create table GameNumber(
	gamenumberid int NOT NULL DEFAULT nextval('gamenumber_id_seq'),
	gamedate date not null,
	gamenumber int not null,
	PRIMARY KEY (gamenumberid)
);

CREATE SEQUENCE gametable_id_seq;

create table GameTable(
	gametableid int NOT NULL DEFAULT nextval('gametable_id_seq'),
	name varchar(255) not null,
	PRIMARY KEY (gametableid)
);

create sequence game_id_seq;

create table game(
    gameid int not null default nextval('game_id_seq'),
    gametableid int references gametable(gametableid),
    gamenumberid int references gamenumber(gamenumberid),
    primary key (gameid)
);

create sequence ball_id_seq;

create table ball(
    ballid int not null default nextval('ball_id_seq'),
    gameid int references game(gameid),
    ballnumber int not null,
    primary key (ballid),
    CONSTRAINT UC_game_ballnumber UNIQUE (gameid, ballnumber)
);

create sequence configuration_id_seq;

create table configuration(
    configurationid int not null default nextval('configuration_id_seq'),
    name varchar(255) not null,
    discriminator varchar(255) not null,
    primary key (configurationid)
);

create table longconfiguration(
    configurationid int references configuration(configurationid),
    value int not null,
    primary key (configurationid)
);