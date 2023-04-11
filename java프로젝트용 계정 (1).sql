CREATE TABLE fishuser(
id varchar2(15),
pw varchar2(15),
nick_name varchar2(30),
name varchar2(10),
address varchar2(100),
customer_phone varchar2(15),
customer_grade char(1) ,
repair_count number(5) default 0,
fishing_rod1 varchar2(30) default '없음',
fishing_rod2 varchar2 (30) default '없음',
fishing_rod3 varchar2 (30) default '없음',
fishing_rod4 varchar2 (30) default '없음',
fishing_rod5 varchar2 (30) default '없음',
constraint fish_user_primary primary key(nick_name));

commit;

drop table request;

SELECT *
FROM fishuser;

SELECT * 
FROM request;

SELECT *
FROM community;

drop trigger update_nk_rq;

CREATE or REPLACE TRIGGER update_nk_rq
AFTER UPDATE OF nick_name ON fishuser
FOR EACH ROW
BEGIN 
    UPDATE request
    SET nick_name = :new.nick_name
    WHERE nick_name = :old.nick_name;
END;
/

CREATE or REPLACE TRIGGER update_nk_comm
AFTER UPDATE OF nick_name ON fishuser
FOR EACH ROW
BEGIN 
    UPDATE community
    SET nick_name = :new.nick_name
    WHERE nick_name = :old.nick_name;
END;
/

CREATE or REPLACE TRIGGER update_nk_cm
AFTER UPDATE OF nick_name ON fishuser
FOR EACH ROW
BEGIN 
    UPDATE comments
    SET nick_name = :new.nick_name
    WHERE nick_name = :old.nick_name;
END;
/

INSERT INTO request
VALUES ( (SELECT max(num)FROM request)+1 ,'benhur41' ,2,'N');

SELECT *
FROM fishuser;

INSERT INTO fishuser (id , pw , nick_name , name, address , customer_phone , customer_grade)
VALUES ('kty12' , 'qwe123' ,'도농이', '김태연' ,'대구 달서구 달구벌대로 1429-18' , '010-5752-2287' ,'M'); 

DESC fishuser;
commit;

SELECT fishing_Rod1,fishing_Rod2,fishing_Rod3,fishing_Rod4,fishing_Rod5 FROM fishuser;

DELETE FROM fishuser WHERE nick_name = 'benhur41';

CREATE TABLE request(
num number(10) ,
nick_name varchar2(30),
fishingRod varchar2(30),
rp_num number(10),
state char(1) default 'N',
constraint foreign_rq_nick foreign key (nick_name)
references fishuser (nick_name) on delete cascade,
constraint foreign_num_repait foreign key (rp_num)
references repair (rp_num) );

drop table saverq;
DROP TABLE request;
SELECT nick_name , decode(rp_num,1,'세척/점검',
                                2,'초리복원',
                                3,'탑교환',
                                4,'손잡이대복원',
                                5,'가이드교환') "repair" ,state
FROM saverq;

drop table saverq;
CREATE TABLE saverq(
nick_name varchar2(30),
fishingRod varchar2(30),
rp_num number(10),
state char(3) default '완',
constraint fosv_fu_nk foreign key (nick_name)
REFERENCES fishuser (nick_name) on delete cascade,
constraint fo_save_rn foreign key (rp_num)
REFERENCES repair (rp_num));
commit;
SELECT *
FROM saverq;
SELECT * 
FROM request;
SELECT num, nick_name, repair, decode(state , 'R' , '수리중',
                                              'P' , '배송중',
                                                    '수리전') "state",decode(customer_grade,'A',price*0.9,
                                                                                         'B',price*0.95,
                                                                                         'C',price*0.97,
                                                                                             price) "discount_price"
FROM fishuser 
join request using (nick_name)
join repair using (rp_num)
WHERE nick_name = 'benhur41'
ORDER BY num;
--수리중이거나 
SELECT nick_name 
FROM request
WHERE num = 1;

SELECT num, nick_name,fishingRod, repair, decode(state , 'R' , '수리중',
				                                              'P' , '배송중',
				                                                    '수리전') "state" ,decode(customer_grade,'A',price*0.9,
					                                                           'B',price*0.95,
					                                                             'C',price*0.97,
					                                                                price) "discount_price"
FROM fishuser
join request using (nick_name)
join repair using (rp_num)
ORDER BY num;

select * 
from saverq;

select *
from request;

INSERT INTO request
VALUES ( 1, 'qwe123' , 2 ,'N');
INSERT INTO request
VALUES ( 2, 'qwe123' , 4 ,'N');
INSERT INTO request
VALUES ( 3, 'qwe123' , 5 ,'N');
INSERT INTO request
VALUES ( 4, 'benhur41' , 4 ,'N');
INSERT INTO request
VALUES ( 5, 'benhur41' , 4 ,'N');
INSERT INTO request
VALUES ( 6, 'benhur41' , 3 ,'N');
INSERT INTO request
VALUES ( 7, 'benhur41' , 3 ,'N');

commit;

SELECT *
FROM request;

SELECT repair, count(repair) as "count"
FROM repair  join request  using (rp_num)
group by repair
ORDER BY count(repair) DESC;

delete FROM request
where num  =1 ;

CREATE TABLE repair(
rp_num number(10) primary key,
repair varchar2(30) ,
price number(10));

