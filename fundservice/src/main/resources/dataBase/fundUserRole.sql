drop table fund_user_role;
CREATE TABLE IF NOT EXISTS `fund_user_role`(
                                               `id` INT UNSIGNED AUTO_INCREMENT COMMENT '主键',
                                               `role_id` VARCHAR(100) NOT NULL COMMENT '角色id',
    `user_id` VARCHAR(40) NOT NULL COMMENT '用户id',
    `is_deleted` TINYINT default 0 COMMENT '逻辑删除 1 是， 0 否',
    `gmt_create` DATE COMMENT '创建时间',
    `gmt_modified` DATE COMMENT '更新时间',
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 案例
INSERT INTO fund_user_role (role_id, user_id, gmt_create, gmt_modified)
VALUES
('1', '1', SYSDATE(), SYSDATE());