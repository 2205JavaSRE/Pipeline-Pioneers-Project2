BEGIN;
-- DROP SCHEMA project0;

CREATE SCHEMA project0 AUTHORIZATION postgres;

-- DROP SEQUENCE project0.accounts_account_id_seq;

CREATE SEQUENCE project0.accounts_account_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.transactions_account_id_seq;

CREATE SEQUENCE project0.transactions_account_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.transactions_transaction_id_seq;

CREATE SEQUENCE project0.transactions_transaction_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.transfer_requests_account_from_seq;

CREATE SEQUENCE project0.transfer_requests_account_from_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.transfer_requests_account_to_seq;

CREATE SEQUENCE project0.transfer_requests_account_to_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.transfer_requests_transfer_id_seq;

CREATE SEQUENCE project0.transfer_requests_transfer_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE project0.users_user_id_seq;

CREATE SEQUENCE project0.users_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;-- project0.accounts definition

-- Drop table

-- DROP TABLE project0.accounts;

CREATE TABLE project0.accounts (
	account_id serial4 NOT NULL,
	approved bool NOT NULL,
	account_nickname varchar(30) NOT NULL,
	account_type varchar(30) NOT NULL,
	account_balance float8 NOT NULL,
	CONSTRAINT accounts_pkey PRIMARY KEY (account_id)
);


-- project0.users definition

-- Drop table

-- DROP TABLE project0.users;

CREATE TABLE project0.users (
	user_id serial4 NOT NULL,
	user_type varchar(30) NOT NULL,
	username varchar(30) NOT NULL,
	user_password varchar(30) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id),
	CONSTRAINT users_username_key UNIQUE (username)
);


-- project0.transactions definition

-- Drop table

-- DROP TABLE project0.transactions;

CREATE TABLE project0.transactions (
	transaction_id serial4 NOT NULL,
	account_id serial4 NOT NULL,
	transaction_type varchar(30) NOT NULL,
	transaction_amt float8 NOT NULL,
	transaction_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id),
	CONSTRAINT transactions_account_id_fkey FOREIGN KEY (account_id) REFERENCES project0.accounts(account_id)
);


-- project0.transfer_requests definition

-- Drop table

-- DROP TABLE project0.transfer_requests;

CREATE TABLE project0.transfer_requests (
	transfer_id serial4 NOT NULL,
	account_from serial4 NOT NULL,
	account_to serial4 NOT NULL,
	transfer_amount float8 NOT NULL,
	CONSTRAINT transfer_requests_pkey PRIMARY KEY (transfer_id),
	CONSTRAINT transfer_requests_account_from_fkey FOREIGN KEY (account_from) REFERENCES project0.accounts(account_id),
	CONSTRAINT transfer_requests_account_to_fkey FOREIGN KEY (account_to) REFERENCES project0.accounts(account_id)
);


-- project0.users_accounts definition

-- Drop table

-- DROP TABLE project0.users_accounts;

CREATE TABLE project0.users_accounts (
	user_id int4 NOT NULL,
	account_id int4 NOT NULL,
	CONSTRAINT users_accounts_account_id_fkey FOREIGN KEY (account_id) REFERENCES project0.accounts(account_id),
	CONSTRAINT users_accounts_user_id_fkey FOREIGN KEY (user_id) REFERENCES project0.users(user_id)
);

INSERT INTO project0.users (username, user_password, user_type) values ('admin', 'password', 'employee');

COMMIT;
