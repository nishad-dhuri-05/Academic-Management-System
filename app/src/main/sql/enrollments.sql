create table enrollments(
    entry_no varchar(20) not null,

    course_code varchar(20) not null,
    start_acad_year int not null,
    semester int not null,

    grade varchar(5),
    status varchar(20),

    PRIMARY KEY (entry_no, course_code, start_acad_year,semester,status)
    
);


insert into enrollments (entry_no,course_code,status,start_acad_year,semester)
values ('2020csb1317', 'CS305', 'RUNNING' , 2022,2);
insert into enrollments (entry_no,course_code,status,start_acad_year,semester)
values ('2020csb1106', 'CS305', 'RUNNING' , 2022,2);
insert into enrollments (entry_no,course_code,status,start_acad_year,semester)
values ('2020csb1154', 'CS305', 'RUNNING' , 2022,2);
insert into enrollments (entry_no,course_code,status,start_acad_year,semester)
values ('2020csb1153', 'CS305', 'RUNNING' , 2022,2);


insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1153', 'CS201','A', 'PASSED', 2021,1);
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1154', 'CS201', 'A','PASSED' , 2021, 1 );
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1153', 'CS101', 'A','PASSED' , 2020,2 );
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1317', 'CS201', 'A-', 'PASSED', 2021,1 );
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1106', 'CS201', 'A-','PASSED' , 2021,1);
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1153', 'CS202', 'A-','PASSED' , 2021,2);
insert into enrollments (entry_no,course_code,grade,status,start_acad_year,semester)
values ('2020csb1153', 'CS301', 'A-','PASSED' , 2022,1);



