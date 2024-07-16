--의약품 API
create table DRUG(
	drg_num number not null,
	drg_name varchar2(4000),
	drg_code varchar2(4000),
	drg_company varchar2(4000),
	drg_effect varchar2(4000),
	drg_dosage CLOB,
	drg_warning CLOB,
	drg_precaution CLOB,
	drg_interaction CLOB,
	drg_seffect CLOB,
	drg_storage CLOB,
	drg_img varchar2(3000),
	constraint drug_pk primary key (drg_num)
);
create sequence drug_seq;

--의약품 복용 기록
create table MEDICINE(
	med_num number not null,
	mem_num number not null,
	med_title varchar2(400) not null,
	med_name clob not null,
	med_date varchar2(10) not null,
	med_time varchar2(30) not null,
	med_dosage varchar2(50) not null,
	med_note varchar2(300),
	constraint medicine_pk primary key (med_num),
	constraint medicine_fk foreign key (mem_num) references member(mem_num)
);
create sequence medicine_seq;