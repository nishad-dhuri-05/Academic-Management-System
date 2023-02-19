CREATE TABLE IF NOT EXISTS course_catalog (
  course_code varchar(20) not null,
  L INT not null,
  T INT NOT NULL,
  P INT NOT NULL,
  Credits int not null,
  PRIMARY KEY (course_code)
) ;


insert into course_catalog (course_code,L,T,P,Credits,) 
values ('CS305',4,2,3,4);

insert into course_catalog (course_code,L,T,P,Credits) 
values ('CS305',4,2,3,4);

insert into course_catalog (course_code,L,T,P,Credits) 
values ('CS101',4,1,2,3);


CREATE TABLE IF NOT EXISTS pre_reqs (
  course_code varchar(20) not null,
  pre_req varchar(20)NOT NULL,
  PRIMARY KEY (course_code,pre_req)
) ;

insert into pre_reqs (course_code,pre_req) 
values ('CS305','CS201');

insert into pre_reqs (course_code,pre_req) 
values ('CS305','CS301');

insert into pre_reqs (course_code,pre_req) 
values ('CS101','NIL');

