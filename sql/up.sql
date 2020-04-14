DROP TABLE IF EXISTS permission;
CREATE TABLE permission(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    access_level INTEGER NOT NULL
);
DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
DROP TABLE IF EXISTS permission_user;
CREATE TABLE permission_user(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    permission_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permission(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id)
);
CREATE TABLE images(
    id  INTEGER primary key autoincrement,
    img blob not null
);
DROP TABLE IF EXISTS manga;
CREATE TABLE manga(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(300) NOT NULL UNIQUE,
    altenativeName VARCHAR(300) NOT NULL UNIQUE,
    image INTEGER NOT NULL,
    imageCharacter INTEGER,
    genres VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    artist VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    active INTEGER NOT NULL,
    progress REAL NOT NULL,
    chapter REAL NOT NULL,
    CONSTRAINT fk_images FOREIGN KEY (image) REFERENCES images(id),
    CONSTRAINT fk_images2 FOREIGN KEY (imageCharacter) REFERENCES images(id)
);
DROP TABLE IF EXISTS role;
CREATE TABLE role(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE
);
DROP TABLE IF EXISTS staff;
CREATE TABLE staff(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(300) NOT NULL UNIQUE,
    active INTEGER NOT NULL DEFAULT '1'
);
DROP TABLE IF EXISTS role_staff;
CREATE TABLE role_staff(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    staff_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT fk_staff FOREIGN KEY (staff_id) REFERENCES staff(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id)
);
DROP TABLE IF EXISTS chapter;
CREATE TABLE chapter(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number REAL NOT NULL,
    manga_id INTEGER NOT NULL,
    translator_id INTEGER NOT NULL,
    typesetter_id INTEGER NOT NULL,
    cleaner_id INTEGER NOT NULL,
    reviser_id INTEGER NOT NULL,
    redraw_id  INTEGER NOT NULL,
    imgur_link TEXT,
    union_link TEXT,
    media_link TEXT
);
create table news(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE,
    postDate TEXT NOT NULL,
    type INTEGER NOT NULL,
    content TEXT NOT NULL,
    url TEXT NOT NULL,
    author INTEGER NOT NULL,
    CONSTRAINT fk_staff FOREIGN KEY (author) REFERENCES staff(id)
);
INSERT INTO role (name) VALUES
('tradutor jp-pt'),
('tradutor en-pt'),
('typesetter'),
('cleaner'),
('revisor'),
('redraw');

INSERT INTO staff (name) VALUES
('fernanda'),
('zetsuki'),
('rodemarck'),
('just a clear note'),
('rapha');

INSERT INTO role_staff(staff_id, role_id) VALUES
(1,2),
(1,3),
(1,4),
(1,6),
(2,5),
(3,2),
(4,2),
(5,5);

SELECT * FROM staff s
    INNER JOIN role_staff rs
    ON s.id = rs.staff_id
        INNER JOIN role r
        ON rs.role_id = r.id
WHERE s.id=?