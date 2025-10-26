CREATE TABLE user_roles
(
    role_id VARCHAR(255) NOT NULL,
    user_id UUID         NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id        UUID NOT NULL,
    name      VARCHAR(255),
    last_name VARCHAR(255),
    email     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role_model FOREIGN KEY (role_id) REFERENCES roles (name);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user_model FOREIGN KEY (user_id) REFERENCES users (id);