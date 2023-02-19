create table auth(
    email varchar(30) primary key not null,
    name varchar(50) ,
    role varchar(30) not null,
    batch INT,
    password varchar(30)NOT NULL,
    joining_date DATE NOT NULL DEFAULT CURRENT_DATE
);

insert into auth(email,role,name,password)
values ('acad@iitrpr.ac.in', 'acad' , 'acad office','acadpass');

insert into auth(email,role,name,password)
values ('faculty1@iitrpr.ac.in', 'faculty', 'faculty1','faculty1pass');

insert into auth(email,role,batch,name,password)
values ('student1@iitrpr.ac.in', 'student','2020', 'student1','student1pass');
