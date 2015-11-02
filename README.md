MyBatis 代码生成器 (MBG)
=======================

[![Build Status](https://travis-ci.org/mybatis/generator.svg?branch=master)](https://travis-ci.org/mybatis/generator)
[![Coverage Status](https://coveralls.io/repos/mybatis/generator/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/generator?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/561964c6a193340f2800033c/badge.svg?style=flat)](https://www.versioneye.com/user/projects/561964c6a193340f2800033c)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis.generator/mybatis-generator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis.generator/mybatis-generator)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0)

![mybatis-generator](http://mybatis.github.io/images/mybatis-logo.png)

专为 MyBatis 和 iBATIS 设计的代码生成器.

本程序主要为各个版本的 MyBatis 生成代码, 同时也兼容 2.2.0 及以上的 iBATIS 版本. 

实现原理是解析数据库中的表(一或多张表), 然后生成相应的访问数据库的组件。

减少了MyBatis的大量初始化配置/映射任务。MBG的目标是生成最常见的CRUD操作(创建、检索、更新、删除; Create, Retrieve, Update, Delete)。



