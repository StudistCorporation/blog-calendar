-- config/migrations/20230519061211-create-calendars.up.sql
create table if not exists calendars (
  id serial primary key,
  created_by serial references users(id),
  title varchar not null,
  description text,
  invitation uuid not null default gen_random_uuid(),
  first_day date not null,
  last_day date not null,
  public_day date not null,
  close_day date
);
