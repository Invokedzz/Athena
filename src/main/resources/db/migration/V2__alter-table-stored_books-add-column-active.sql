ALTER TABLE stored_books add column active integer;
update stored_books set active = 1;