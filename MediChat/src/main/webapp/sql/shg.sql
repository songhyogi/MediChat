create table dayoff(
 doff_num number not null,
 doc_num number not null,
 doff_date varchar2(30) not null,
 doff_time varchar2(30) not null,
 constraint doff_pk primary key (doff_num),
 constraint doff_fk foreign key (doc_num) references member (mem_num)
);
create table reservation(
 res_num number not null,
 mem_num number not null,
 doc_num number not null,
 res_status number(2) default 0 not null,
 res_type number(2) not null,
 res_date varchar2(30) not null,
 res_time varchar2(30) not null,
 res_reg date default sysdate not null,
 res_content clob not null,
 constraint res_pk primary key (res_num),
 constraint res_fk1 foreign key (mem_num) references member (mem_num),
 constraint res_fk2 foreign key (doc_num) references member (mem_num)
);