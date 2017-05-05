/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-05-05 16:25:30                          */
/*==============================================================*/


drop table if exists approve;

drop table if exists ask;

drop table if exists attendance;

drop table if exists company;

drop table if exists companyRoom;

drop table if exists department;

drop table if exists notice;

drop table if exists position;

drop table if exists user;

/*==============================================================*/
/* Table: approve                                               */
/*==============================================================*/
create table approve
(
   approve_ID           int not null,
   user_ID              char(11),
   approve_Content      char(100),
   approve_AdminID      char(11),
   primary key (approve_ID)
);

/*==============================================================*/
/* Table: ask                                                   */
/*==============================================================*/
create table ask
(
   user_ID              char(11) not null,
   room_ID              char(10) not null,
   ask_ID               char(10),
   ask_Title            char(20),
   ask_Content          char(50),
   primary key (user_ID, room_ID)
);

/*==============================================================*/
/* Table: attendance                                            */
/*==============================================================*/
create table attendance
(
   attendance_ID        char(10) not null,
   user_ID              char(11),
   attendance_Title     char(20),
   attendance_Content   char(50),
   attendance_Date      char(10),
   attendance_Place     char(50),
   primary key (attendance_ID)
);

/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company
(
   company_ID           char(6) not null,
   company_Name         char(20),
   company_OwnerID      char(11) not null,
   primary key (company_ID, company_OwnerID)
);

/*==============================================================*/
/* Table: companyRoom                                           */
/*==============================================================*/
create table companyRoom
(
   room_ID              char(10) not null,
   company_ID           char(6),
   company_OwnerID      char(11),
   room_Name            char(10),
   room_State           bool,
   primary key (room_ID)
);

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
   company_ID           char(6),
   company_OwnerID      char(11),
   department_ID        char(10) not null,
   department_Name      char(10)
);

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice
(
   notice_ID            int not null,
   user_ID              char(11),
   notice_content       char(50),
   notice_Todepartmentid char(10),
   primary key (notice_ID)
);

/*==============================================================*/
/* Table: position                                              */
/*==============================================================*/
create table position
(
   position_ID          char(10) not null,
   position_Name        char(10),
   primary key (position_ID)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_ID              char(11) not null,
   company_ID           char(6),
   company_OwnerID      char(11),
   user_Name            char(20),
   user_Password        char(8),
   user_Sex             char(1),
   user_Company         char(20),
   user_Isadmin         bool,
   user_Position        char(10),
   primary key (user_ID)
);

alter table approve add constraint FK_Relationship_4 foreign key (user_ID)
      references user (user_ID) on delete restrict on update restrict;

alter table ask add constraint FK_ask foreign key (user_ID)
      references user (user_ID) on delete restrict on update restrict;

alter table ask add constraint FK_ask2 foreign key (room_ID)
      references companyRoom (room_ID) on delete restrict on update restrict;

alter table attendance add constraint FK_Relationship_3 foreign key (user_ID)
      references user (user_ID) on delete restrict on update restrict;

alter table companyRoom add constraint FK_Relationship_2 foreign key (company_ID, company_OwnerID)
      references company (company_ID, company_OwnerID) on delete restrict on update restrict;

alter table department add constraint FK_Relationship_8 foreign key (company_ID, company_OwnerID)
      references company (company_ID, company_OwnerID) on delete restrict on update restrict;

alter table notice add constraint FK_Relationship_5 foreign key (user_ID)
      references user (user_ID) on delete restrict on update restrict;

alter table position add constraint FK_Relationship_9 foreign key ()
      references department on delete restrict on update restrict;

alter table user add constraint FK_Relationship_1 foreign key (company_ID, company_OwnerID)
      references company (company_ID, company_OwnerID) on delete restrict on update restrict;

