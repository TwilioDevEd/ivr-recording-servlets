CREATE TABLE agents (
  id SERIAL UNIQUE NOT NULL PRIMARY KEY,
  extension VARCHAR(50) NOT NULL,
  phone_number VARCHAR(50) NOT NULL
);

CREATE TABLE recordings (
  id SERIAL UNIQUE NOT NULL PRIMARY KEY,
  url VARCHAR(50) NOT NULL,
  transcription text NULL,
  phone_number VARCHAR(50) NOT NULL,
  agent_id     INT4          NOT NULL,
  FOREIGN KEY (agent_id) REFERENCES agents (id)
);

INSERT INTO agents(extension, phone_number) VALUES ('Brodo', '+15552483591');
INSERT INTO agents(extension, phone_number) VALUES ('Dugobah', '+15558675309');
INSERT INTO agents(extension, phone_number) VALUES ('113', '+15553185602');
