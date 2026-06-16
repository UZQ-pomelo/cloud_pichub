# 🖼️ 智能协同云图库

> 集成 AI 图像处理与多人实时协同编辑的云端图片管理平台

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.6-brightgreen?style=flat&logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)](https://www.java.com/)
[![MyBatis Plus](https://img.shields.io/badge/MyBatis%20Plus-3.5.9-blue?style=flat)](https://baomidou.com/)
[![ShardingSphere](https://img.shields.io/badge/ShardingSphere-5.2.0-red?style=flat)](https://shardingsphere.apache.org/)
[![Sa-Token](https://img.shields.io/badge/Sa--Token-1.39.0-ff69b4?style=flat)](https://sa-token.cc/)
[![Redis](https://img.shields.io/badge/Redis-缓存-red?style=flat&logo=redis)](https://redis.io/)
[![WebSocket](https://img.shields.io/badge/WebSocket-实时协作-9cf?style=flat)](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)
[![腾讯云COS](https://img.shields.io/badge/存储-腾讯云COS-blue?style=flat)](https://cloud.tencent.com/product/cos)
[![阿里云AI](https://img.shields.io/badge/AI-阿里云DashScope-purple?style=flat)](https://dashscope.aliyun.com/)
[![Knife4j](https://img.shields.io/badge/API文档-Knife4j-brightgreen?style=flat)](https://doc.xiaominfo.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat)](LICENSE)

---

## ✨ 核心特性

### 🤖 AI 图像处理
- **AI 智能扩图（Outpainting）**：接入阿里云 DashScope AI，自动扩展图片边界，生成合理的内容填充
- **以图搜图**：自研图片相似度搜索，上传图片即可找到图库中的相似图片
- **主色调提取**：上传时自动分析图片主色调，支持按颜色搜索

### 👥 多人实时协同编辑
- 基于 **WebSocket** 的实时通信，同一张图片可由多人同时在线观看
- **编辑锁机制**：同一时间仅一人可编辑，其余用户实时看到编辑者的操作广播
- 用户进入/退出编辑状态时自动通知所有在线协作者

### 🏢 团队空间管理
- **私有空间**：每个用户拥有独立的图片存储空间
- **团队空间**：支持创建协作空间，邀请成员加入
- **三级角色**：`viewer`（查看者）/ `editor`（编辑者）/ `admin`（管理员），细粒度权限控制
- **空间等级**：普通版 / 专业版 / 旗舰版，不同等级拥有不同的存储容量和图片数量上限

### 📊 空间分析
- **使用概览**：空间总大小、图片数量、使用比例一目了然
- **分类分析**：按图片分类统计占比
- **标签分析**：按标签分布统计
- **大小分布**：按图片文件大小区间统计
- **成员分析**：团队成员上传贡献排名
- **空间排名**：所有空间按使用量排行

### 🔐 权限与安全
- 基于 **Sa-Token** 的 RBAC 权限认证
- 用户角色：普通用户 / 管理员
- 图片审核工作流：待审核 → 审核通过 / 审核拒绝
- **Spring Session + Redis** 分布式会话支持

### ⚡ 技术亮点
- **ShardingSphere 分库分表**：按空间 ID 动态分表，支持海量图片存储
- **多级缓存**：Caffeine 本地缓存 + Redis 分布式缓存，减少数据库压力
- **腾讯云 COS**：图片文件存储在腾讯云对象存储，高可用低成本

---

## 🛠️ 技术栈

| 类别 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 基础框架 | Spring Boot | 2.7.6 | 主框架 |
| 开发语言 | Java | 17 | - |
| 数据访问 | MyBatis Plus | 3.5.9 | ORM + 分页 |
| 分库分表 | ShardingSphere | 5.2.0 | 按空间动态分表 |
| 权限认证 | Sa-Token | 1.39.0 | RBAC 权限 |
| 缓存 | Redis + Caffeine | - | 分布式 + 本地两级缓存 |
| 会话管理 | Spring Session + Redis | - | 分布式会话 |
| 对象存储 | 腾讯云 COS | 5.6.227 | 图片文件存储 |
| AI 服务 | 阿里云 DashScope | - | AI 图像扩图 |
| 实时通信 | Spring WebSocket | - | 多人协同编辑 |
| 接口文档 | Knife4j | 4.4.0 | Swagger 增强文档 |
| 工具库 | Hutool | 5.8.26 | 通用工具 |
| HTML 解析 | Jsoup | 1.15.3 | 爬虫抓取图片 |

---

## 📁 项目结构

```
src/main/java/com/pichub/cloud_pichub/
├── api/                    # 外部 API 集成
│   ├── aliyunai/          # 阿里云 DashScope AI（扩图）
│   └── imagesearch/       # 以图搜图服务
├── manager/
│   ├── auth/              # Sa-Token 权限管理器
│   ├── sharding/          # 自定义分表算法（按 spaceId 动态路由）
│   └── websocket/         # WebSocket 协同编辑处理器
├── controller/            # REST 接口层（Picture/Space/SpaceAnalyze/User 等）
├── service/               # 业务逻辑层
├── mapper/                # MyBatis 数据访问层
├── model/
│   ├── entity/            # 数据库实体
│   ├── dto/               # 请求/响应 DTO
│   ├── vo/                # 视图对象
│   └── enums/             # 枚举类
├── config/                # 配置类（COS/CORS/MyBatis-Plus 等）
├── aop/                   # 切面编程
├── annotation/            # 自定义注解（权限校验等）
├── common/                # 公共工具类（统一响应、异常处理）
├── constant/              # 常量定义
└── exception/             # 自定义异常
```

---

## 🚀 快速开始

### 环境要求

- **JDK** 17+
- **MySQL** 5.7+
- **Redis** 5.0+
- **Maven** 3.6+
- **腾讯云 COS**（对象存储，需注册开通）
- **阿里云 DashScope**（AI 扩图功能，需申请 API Key）

### 本地启动

1. **克隆项目**

```bash
git clone https://github.com/UZQ-pomelo/cloud_pichub.git
cd cloud_pichub
```

2. **初始化数据库**

执行 `sql/` 目录下的建表脚本：

```bash
mysql -u root -p < sql/create_table.sql
```

> 数据库名为 `cloud_pichub`，使用 UTF-8 编码（`utf8mb4`）。

3. **配置 `application-local.yml`**

在 `src/main/resources/` 下创建 `application-local.yml`，填入以下配置（模板参考 `application-local.yml.example`）：

```yaml
# 腾讯云 COS 对象存储
cos:
  client:
    secretId: <你的 SecretId>
    secretKey: <你的 SecretKey>
    region: ap-guangzhou        # 你的 COS 区域
    bucket: <你的桶名>
    host: https://<你的CDN域名>

# 数据库
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cloud_pichub
    username: <你的数据库用户名>
    password: <你的数据库密码>

# 阿里云 AI（可选，不需要 AI 扩图可留空）
aliYunAi:
  apiKey: <你的 DashScope API Key>
```

4. **启动项目**

```bash
mvn spring-boot:run
```

启动后访问：
- **接口文档**：`http://localhost:8123/api/doc.html`
- **API 前缀**：`http://localhost:8123/api`

---

## 📋 核心模块说明

### 图片管理 (`PictureController`)
- 支持单张上传、批量抓取上传（配合 Jsoup 爬取远程图片）
- 图片编辑、标签管理、分类管理
- 审核流程：审核通过/拒绝，记录审核人和审核信息
- 自动生成缩略图
- 支持按颜色搜索（基于上传时提取的主色调）

### 空间系统 (`SpaceController`)
- 创建私有空间 / 团队空间
- 管理员可更新空间等级和限额
- 空间等级枚举：普通版 / 专业版 / 旗舰版（不同 maxSize / maxCount）

### 空间分析 (`SpaceAnalyzeController`)
- 使用概览、分类统计、标签统计、大小分布、成员贡献排名、空间排行

### 协同编辑（WebSocket）
- 连接端点由 `PictureEditHandler` 处理
- 握手拦截器 `WsHandshakeInterceptor` 在连接建立前校验用户身份和图片权限
- 编辑状态由 `ConcurrentHashMap` 内存管理，支持水平扩展时可通过 Redis 改造

### 分表策略
- 自定义 `PictureShardingAlgorithm`，按 `spaceId` 动态路由
- `spaceId = null` → 写入公共图库基础表 `picture`
- `spaceId = 100` → 写入分表 `picture_100`
- 业务层通过 `SpaceService` 在创建空间时动态 `CREATE TABLE picture_{spaceId}`

---

## 📄 License

本项目采用 [MIT License](LICENSE) 开源协议。

---

*如果这个项目对你有帮助，欢迎 Star ⭐️*
