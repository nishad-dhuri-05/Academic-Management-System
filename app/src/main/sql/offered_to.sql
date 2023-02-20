create table offered_to(
    course_code varchar(20) NOT NULL,
    start_acad_year int ,
    semester int,

    offered_dept varchar(20) ,
    batch int not null,
    min_cgpa float(8) NOT NULL,
    type varchar(10),

    PRIMARY KEY(course_code,start_acad_year,semester)
);


insert into offered_to (course_code,start_acad_year,semester, offered_dept, batch , min_cgpa, type)
values (
    'CS305',2022,2, 'CSE',2020, 7 , 'PC'
);