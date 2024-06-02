create table clients(
    id varchar(40) primary key,
    username varchar(32) not null unique,
    password text not null,
    company_name varchar(32),
    branch varchar(32),
    registration_date timestamp default current_timestamp,
    enabled boolean default true,
    role varchar(10) not null default 'USER'
);
create table contacts(
    id varchar(40) primary key,
    name varchar(25),
    surname varchar(25),
    phone varchar(25),
    email varchar(32),
    client_id varchar(40) references clients(id),
    target_client_id varchar(40) references clients(id)
);
create table tasks(
    id varchar(40) primary key,
    description varchar(2000),
    dead_line timestamp,
    status varchar(16),
    created_by varchar(40) references clients(id),
    assignee_id varchar(40) references contacts(id)
);