CREATE TABLE `user` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `code` varchar(255) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(16) NOT NULL COMMENT '盐值',
  `person_id` varchar(36) NOT NULL COMMENT '关联person表的Id',
  `status` tinyint(1) NOT NULL COMMENT '状态（0：禁用，1：启用）',
  `validate_date` datetime NOT NULL COMMENT '用户账号有效期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_uid` varchar(36) DEFAULT NULL COMMENT '更新数据的用户id',
  `create_uid` varchar(36) DEFAULT NULL COMMENT '新建数据的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `descpt` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `code` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `create_uid` varchar(36) DEFAULT NULL COMMENT '添加数据的用户id',
  `update_uid` varchar(36) DEFAULT NULL COMMENT '更新数据的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '数据添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `user_id` varchar(36) NOT NULL COMMENT '用户Id，关联user表',
  `role_id` varchar(36) NOT NULL COMMENT '角色Id，关联role表',
  `update_uid` varchar(36) DEFAULT NULL COMMENT '更新数据的用户id',
  `create_uid` varchar(36) DEFAULT NULL COMMENT '新建数据的用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_permission` (
  `role_id` varchar(36) NOT NULL COMMENT '角色Id，关联role表',
  `permission_id` varchar(36) NOT NULL COMMENT '权限Id，关联permission表',
  `create_uid` varchar(36) DEFAULT NULL COMMENT '新建数据的用户id',
  `update_uid` varchar(36) DEFAULT NULL COMMENT '更新数据的用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `code` varchar(50) NOT NULL COMMENT '编码，对应权限代号',
  `url` varchar(50) DEFAULT NULL COMMENT '菜单url',
  `menu_id` varchar(20) DEFAULT NULL COMMENT '唯一标识页面中的元素，用于Jquery查找',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父菜单id',
  `zindex` int(2) DEFAULT NULL COMMENT '菜单排序',
  `typ` int(1) DEFAULT NULL COMMENT '权限分类（0 菜单；1（子菜单），2（按钮） 功能，3（其他））',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标名称',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `create_uid` varchar(36) DEFAULT NULL COMMENT '添加数据的用户id',
  `update_uid` varchar(36) DEFAULT NULL COMMENT '更新数据的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '数据新建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

