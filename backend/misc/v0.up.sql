CREATE TABLE users (
	date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	email TEXT NOT NULL,
	full_name TEXT NOT NULL,
	profile_photo_url TEXT,
	uid TEXT PRIMARY KEY,
    goal_weight NUMERIC(6, 2),
    goal_date DATE,
    CHECK (goal_weight > 0),
    CHECK (goal_date > CURRENT_DATE)
);