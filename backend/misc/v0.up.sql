CREATE TABLE users (
    -- Compulsory fields populated upon creation
	date_created TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	date_updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	email TEXT NOT NULL,
	is_profile_public BOOLEAN NOT NULL DEFAULT FALSE,
	uid TEXT PRIMARY KEY,

	-- Personal information
	date_of_birth DATE,
	full_name TEXT,
	gender INTEGER,
	height NUMERIC(6, 2),
	profile_photo_url TEXT,
	weight NUMERIC(6, 2),

	-- Fitness Goals
    goal_weight NUMERIC(6, 2),
    goal_date DATE,
    CHECK (goal_weight > 0),
    CHECK (goal_date > CURRENT_DATE)
);
