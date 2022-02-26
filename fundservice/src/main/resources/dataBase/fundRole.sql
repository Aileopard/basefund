drop table fund_role;
CREATE TABLE IF NOT EXISTS `fund_role`(
                                          `id` INT UNSIGNED AUTO_INCREMENT COMMENT '主键',
                                          `role_name` VARCHAR(100) NOT NULL COMMENT '角色名称',
    `role_code` VARCHAR(40) NOT NULL COMMENT '角色代码',
    `remark` VARCHAR(200) COMMENT '备注',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 1 是， 0 否',
    `gmt_create` DATE COMMENT '创建时间',
    `gmt_modified` DATE COMMENT '更新时间',
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 案例
INSERT INTO fund_role (role_name, role_code, gmt_create, gmt_modified)
VALUES
('admin', '1', SYSDATE(), SYSDATE());