create table enrollments(
    entry_no varchar(20) not null,

    course_code varchar(20) not null,

    grade varchar(5),
    status varchar(20),

    PRIMARY KEY (entry_no, course_code)
    
);

create table students(
    entry_no varchar(20),
    student_dept varchar(20),
    batch int,

    PRIMARY KEY (entry_no)
);

insert into enrollments (entry_no,course_code,status)
values ('2020csb1317', 'CS305', 'RUNNING' );
insert into enrollments (entry_no,course_code,status)
values ('2020csb1106', 'CS305', 'RUNNING' );

insert into enrollments (entry_no,course_code,grade,status)
values ('2020csb1153', 'CS301','A', 'COMPLETED' );
insert into enrollments (entry_no,course_code,grade,status)
values ('2020csb1154', 'CS305', 'A','COMPLETED' );
insert into enrollments (entry_no,course_code,grade,status)
values ('2020csb1153', 'CS305', 'A','COMPLETED' );
insert into enrollments (entry_no,course_code,grade,status)
values ('2020csb1317', 'CS301', 'A-', 'COMPLETED' );
insert into enrollments (entry_no,course_code,grade,status)
values ('2020csb1106', 'CS301', 'A-','COMPLETED' );

insert into students (entry_no,student_dept,batch)
values ('2020csb1317','CSE',2020);
insert into students (entry_no,student_dept,batch)
values ('2020csb1106','CSE',2020);
insert into students (entry_no,student_dept,batch)
values ('2020csb1153','CSE',2020);
insert into students (entry_no,student_dept,batch)
values ('2020csb1154','CSE',2020);
