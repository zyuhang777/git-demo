CREATE TABLE `interface_info` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '接口id号',
                                  `name` varchar(256) NOT NULL COMMENT '接口名称',
                                  `url` varchar(256) NOT NULL COMMENT '接口请求地址',
                                  `description` varchar(512) NOT NULL COMMENT '接口信息描述',
                                  `requestHeader` varchar(512) NOT NULL COMMENT '接口请求头信息',
                                  `responsetHeade` varchar(512) NOT NULL COMMENT '接口相应头信息',
                                  `status` int(2) NOT NULL DEFAULT '0' COMMENT '接口提供状态码（0：不能使用1：可以使用默认为0）',
                                  `method` varchar(256) NOT NULL COMMENT '接口请求类型',
                                  `userId` bigint(20) NOT NULL COMMENT '接口创建者id',
                                  `isDelete` int(2) NOT NULL DEFAULT '0' COMMENT '接口是否删除（0：未删除  1：删除 默认为0）',
                                  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '接口创建时间',
                                  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '接口更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8