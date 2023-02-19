create table enrollments(
    entry_no varchar(20),
    student_dept varchar(20),
    batch int ,

    course_code varchar(20),
    course_dept varchar(20),
    credit float8 ,

    type varchar(10),
    grade varchar(5),

    PRIMARY KEY (entry_no, course_code,grade)
    
);



