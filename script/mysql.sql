DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS document_archive;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS comments_archive;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS branch;

 CREATE TABLE users (
  username varchar(45) NOT NULL,
  password varchar(60) NOT NULL,
  enabled tinyint(4) NOT NULL DEFAULT 1,
  branchCode int(11) DEFAULT 0,
  PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) DEFAULT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE document_archive (
  documentId int(11) DEFAULT NULL,
  state varchar(45) DEFAULT NULL,
  fileName varchar(100) DEFAULT NULL,
  fileLocation varchar(255) DEFAULT NULL,
  branchCode int(11) DEFAULT NULL,
  createdBy varchar(60) DEFAULT NULL,
  placeOfMeeting varchar(100) DEFAULT NULL,
  bookletNo varchar(60) DEFAULT NULL,
  applicationNo int(11) DEFAULT NULL,
  numOfCustomers int(11) DEFAULT NULL,
  lockedBy varchar(60) DEFAULT NULL,
  completedBy varchar(60) DEFAULT NULL,
  approvedBy varchar(60) DEFAULT NULL,
  assignedTo varchar(60) DEFAULT NULL,
  queryLevel int(11) DEFAULT NULL,
  onHold tinyint(4) DEFAULT NULL,
  locked tinyint(4) DEFAULT NULL,
  approved tinyint(4) DEFAULT NULL,
  documentArchiveId int(11) NOT NULL AUTO_INCREMENT,
  rescanNeeded tinyint(4) DEFAULT NULL,
  recCreatedOn varchar(60) DEFAULT NULL,
  recCompletedOn varchar(60) DEFAULT NULL,
  recApprovedOn varchar(60) DEFAULT NULL,
  PRIMARY KEY (documentArchiveId)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

CREATE TABLE document (
  documentId int(11) NOT NULL AUTO_INCREMENT,
  state varchar(60) DEFAULT NULL,
  fileName varchar(100) DEFAULT NULL,
  fileLocation varchar(255) DEFAULT NULL,
  branchCode int(11) DEFAULT NULL,
  createdBy varchar(60) DEFAULT NULL,
  placeOfMeeting varchar(100) DEFAULT NULL,
  bookletNo varchar(60) DEFAULT NULL,
  applicationNo int(11) DEFAULT NULL,
  numOfCustomers int(11) DEFAULT NULL,
  lockedBy varchar(60) DEFAULT NULL,
  completedBy varchar(60) DEFAULT NULL,
  approvedBy varchar(60) DEFAULT NULL,
  assignedTo varchar(60) DEFAULT NULL,
  queryLevel int(11) DEFAULT NULL,
  onHold tinyint(4) DEFAULT NULL,
  locked tinyint(4) DEFAULT NULL,
  approved tinyint(4) DEFAULT NULL,
  rescanNeeded tinyint(4) DEFAULT NULL,
  recCreatedOn varchar(60) DEFAULT NULL,
  recApprovedOn varchar(60) DEFAULT NULL,
  recCompletedOn varchar(60) DEFAULT NULL,
  putOnHoldBy varchar(60) DEFAULT NULL,
  PRIMARY KEY (documentId)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;

CREATE TABLE comments_archive (
  commentId int(11) DEFAULT NULL,
  documentId int(11) DEFAULT NULL,
  commentedBy varchar(60) DEFAULT NULL,
  recCreatedOn varchar(60) DEFAULT NULL,
  state varchar(45) DEFAULT NULL,
  comments text,
  idx varchar(45) DEFAULT NULL,
  commentArchiveId int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (commentArchiveId)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

CREATE TABLE comments (
  commentId int(11) NOT NULL AUTO_INCREMENT,
  documentId int(11) NOT NULL,
  commentedBy varchar(60) NOT NULL,
  recCreatedOn varchar(60) DEFAULT NULL,
  state varchar(45) DEFAULT NULL,
  comments text,
  idx int(11) DEFAULT NULL,
  PRIMARY KEY (commentId)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8;

CREATE TABLE branch (
  branchCode int(11) NOT NULL,
  branchName varchar(60) NOT NULL,
  zone varchar(60) NOT NULL,
  region varchar(60) NOT NULL,
  enabled tinyint(4) NOT NULL,
  PRIMARY KEY (branchCode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users
(username,
password,
enabled)
VALUES
('vindhya',
'$2a$10$YPTiApdzgmaga9aT83kyOu4Le8Zn3WsO7QTt.PmFeDcGSpTu.qYhi',
1);

INSERT INTO user_roles
(user_role_id,
username,
role)
VALUES
(1,'vindhya','ROLE_ADMIN');
