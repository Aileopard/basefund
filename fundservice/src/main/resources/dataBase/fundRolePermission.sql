drop table fund_role_permission;
CREATE TABLE IF NOT EXISTS `fund_role_permission`(
                                                     `id` INT UNSIGNED AUTO_INCREMENT COMMENT '主键',
                                                     `role_id` VARCHAR(50) COMMENT '所属上级',
    `permission_id` VARCHAR(200) NOT NULL COMMENT '名称',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 1 是， 0 否',
    `gmt_create` DATE COMMENT '创建时间',
    `gmt_modified` DATE COMMENT '更新时间',
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 案例
INSERT INTO fund_role_permission (role_id, permission_id, gmt_create, gmt_modified)
VALUES
('1', '1', SYSDATE(), SYSDATE());