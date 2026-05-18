ALTER TABLE users
DROP COLUMN zip_code,
DROP COLUMN street,
DROP COLUMN number,
DROP COLUMN neighborhood,
DROP COLUMN city,
DROP COLUMN state,
DROP COLUMN complement;



CREATE TABLE addresses (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           user_id UUID NOT NULL UNIQUE,

                           zip_code VARCHAR(10),
                           street VARCHAR(150),
                           number VARCHAR(20),
                           neighborhood VARCHAR(100),
                           city VARCHAR(100),
                           state VARCHAR(2),
                           complement VARCHAR(255),

                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_addresses_users
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
);