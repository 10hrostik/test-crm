insert into users (id, username, password, registration_date, enabled, role)
values  ('7f000001-8fda-1763-818f-da6dc6910000', 'admin', 'c17b88979738f9f53cf437a8f7d12e5e4590c4ea1ec4d8caf1a2c78ba833d5b5', '2024-06-02 22:29:38.438193', true, 'USER'),
        ('7f000001-8fdc-1e49-818f-dcd6275a0000', 'admin1', 'c17b88979738f9f53cf437a8f7d12e5e4590c4ea1ec4d8caf1a2c78ba833d5b5', '2024-06-03 09:42:53.365710', true, 'USER');
insert into clients (id, company_name, branch, address)
values  ('5bb10554-cf12-4f24-b5d0-f0ac63dfa82e ', 'amous', 'logistics', 'Lviv'),
        ('67bb98f8-8bbb-48ce-887a-a9474ffa68a4', 'covizmo', 'enginnering', 'Lviv');
insert into contacts (id, name, surname, phone, email, created_by, client_id)
values  ('a1aa86c2-7df8-4a7c-9482-7b00a57eb5e0', 'Rostyslav', 'Horban', '0984601237', 'houseofwolves20', '7f000001-8fda-1763-818f-da6dc6910000', '5bb10554-cf12-4f24-b5d0-f0ac63dfa82e ');

insert into tasks (id, description, dead_line, status, created_by, assignee_id, dead_line_notified)
values  ('9c56d7da-2482-4369-9e70-831875b52b8a', 'Some desc', '2024-06-03 22:28:14.000000', 'COMPLETED', '7f000001-8fda-1763-818f-da6dc6910000', 'a1aa86c2-7df8-4a7c-9482-7b00a57eb5e0', false),
        ('7f000001-8fdc-1d29-818f-dc4d61730000', 'task 2', null, 'OPEN', '7f000001-8fda-1763-818f-da6dc6910000', 'a1aa86c2-7df8-4a7c-9482-7b00a57eb5e0', false);
