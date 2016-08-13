CREATE TABLE entry
(
  id bigserial NOT NULL,
  content character varying(1024),
  creation_date timestamp without time zone,
  CONSTRAINT entry_pkey PRIMARY KEY (id)
)