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
values ('CS301','CS201');

insert into pre_reqs (course_code,pre_req) 
values ('CS101','NIL');

insert into pre_reqs (course_code,pre_req) 
values ('CS201','NIL');
