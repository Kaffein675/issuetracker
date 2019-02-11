CREATE TABLE issues(
    issue_uuid uuid primary key,
    title varchar(255) not null,
    discription text,
    publishing_date date
);

CREATE TABLE comments (
    comment_uuid uuid primary key,
    issue_uuid uuid references issues(issue_uuid),
    author varchar(255),
    content text,
    submission_date date
);

CREATE TABLE issue_status (
    issue_uuid uuid references issues(issue_uuid),
    status varchar(255)
);