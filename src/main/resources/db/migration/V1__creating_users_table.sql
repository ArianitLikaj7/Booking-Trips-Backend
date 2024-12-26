CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       username VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255)
);

insert into users(id, created_at, first_name, last_name, username, password, role)
values (1,
        current_timestamp,
        'Arianit',
        'Likaj',
        'superadmin',
        '$2a$12$Y4EY97bPUPBh3SC.7zgUj.F77zwSCYQT8QjhmTg.jVv3BUKEKBiPK',
        'SUPER_ADMIN');