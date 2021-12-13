alter table CONV_TOPICS add column CREATED1 datetime NOT NULL;
alter table CONV_TOPICS add column MODIFIED1 datetime NULL;
alter table CONV_TOPICS add column LAST_ACTIVITY1 datetime NULL;
alter table CONV_POSTS add column CREATED1 datetime NOT NULL;
alter table CONV_POSTS add column MODIFIED1 datetime NULL;
alter table CONV_COMMENTS add column CREATED1 datetime NOT NULL;
alter table CONV_COMMENTS add column MODIFIED1 datetime NULL;
alter table USER_STATISTICS add column LAST_POST_DATE1 NULL;
