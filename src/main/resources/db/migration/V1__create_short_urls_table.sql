CREATE TABLE short_urls (
    id              BIGSERIAL PRIMARY KEY,
    short_code      VARCHAR(16) NOT NULL,
    original_url    VARCHAR(2048) NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    expires_at      TIMESTAMP,
    click_count     BIGINT NOT NULL DEFAULT 0,
    created_by      VARCHAR(255),
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uq_short_urls_short_code UNIQUE (short_code)
);

CREATE INDEX idx_short_urls_short_code ON short_urls (short_code);
CREATE INDEX idx_short_urls_is_active_expires_at ON short_urls (is_active, expires_at);