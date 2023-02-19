CREATE TABLE IF NOT EXISTS course_catalog (
  course_code varchar(20) not null,
  L float(8) not null,
  T float(8) NOT NULL,
  P float(8) NOT NULL,
  Credits float(8) not null,
  PRIMARY KEY (course_code)
) ;


insert into course_catalog (course_code,L,T,P,Credits) 
values ('CS305',4,2,3,4.5);

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

update course_catalog set l=4.0 , t= 0.0 , p=3.5 , credits=4.5 where course_code='CS305';

