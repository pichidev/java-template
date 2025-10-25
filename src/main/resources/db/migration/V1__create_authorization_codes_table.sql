CREATE TABLE authorization_codes
(
    code        VARCHAR(255) NOT NULL,
    user_payload JSONB,
    expires_at   TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_authorization_codes PRIMARY KEY (code)
);