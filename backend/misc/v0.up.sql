CREATE TABLE users (
	date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	uid TEXT PRIMARY KEY,
	email TEXT NOT NULL,
	date_of_birth DATE,
	full_name TEXT,
	gender INTEGER,
	bio TEXT,
    goal_weight NUMERIC(6, 2),
    goal_date DATE,
    CHECK (goal_weight > 0),
    CHECK (goal_date > CURRENT_DATE)
);

CREATE TABLE workout_log (
    calories_burnt INTEGER NOT NULL,
    date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    duration INTEGER NOT NULL,
    id BIGSERIAL PRIMARY KEY,
    intensity INTEGER NOT NULL DEFAULT 0,
    name TEXT,
    user_id TEXT NOT NULL REFERENCES users ON DELETE CASCADE
);
