-- config/migrations/20230519011932-create-users.up.sql
create type user_status
    as enum ('admin', 'disabled', 'writer');
create table users (
  id serial primary key,
  auth_id uuid references auth.users(id),
  email varchar not null,
  display_name varchar not null,
  status user_status not null default 'writer'
);
