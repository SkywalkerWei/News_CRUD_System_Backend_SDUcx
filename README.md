# 后端代码仓库：概述

本项目为山东大学崇新学堂2025年春季开放性创新实践1课程软件开发部分相关代码仓库，本仓库为后端部分的代码。

## 技术栈

- JDK 17
- 核心框架 : Spring Boot 2.7.x （基于build.gradle中的spring-boot-dependencies配置）
- 数据持久层 : MyBatis-Plus 3.5.3.1 （结合mybatis-generator-core实现ORM）
- 安全框架 : Spring Security 5.7.x （通过security-test模块验证）
- API文档 : SpringDoc OpenAPI 1.6.14 （集成swagger-ui）
- 构建工具 : Gradle 8.1.3 （Wrapper配置验证）

### 接口层(Controller)

- 基于Spring MVC实现RESTful API
- 统一异常处理

### 业务层(Service)

- 事务管理@Transactional
- 业务逻辑组合

### 持久层(DAO)

- MyBatis动态SQL
- 分页插件配置

## 实现功能

### 新闻管理功能

- 新闻的CRUD操作接口
- 支持按标题、栏目、创建时间等条件组合查询
- 添加了OpenAPI 3文档注解，便于API文档生成

### 栏目管理功能

- 实现了栏目的CRUD操作接口
- 添加了栏目名称唯一性检查
- 实现了栏目删除时的新闻迁移功能
- 支持按栏目名称模糊查询

### 权限校验功能

- 只有授权的管理员可以修改、新建、删除项
- 一般用户只能搜索和查看内容

## 更新日志

### 2025-04-02

删除了大量没有被使用的冗余代码，删除了大量弃用的模块，优化代码结构

### 2025-04-07

BugFix：修复了管理员创建新用户时，选择特定权限后导致报错无法新建用户的问题