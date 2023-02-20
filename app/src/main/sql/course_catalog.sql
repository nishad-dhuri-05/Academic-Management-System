CREATE TABLE IF NOT EXISTS course_catalog (
  course_code varchar(20) not null,
  L float(8) not null,
  T float(8) NOT NULL,
  P float(8) NOT NULL,
  department varchar(20) not null,
  credits float(8) not null,
  PRIMARY KEY (course_code)
) ;


insert into course_catalog (course_code,L,T,P,credits,department) 
values ('CS305',4,2,3,4.5,'CSE');

insert into course_catalog (course_code,L,T,P,credits,department) 
values ('CS301',3,1,3,4,'CSE');

insert into course_catalog (course_code,L,T,P,credits,department) 
values ('CS101',4,1,2,3,'CSE');

insert into course_catalog (course_code,L,T,P,credits,department) 
values ('CS201',4,1,2,4,'CSE');


-- update course_catalog set l=4.0 , t= 0.0 , p=3.5 , credits=4.5 where course_code='CS305';

