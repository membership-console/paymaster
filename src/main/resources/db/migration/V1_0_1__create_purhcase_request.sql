CREATE TABLE IF NOT EXISTS purchase_request
(
    id           UUID                     DEFAULT gen_random_uuid() NOT NULL,
    name         VARCHAR(255)                                       NOT NULL,
    description  VARCHAR(1023)                                      NOT NULL,
    price        INTEGER                                            NOT NULL,
    quantity     INTEGER                                            NOT NULL,
    url          VARCHAR(1023)                                      NOT NULL,
    status       INTEGER                                            NOT NULL,
    requested_by INTEGER                                            NOT NULL,
    requested_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TRIGGER refresh_purchase_request_updated_at_step1
    BEFORE UPDATE
    ON purchase_request
    FOR EACH ROW
EXECUTE PROCEDURE refresh_updated_at_step1();

CREATE TRIGGER refresh_purchase_request_updated_at_step2
    BEFORE UPDATE OF updated_at
    ON purchase_request
    FOR EACH ROW
EXECUTE PROCEDURE refresh_updated_at_step2();

CREATE TRIGGER refresh_purchase_request_updated_at_step3
    BEFORE UPDATE
    ON purchase_request
    FOR EACH ROW
EXECUTE PROCEDURE refresh_updated_at_step3();
