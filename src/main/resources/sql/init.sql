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

-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id serial PRIMARY KEY,
    first_name text COLLATE pg_catalog."default",
    last_name text COLLATE pg_catalog."default",
    keycloak_id text COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
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
    user_id_fk bigint,
    CONSTRAINT project_id_fk FOREIGN KEY (project_id_fk)
        REFERENCES public.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id_fk_constrain FOREIGN KEY (user_id_fk)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.task
    OWNER to postgres;


