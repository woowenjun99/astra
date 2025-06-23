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

CREATE TABLE accounts (
	date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    id BIGSERIAL PRIMARY KEY,
    provider_id VARCHAR(30) NOT NULL,
    uid VARCHAR(30) NOT NULL REFERENCES users
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

CREATE TABLE daily_logs (
    comments VARCHAR(500),
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date DATE NOT NULL DEFAULT current_date,
    id BIGSERIAL PRIMARY KEY,
    uid VARCHAR(30) NOT NULL REFERENCES users ON DELETE CASCADE,
    weight NUMERIC(10, 2)
);

CREATE TABLE workout_logs (
    calories_burnt INTEGER,
    date DATE NOT NULL,
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    duration INTEGER,
    id BIGSERIAL PRIMARY KEY,
    intensity VARCHAR(10),
    title VARCHAR(50) NOT NULL,
    uid VARCHAR(30) NOT NULL REFERENCES users ON DELETE CASCADE
);

CREATE TABLE exercises (
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    id BIGSERIAL PRIMARY KEY,
    index INTEGER NOT NULL,
    name VARCHAR(50) NOT NULL,
    reps INTEGER NOT NULL,
    sets INTEGER NOT NULL,
    weight INTEGER,
    workout_log_id BIGSERIAL NOT NULL REFERENCES workout_logs ON DELETE CASCADE
);

CREATE TABLE runs (
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    distance INTEGER NOT NULL,
    duration INTEGER NOT NULL,
    id BIGSERIAL PRIMARY KEY,
    index INTEGER NOT NULL,
    workout_log_id BIGSERIAL NOT NULL REFERENCES workout_logs ON DELETE CASCADE
);
