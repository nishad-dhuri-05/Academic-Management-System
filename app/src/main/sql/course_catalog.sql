CREATE TABLE IF NOT EXISTS course_catalog (
  course_code varchar(20) not null,
  L INT not null,
  T INT NOT NULL,
  P INT NOT NULL,
  Credits int not null,
  pre_req varchar(20),
  PRIMARY KEY (course_code,pre_req)
) ;


insert into course_catalog (course_code,L,T,P,Credits,pre_req) 
values ('CS305',4,2,3,4,'CS201');

insert into course_catalog (course_code,L,T,P,Credits,pre_req) 
values ('CS305',4,2,3,4,'CS301');

insert into course_catalog (course_code,L,T,P,Credits,pre_req) 
values ('CS101',4,1,2,3,'NIL');

