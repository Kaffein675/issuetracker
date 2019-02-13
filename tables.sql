CREATE TABLE issues(
    issue_id int primary key AUTO_INCREMENT,
    title varchar(255) not null,
    discription text,
    publishing_date date
);

CREATE TABLE comments (
    comment_id int primary key AUTO_INCREMENT,
    issue_id int references issues(issue_id),
    author varchar(255),
    content text,
    submission_date date
);

CREATE TABLE issue_status (
    status_id int primary key AUTO_INCREMENT,
    issue_id int references issues(issue_id),
    status varchar(255)
);