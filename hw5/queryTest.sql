/* Test Rollback and Recovery Here */

select * from data;
select * from s;

set transaction read write;

insert into data values (7, 100);
insert into data values (8, 200);
insert into s values (4, 18, 'James');
insert into s values (5, 19, 'John');

### flush;
rollback;

/* update table and end transaction above */

select * from data;
select * from s;