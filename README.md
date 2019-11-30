# 系统技术栈
- 客户端采用vue+elementUi 代理服务器使用nginx
- 服务端使用java8+mysql5.7+redis
- 搜索服务采用Elasticsearch 6.3.2
- 附件服务器采用云存储 七牛云
# 客户端搭建
## 下载源码
> 源码地址 [diaryClient](https://github.com/ygh1483345314/diaryClient)
## 运行客户端
>  安装node.js后 cd到根目录下 执行
>  npm install
>  npm run dev
>  http://localhost:8080 访问成功即可。
#  服务端配置
>  需要先安装mysql5.7+Elasticsearch 6.3.2+redis+rabbitmq 
## 表创建
> 创建数据库diary  执行项目文件中SQL.sql 文件中sql语句(一切以实际项目中sql文件微注,下文仅供参考) 注意 要把mysql中默认字符集改成utf8mb4 不然保存表情会报错.
```
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
```
## 下载源码
> 源码地址 [diaryServer](https://github.com/ygh1483345314/diaryServer)
## 修改application-dev.yml
> 修改mysql,redis,Elasticsearch,rabbitmq,邮箱,七牛云 账号密码
![配置.jpg](http://img.yeblog.club/6912b500ec3d451091728f8f2a9f7a8c)
![配置2.jpg](http://img.yeblog.club/b0912bd4655d4a079de94d385d4790bc)
![配置3.jpg](http://img.yeblog.club/b7c576b3c0cb4d16ab0c352f5e2c9942)
在 DiaryApplication.java 启动即可.
# API文档
> 项目使用了 swagger2 配置文件中如下,控制了api文档的开放与关闭
```yml
swagger2:
  enable: true #开发环境 开放API
```
访问页面 http://localhost:8031/api/swagger-ui.html
![文档.jpg](http://img.yeblog.club/0c523d820579453987560d42f53bdf90)
# 访问
> 前端: http://localhost:8080/
> 管理端 http://localhost:8080/#/admin
> 默认账号:root 密码:123456 
