-- Table: public.project

-- DROP TABLE IF EXISTS public.project;

CREATE TABLE IF NOT EXISTS public.project
(
    id serial PRIMARY KEY,
    name text COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    status text COLLATE pg_catalog."default",
    start_date timestamp with time zone
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.project
    OWNER to postgres;


-- Table: public.task

-- DROP TABLE IF EXISTS public.task;

CREATE TABLE IF NOT EXISTS public.task
(
    id serial PRIMARY KEY,
    header text COLLATE pg_catalog."default",
    link_info text COLLATE pg_catalog."default",
    status text COLLATE pg_catalog."default",
    start_time timestamp with time zone,
    finish_time timestamp with time zone,
    project_id_fk bigint,
    description text COLLATE pg_catalog."default",
    user_keycloak_id text,
    CONSTRAINT project_id_fk FOREIGN KEY (project_id_fk)
        REFERENCES public.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.task
    OWNER to postgres;


INSERT INTO project(name, description, status)
VALUES ('TEST_PROJECT', 'DESCRIPTION', 'ACTIVE');

INSERT INTO task(header, link_info, status, project_id_fk, description)
VALUES ('TEST_TASK', 'TEST_LINK', 'COMPLETE', 1, 'DESCRIPTION');

