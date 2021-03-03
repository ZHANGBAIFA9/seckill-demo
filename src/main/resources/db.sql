create table if not EXISTS t_user(
	`id` BIGINT(20) not null comment '用户id' ,
	`nickname` VARCHAR(255) not null ,
	`password` varchar(32) DEFAULT null comment 'md5(md5(pass明文+ 固定salt) + salt)',
	`salt` VARCHAR(10) default null ,
	`head` varchar(128) default null comment '头像' ,
	`register_date` datetime default null comment '注册时间' ,
	`last_login_date` datetime default null comment '最后一次登陆时间' ,
	`login_count` int(11) default '0' comment '登陆次数' ,
	primary key(`id`)
) comment '用户表'