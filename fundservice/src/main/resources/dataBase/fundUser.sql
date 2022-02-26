drop table fund_user;
CREATE TABLE IF NOT EXISTS `fund_user`(
                                          `id` INT UNSIGNED AUTO_INCREMENT COMMENT '主键',
                                          `username` VARCHAR(100) NOT NULL COMMENT '用户名',
    `password` VARCHAR(40) NOT NULL COMMENT '密码',
    `nick_name` VARCHAR(50) NOT NULL COMMENT '昵称',
    `salt` VARCHAR(200) NOT NULL COMMENT '用户头像',
    `token` VARCHAR(200) NOT NULL COMMENT '用户签名',
    `is_deleted` TINYINT COMMENT '逻辑删除 1 是， 0 否',
    `gmt_create` DATE COMMENT '创建时间',
    `gmt_modified` DATE COMMENT '更新时间',
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 案例
INSERT INTO fund_user (username, password, nick_name, salt, token, gmt_create, gmt_modified)
VALUES
('leo', '123', 'leo-zu', 'salt', 'token', SYSDATE(), SYSDATE());