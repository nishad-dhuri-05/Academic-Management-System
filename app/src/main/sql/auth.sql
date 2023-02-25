create table auth(
    email varchar(30) primary key not null,
    entry_no varchar(30),
    name varchar(50) ,
    role varchar(30) not null,
    department varchar(10),
    batch INT,
    password varchar(30)NOT NULL,
    phone_number varchar(20) ,
    joining_date DATE NOT NULL DEFAULT CURRENT_DATE
);

insert into auth(email,role,name,password)
values ('acad@iitrpr.ac.in', 'acad' , 'Staff Dean office','acadpass');
insert into auth(email,role,name,password)
values ('acadtest@iitrpr.ac.in', 'acadtest' , 'Staff Dean office','acadtestpass');

insert into auth(email,role,name,password,department,phone_number)
values ('faculty1@iitrpr.ac.in', 'faculty', 'faculty1','faculty1pass','CSE','1234567890');
insert into auth(email,role,name,password,department,phone_number)
values ('faculty2@iitrpr.ac.in', 'faculty', 'faculty2','faculty2pass','CSE','1234567890');
insert into auth(email,role,name,password,department,phone_number)
values ('facultytest@iitrpr.ac.in', 'faculty', 'facultytest','facultytestpass','CSE','1234567890');

insert into auth(email,role,batch,name,password,department,entry_no,phone_number)
values ('2020csb1317@iitrpr.ac.in', 'student','2020', 'Subham','2020csb1317','CSE','2020csb1317','123456789');
insert into auth(email,role,batch,name,password,department,entry_no,phone_number)
values ('2020csb1106@iitrpr.ac.in', 'student','2020', 'Parth','2020csb1106','CSE','2020csb1106','123456789');
insert into auth(email,role,batch,name,password,department,entry_no,phone_number)
values ('2020csb1153@iitrpr.ac.in', 'student','2020', 'Aman Kumar','2020csb1153','CSE','2020csb1153','123456789');
insert into auth(email,role,batch,name,password,department,entry_no,phone_number)
values ('2020csb1154@iitrpr.ac.in', 'student','2020', 'Aman Adatia','2020csb1154','CSE','2020csb1154','123456789');
insert into auth(email,role,batch,name,password,department,entry_no,phone_number)
values ('2020csbtest@iitrpr.ac.in', 'student','2020', 'student test','2020csbtest','CSE','2020csbtest','123456789');


-- select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where enrollments.start_acad_year =2021 and enrollments.semester=1 and course_catalog.course_code = enrollments.course_code and entry_no = '2020csb1153' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and status!='DROPPED' and status!='INSTRUCTOR WITHDREW' ;