-- config/migrations/20230519011932-create-users.down.sql
drop table if exists users;
drop type if exists user_status;
