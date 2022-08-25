-- public."member" definition

-- Drop table

-- DROP TABLE public."member";

CREATE TABLE public."member" (
                                 id serial4 NOT NULL,
                                 name varchar(255) NOT NULL,
                                 address varchar(255) NOT NULL,
                                 birth_date date NOT NULL,
                                 balance int4 NOT NULL,
                                 CONSTRAINT member_pkey PRIMARY KEY (id)
);


-- public."transaction" definition

-- Drop table

-- DROP TABLE public."transaction";

CREATE TABLE public."transaction" (
                                      id serial4 NOT NULL,
                                      member_id int4 NOT NULL,
                                      transaction_type varchar(255) NOT NULL,
                                      amount int4 NOT NULL,
                                      transaction_date date NOT NULL,
                                      transaction_history_id int8 NULL,
                                      CONSTRAINT transaction_pkey PRIMARY KEY (id)
);


-- public."transaction" foreign keys

ALTER TABLE public."transaction" ADD CONSTRAINT member_id FOREIGN KEY (transaction_history_id) REFERENCES public."member"(id);
ALTER TABLE public."transaction" ADD CONSTRAINT transaction_member_id_fkey FOREIGN KEY (member_id) REFERENCES public."member"(id);