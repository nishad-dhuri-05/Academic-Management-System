create table course_offering(
    course_code varchar(20)NOT NULL,
    instructor_email varchar(30)NOT NULL,
    offering_dept varchar(20)NOT NULL,
    offered_dept varchar(20) ,
    batch int not null,
    min_cgpa float(8) NOT NULL,
    status varchar(20),
    type varchar(10),
    PRIMARY KEY(course_code,batch,offered_dept)
);


insert into course_offering(course_code,instructor_email,offering_dept,offered_dept,batch,min_cgpa,status,type)
values (
    'CS305', 'faculty1@iitrpr.ac.in', 'CSE' ,'CSE', 2020, 7.5 , 'Running' , 'PC'
);