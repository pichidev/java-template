CREATE TABLE auth_identities
(
    id               UUID                        NOT NULL,
    provider         VARCHAR(50)                 NOT NULL,
    provider_user_id VARCHAR(255)                NOT NULL,
    user_id          UUID                        NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_auth_identities PRIMARY KEY (id)
);

ALTER TABLE auth_identities
    ADD CONSTRAINT uc_6be40caee356556feb09c3a1e UNIQUE (provider, provider_user_id);

CREATE INDEX idx_d84ad0dd4eb3f7a48c028b733 ON auth_identities (user_id);