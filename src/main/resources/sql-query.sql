DROP DATABASE IF EXISTS bac_game;
CREATE DATABASE bac_game;
use bac_game;
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  user_id  int         not null auto_increment,
  username varchar(40) NOT NULL,
  password varchar(60) NOT NULL
  token varchar (60),
  PRIMARY KEY (user_id, username)
);

create table game (
  game_id    int    not null auto_increment primary key,
  user_id    int    not null,
  secret_num int(4) not null,
  leaved     bit(1)          default 1
);
create table attempts (
  game_id  int       not null,
  num      int       not null,
  attempt  int(4)    not null,
  datetime timestamp not null
);

select *
from user;
select *
from game;
select *
from attempts;


#Record
select
  u.username,
  avg( a.maxnum) as avg
from
  game g
  inner join
  (select
     max(num) as maxnum,
     game_id
   from attempts
   group by game_id) as a on g.game_id = a.game_id
  inner join user u on g.user_id = u.user_id
where leaved = 0
group by username
order by avg