Drop TABLE request;

DESC request;
DESC repair;
INSERT INTO repair
VALUES (1 , '세척/점검' , 40000);
INSERT INTO repair
VALUES (2 , '초리복원' , 50000);
INSERT INTO repair
VALUES (3 , '탑교환' , 30000);
INSERT INTO repair
VALUES (4 , '손잡이대복원' , 90000);
INSERT INTO repair
VALUES (5 , '가이드교환' , 30000);

DROP TABLE repair_price;

SELECT *
FROM repair;

DELETE FROM saverq;

UPDATE fishuser SET customer_grade = 'D' WHERE nick_name = 'benhur41';

SELECT *
FROM request;
commit;
DELETE FROM request WHERE nick_name = 'benhur41';

CREATE TABLE test(
co_num number(5) primary key,
title varchar2(100) not null,
write_date date default sysdate);

drop table test;

INSERT INTO test 
VALUES (1,'안녕하세요1',sysdate);
INSERT INTO test 
VALUES (2,'안녕하세요2',sysdate);
INSERT INTO test 
VALUES (3,'안녕하세요3',sysdate);
INSERT INTO test 
VALUES (4,'안녕하세요4',sysdate);
INSERT INTO test 
VALUES (5,'안녕하세요5',sysdate);

SELECT *
FROM test;

CREATE TABLE test2(
title varchar2(100),
contents varchar2(2000),
co_num number(5) ,
constraint const_test2_foreign foreign key (co_num)
References test (co_num) on delete cascade);

INSERT INTO test2
values ('zz','hi hello' , 3 );
INSERT INTO test2
values ('zz','hi hello' , 3 );
INSERT INTO test2
values ('zz','hi hello' , 3 );
INSERT INTO test2
values ('zz','hi hello' , 3 );
INSERT INTO test2
values ('zz','hi hello' , 3 );

commit;

SELECT *
FROM test2;
SELECT *
FROM test;

drop table test2;
drop table test;

SELECT employee_id , last_name , department_id , department_name
FROM employees FULL outer join departments 
USING ( department_id );
-- 트리거 
CREATE or REPLACE TRIGGER update_co_num
AFTER UPDATE OF co_num ON community
FOR EACH ROW
BEGIN 
    UPDATE comments
    SET co_num = :new.co_num
    WHERE co_num = :old.co_num;
END;
/
-- 커뮤티니 게시판
CREATE TABLE community(
co_num number(5) primary key,
nick_name varchar2(30),
title varchar2(60) not null,
content varchar2(2000) not null,
write_date Date default sysdate,
views number (4) default 0,
recommand number (4) default 0,
constraint foreign_fu_nk foreign key (nick_name)
REFERENCES fishuser (nick_name) on delete cascade);

DESC community;
DESC comments;

SELECT max(num)
FROM comments
WHERE co_num =1;

delete FROM comments WHERE co_num = 1;

CREATE TABLE comments(
true_num number(5) primary key,
co_num number(5) ,
num number(5),
nick_name varchar2(30),
content varchar2(1000),
write_date Date default sysdate,
recommand number(4) default 0,
constraint fo_comm_num foreign key (co_num)
REFERENCES community (co_num) on delete cascade,
constraint fo_fu_nk foreign key ( nick_name)
references fishuser (nick_name) on delete cascade);
drop table comments;
SELECT true_num FROM comments WHERE num =1 AND co_num = 1;

commit;
SELECT * FROM community ORDER BY co_num DESC;
drop table community;
commit;
drop table comments;
update test set co_num = co_num -1 WHERE co_num > ?;

drop trigger update_co_num;


SELECT employee_id , e.department_id ,department_name , street_address
FROM employees e 
join departments d on e.department_id = d.department_id
join locations l on d.location_id=l.location_id;


SELECT nick_name ,customer_grade,
        case customer_grade when 'A' then price*0.9
                            when 'B' then price*0.95
                            when 'C' then price*0.98
                            else price
                    end
                    as "할인가격"
FROM fishuser join request
using (nick_name);

SELECT repair , decode( customer_grade , 'A' ,price*0.9,
                                        'B' , price*0.95,
                                        'C' , price*0.98) as "discount",state
FROM repair 
join request USING (rp_num)
join fishuser USING (nick_name)
WHERE nick_name = 'benhur41';

CREATE TABLE recommand_safe(
co_num number(5),
nick_name varchar2(30),
constraint fo_safe_num foreign key (co_num)
references community (co_num) on delete cascade,
constraint fo_safe_name foreign key (nick_name)
references fishuser (nick_name) on delete cascade);

CREATE TABLE recommand_safe_CMS(
true_num number(5),
nick_name varchar2(30),
constraint fo_safe_tn foreign key (true_num)
references comments (true_num) on delete cascade,
constraint focm_safe_name foreign key (nick_name)
references fishuser (nick_name) on delete cascade);

SELECT *
FROM recommand_safe;

SELECT * 
FROM community;
desc saverq;
SELECT *
FROM request;
commit;
desc request;
select *
FROM comments;
DELETE FROM comments WHERE num = 1 AND co_num = 2;
UPDATE comments SET num = num - 1 WHERE co_num =1 AND num >2 ;
SELECT *
FROM recommand_safe_CMS;
commit;
delete from recommand_safe_CMS WHERE true_num = 1;