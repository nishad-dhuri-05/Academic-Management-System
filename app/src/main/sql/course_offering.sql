create table course_offering(
    course_code varchar(20) NOT NULL,
    start_acad_year int ,
    semester int,

    instructor_email varchar(30)NOT NULL,
    offering_dept varchar(20)NOT NULL,

    status varchar(20),
    PRIMARY KEY(course_code,start_acad_year,semester)
);


insert into course_offering(course_code,start_acad_year,semester, instructor_email,offering_dept,status)
values (
    'CS305',2022,2, 'faculty1@iitrpr.ac.in', 'CSE' , 'Running'
);

