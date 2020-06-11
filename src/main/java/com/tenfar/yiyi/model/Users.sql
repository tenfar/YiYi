-- auto Generated on 2020-06-06
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         VARCHAR(20) NOT NULL COMMENT 'id',
    `name`     VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户名',
    `password` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '密码',
    email      VARCHAR(64) NOT NULL DEFAULT '' COMMENT '电子邮件',
    phone      VARCHAR(50) NOT NULL DEFAULT '' COMMENT '电话号码',
    sex        INT(1)      NOT NULL DEFAULT -1 COMMENT '性别',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'users';
