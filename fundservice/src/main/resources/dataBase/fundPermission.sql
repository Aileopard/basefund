-- drop table fund_permission;
CREATE TABLE IF NOT EXISTS `fund_permission`(
                                                `id` INT UNSIGNED AUTO_INCREMENT COMMENT '主键',
                                                `pid` VARCHAR(50) COMMENT '所属上级',
    `name` VARCHAR(200) NOT NULL COMMENT '名称',
    `type` VARCHAR(10) NOT NULL DEFAULT '1' COMMENT '类型(1:菜单,2:按钮)',
    `permission_value` VARCHAR(40) COMMENT '权限值',
    `icon` VARCHAR(40) COMMENT '图标',
    `status` VARCHAR(40) NOT NULL DEFAULT '1' COMMENT '状态(0:禁止,1:正常)',
    `path` VARCHAR(100)  COMMENT '访问路径',
    `component` VARCHAR(100) COMMENT '组建路径',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 1 是， 0 否',
    `gmt_create` DATE COMMENT '创建时间',
    `gmt_modified` DATE COMMENT '更新时间',
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 案例
INSERT INTO fund_permission (pid, name, gmt_create, gmt_modified)
VALUES
('1', '1', SYSDATE(), SYSDATE());