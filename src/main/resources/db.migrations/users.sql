create table users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    seed TEXT NOT NULL UNIQUE,
    role TEXT NOT NULL
);

