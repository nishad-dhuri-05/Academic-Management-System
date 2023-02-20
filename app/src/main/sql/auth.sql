create table auth(
    email varchar(30) primary key not null,
    entry_no varchar(30),
    name varchar(50) ,
    role varchar(30) not null,
    department varchar(10),
    batch INT,
    password varchar(30)NOT NULL,
    joining_date DATE NOT NULL DEFAULT CURRENT_DATE
);

insert into auth(email,role,name,password)
values ('acad@iitrpr.ac.in', 'acad' , 'acad office','acadpass');

insert into auth(email,role,name,password,department)
values ('faculty1@iitrpr.ac.in', 'faculty', 'faculty1','faculty1pass','CSE');

insert into auth(email,role,name,password,department)
values ('faculty2@iitrpr.ac.in', 'faculty', 'faculty2','faculty2pass','CSE');

insert into auth(email,role,batch,name,password,department,entry_no)
values ('2020csb1317@iitrpr.ac.in', 'student','2020', 'Subham','2020csb1317','CSE','2020csb1317');
insert into auth(email,role,batch,name,password,department,entry_no)
values ('2020csb1106@iitrpr.ac.in', 'student','2020', 'Parth','2020csb1106','CSE','2020csb1106');
insert into auth(email,role,batch,name,password,department,entry_no)
values ('2020csb1153@iitrpr.ac.in', 'student','2020', 'Aman Kumar','2020csb1153','CSE','2020csb1153');
insert into auth(email,role,batch,name,password,department,entry_no)
values ('2020csb1154@iitrpr.ac.in', 'student','2020', 'Aman Adatia','2020csb1154','CSE','2020csb1154');

