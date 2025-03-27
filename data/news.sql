-- 创建新闻分类表
CREATE TABLE IF NOT EXISTS `news_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻分类表';

-- 创建新闻表
CREATE TABLE IF NOT EXISTS `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '新闻标题',
  `content` text COMMENT '新闻内容',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(40) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(40) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`),
  CONSTRAINT `fk_news_category` FOREIGN KEY (`category_id`) REFERENCES `news_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻表';

-- 插入新闻分类（如果name已存在则更新）
INSERT INTO `news_category` (`name`, `create_time`, `update_time`) 
VALUES ('category', '1111-11-11 11:22:33', '1111-11-11 11:22:33')
ON DUPLICATE KEY UPDATE 
  `create_time` = VALUES(`create_time`),
  `update_time` = VALUES(`update_time`);

-- 插入新闻数据（如果id已存在则更新）
INSERT INTO `news` (`id`, `title`, `content`, `category_id`, `create_time`, `update_time`, `created_by`, `updated_by`) 
VALUES (1, 'Title', 'text', 1, '1111-11-11 11:22:33', '1111-11-11 11:22:33', 'aaa', 'aaa')
ON DUPLICATE KEY UPDATE
  `title` = VALUES(`title`),
  `content` = VALUES(`content`),
  `category_id` = VALUES(`category_id`),
  `create_time` = VALUES(`create_time`),
  `update_time` = VALUES(`update_time`),
  `created_by` = VALUES(`created_by`),
  `updated_by` = VALUES(`updated_by`);