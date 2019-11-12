
use diary;
#用户表
create table `sys_user` (
    `id` int not null auto_increment COMMENT 'userID自增ID',
    `username` varchar(255) not null default '' comment '用户名',
    `password` varchar(255) not null default '' comment '密码',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

insert into sys_user(username,password)values('root','4QrcOUm6Wau+VuBX8g+IPg==');




#文章表
create table `sys_article` (
    `id` int not null auto_increment COMMENT '自增ID',
    `title` varchar(255) not null default '' comment '标题',
    `url` varchar(255)  default '' comment '链接地址',
    `icon` varchar(64)  default '' comment '作为导航栏时，菜单图标',
    `content` MEDIUMTEXT not null   comment 'markdown 文本内容',
    `html` MEDIUMTEXT not null   comment '富文本内容',
    `edit` int(1) not null default 0  comment '0 为markdown 内容 1 为 文本类型',
    `dateb` timestamp not null default current_timestamp comment '页面中的创建时间',
    `original` char(1) not null default '0'  comment '0 为原创 1 则相反',
    `comments` char(1) not null default '1'  comment '0 不开启评论 1 则相反',
    `top` char(1) not null default '0' comment '0 置顶 1 则相反',
    `status` int(1) not null default 0 comment '0 草稿 1 则相反',
     `page` int(1) not null default 0 comment '0 文章 1 页面',
    `reading` int(11) not null default 0 comment '浏览量',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;


#标签表
create table `sys_tag` (
    `id` int not null auto_increment COMMENT '自增ID',
    `name` varchar(255) not null default '' comment '标签名称',
    `arid` int not null  COMMENT  '文章ID',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;


ALTER table sys_tag ADD INDEX indexarid(arid);


#类型表
create table `sys_type` (
    `id` int not null auto_increment COMMENT '自增ID',
    `name` varchar(255) not null default '' comment '标签名称',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;


#类型表 文章映射表
create table `sys_type_and_article` (
    `id` int not null auto_increment COMMENT '自增ID',
    `tyid` int not null  COMMENT  '类型ID',
    `arid` int not null  COMMENT  '文章ID',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;


#博客评论表
create table `sys_comment` (
    `id` int not null auto_increment COMMENT '自增ID',
    `arid` int not null DEFAULT 0  COMMENT  '文章ID',
    `parent_id` int DEFAULT 0 COMMENT '楼中楼指定父节点',
    `name` varchar(64) not NULL DEFAULT ''  comment '提交人',
    `email` varchar(64) not NULL DEFAULT ''  comment '邮件地址',
    `content` TEXT   comment '评论内容',
    `is_author` int(1) not NULL DEFAULT 0  comment '是否作者评论',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

ALTER table sys_comment ADD INDEX indexarid(arid);
ALTER table sys_comment ADD INDEX indexparentid(parent_id);

#附件表
create table `sys_image_file` (
    `id` int not null auto_increment COMMENT '自增ID',
    `name` varchar(128) not null default '' comment '文件名称',
    `key_id` varchar(512) not null default '' comment '随机key',
    `path` varchar(512) not null default '' comment '七牛云域名路径',
     `url` varchar(512) not null default '' comment '显示缩略图路径',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
ALTER table sys_image_file ADD INDEX indexkey(key_id);














