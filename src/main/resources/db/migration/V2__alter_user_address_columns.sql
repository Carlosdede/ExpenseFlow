ALTER TABLE users
RENAME COLUMN district to neighborhood;

ALTER TABLE users
ADD COLUMN complement VARCHAR(255);