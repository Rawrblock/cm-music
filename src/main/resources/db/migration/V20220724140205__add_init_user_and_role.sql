INSERT INTO `user` (id, username, nickname, password, created_time, updated_time)
VALUES ('1', 'admin', '村民', '$2a$10$78s1Sduoppcpa/xDgF8rrOauvDEuN5aq7sMgym4Hi1pvkJZWr0YgO',
        '2022-07-24 14:41:12.260000', '2022-07-24 14:41:12.260000');

INSERT INTO `role` (id, name, title, created_time, updated_time)
VALUES ('1', 'ROLE_USER', '普通用户', '2022-07-24 14:41:12.260000', '2022-07-24 14:41:12.260000');

INSERT INTO `role` (id, name, title, created_time, updated_time)
VALUES ('2', 'ROLE_ADMIN', '超级管理员', '2022-07-24 14:41:12.260000', '2022-07-24 14:41:12.260000');

INSERT INTO `user_role` (user_id, role_id)
VALUES ('1', '1');

INSERT INTO `user_role` (user_id, role_id)
VALUES ('1', '2');

