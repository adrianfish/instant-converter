alter table CONV_TOPICS add column CREATED1 datetime NOT NULL;
alter table CONV_TOPICS add column MODIFIED1 datetime NULL;
alter table CONV_TOPICS add column LAST_ACTIVITY1 datetime NULL;

alter table CONV_POSTS add column CREATED1 datetime NOT NULL;
alter table CONV_POSTS add column MODIFIED1 datetime NULL;

alter table CONV_POST_STATUS add column VIEWED_DATE1 datetime NULL;

alter table CONV_COMMENTS add column CREATED1 datetime NOT NULL;
alter table CONV_COMMENTS add column MODIFIED1 datetime NULL;

alter table CONV_USER_STATISTICS add column LAST_POST_DATE1 datetime NULL;



--After script adds the dates to the above fields, run the following:

/*
alter table CONV_TOPICS 
drop column CREATED, 
drop column MODIFIED, 
drop column LAST_ACTIVITY;

alter table CONV_TOPICS 
change CREATED1 CREATED datetime NOT NULL, 
change MODIFIED1 MODIFIED datetime NULL, 
change LAST_ACTIVITY1 LAST_ACTIVITY datetime NULL;

alter table CONV_POSTS 
drop column CREATED, 
drop column MODIFIED;

alter table CONV_POSTS 
change CREATED1 CREATED datetime NOT NULL, 
change MODIFIED1 MODIFIED datetime NULL;

alter table CONV_COMMENTS 
drop column CREATED, 
drop column MODIFIED;

alter table CONV_COMMENTS 
change CREATED1 CREATED datetime NOT NULL, 
change MODIFIED1 MODIFIED datetime NULL;

alter table CONV_POST_STATUS drop column VIEWED_DATE;
alter table CONV_POST_STATUS change VIEWED_DATE1 VIEWED_DATE datetime NULL;

alter table CONV_USER_STATISTICS drop column LAST_POST_DATE;
alter table CONV_USER_STATISTICS change LAST_POST_DATE1 LAST_POST_DATE datetime NULL;
*/