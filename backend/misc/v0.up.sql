CREATE TABLE users (
	bio VARCHAR(500),
	date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	date_of_birth DATE,
	date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	email VARCHAR(50) NOT NULL,
	full_name VARCHAR(50),
	gender VARCHAR(20),
	uid VARCHAR(30) PRIMARY KEY
);

CREATE TABLE fitness_goals (
    category VARCHAR(10) NOT NULL,
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    description VARCHAR(500),
    target_date DATE NOT NULL,
    target_value NUMERIC(10, 2) NOT NULL,
    title VARCHAR(30) NOT NULL,
    uid VARCHAR(30) NOT NULL REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (category, uid)
);

CREATE TABLE fitness_log (
    category VARCHAR(10) NOT NULL,
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_logged DATE NOT NULL,
    description VARCHAR(500),
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    uid VARCHAR(30) NOT NULL REFERENCES users ON DELETE CASCADE,
    value NUMERIC(10, 2) NOT NULL
);
