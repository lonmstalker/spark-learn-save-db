CREATE ROLE event WITH PASSWORD 'event';
GRANT ALL PRIVILEGES ON DATABASE event TO event;
ALTER ROLE event WITH LOGIN;

CREATE USER dbz WITH PASSWORD 'dbz';
GRANT ALL PRIVILEGES ON DATABASE event to dbz;
ALTER ROLE dbz WITH LOGIN;

SET session_replication_role = local;