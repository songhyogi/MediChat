CREATE TABLE Disease(
dis_num	NUMBER	NOT NULL primary key,
dis_name VARCHAR2(90) NOT NULL,
dis_symptoms	VARCHAR2(300)	NOT NULL,
dis_department	VARCHAR2(2)	NOT NULL,
dis_hit	NUMBER	DEFAULT 0	NOT NULL
);
CREATE sequence dis_seq;


CREATE TABLE Healthy_Blog (
	healthy_num	NUMBER		NOT NULL primary key,
	mem_num	NUMBER		NOT NULL,
	healthy_title	VARCHAR2(90)		NOT NULL,
	healthy_content	CLOB		NOT NULL,
	h_reg_date	DATE	DEFAULT SYSDATE	NOT NULL,
	h_modify_date	DATE		NULL,
	healthy_hit	NUMBER(8)	DEFAULT 0	NOT NULL,
    constraint healthy_fk1 foreign key (mem_num) references member(mem_num)
);

create sequence health_seq;

