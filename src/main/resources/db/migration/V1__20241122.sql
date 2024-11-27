CREATE TABLE wallet_types
(
    uid           UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at    TIMESTAMP DEFAULT now() NOT NULL,
    modified_at   TIMESTAMP,
    name          VARCHAR(32)             NOT NULL,
    currency_code VARCHAR(3)              NOT NULL,
    status        VARCHAR(18)             NOT NULL,
    archived_at   TIMESTAMP,
    user_type     VARCHAR(15),
    creator       VARCHAR(255),
    modifier      VARCHAR(255)
);
CREATE TABLE wallets
(
    uid             UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at      TIMESTAMP DEFAULT now() NOT NULL,
    modified_at     TIMESTAMP,
    name            VARCHAR(32)             NOT NULL,
    wallet_type_uid UUID                    NOT NULL
        constraint fk_wallets_wallet_types references wallet_types,
    user_uid        UUID                    NOT NULL,
    status          VARCHAR(30)             NOT NULL,
    balance         DECIMAL   DEFAULT 0.0   NOT NULL,
    archived_at     TIMESTAMP
);

CREATE TABLE payment_requests
(
    uid               UUID      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at        TIMESTAMP DEFAULT now()              NOT NULL,
    modified_at       TIMESTAMP,
    user_uid          UUID                                 NOT NULL,
    wallet_uid        UUID                                 NOT NULL references wallets (uid),
    amount            DECIMAL   DEFAULT 0.0                NOT NULL,
    status            VARCHAR,
    comment           VARCHAR(256),
    payment_method_id bigint
);

CREATE TABLE transactions
(
    uid                 UUID      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at          TIMESTAMP DEFAULT now()              NOT NULL,
    modified_at         TIMESTAMP,
    user_uid            UUID                                 NOT NULL,
    wallet_uid          UUID                                 NOT NULL references wallets (uid),
    wallet_name         VARCHAR(32)                          NOT NULL,
    amount              DECIMAL   DEFAULT 0.0                NOT NULL,
    type                VARCHAR(32)                          NOT NULL,
    state               VARCHAR(32)                          NOT NULL,
    payment_request_uid UUID                                 NOT NULL references payment_requests on delete cascade
);
CREATE TABLE top_up_requests
(
    uid                 UUID      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at          TIMESTAMP DEFAULT now()              NOT NULL,
    provider            VARCHAR                              NOT NULL,
    payment_request_uid UUID                                 NOT NULL references payment_requests on delete cascade
);
CREATE TABLE withdrawal_requests
(
    uid                 UUID      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at          TIMESTAMP DEFAULT now()              NOT NULL,
    payment_request_uid UUID                                 NOT NULL references payment_requests on delete cascade
);
CREATE TABLE transfer_requests
(
    uid                      UUID      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at               TIMESTAMP DEFAULT now()              NOT NULL,
    system_rate              VARCHAR                              NOT NULL,
    payment_request_uid_from UUID                                 NOT NULL references payment_requests on delete cascade,
    payment_request_uid_to   UUID                                 NOT NULL references payment_requests on delete cascade
);