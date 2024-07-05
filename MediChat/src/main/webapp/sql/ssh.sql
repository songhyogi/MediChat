CREATE TABLE Disease(
dis_num	NUMBER	NOT NULL primary key,
dis_name VARCHAR2(90) NOT NULL,
dis_symptoms	VARCHAR2(300)	NOT NULL,
dis_department	VARCHAR2(2)	NOT NULL,
dis_hit	NUMBER	DEFAULT 0	NOT NULL
);
CREATE sequence dis_seq;
