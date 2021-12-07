# iot-modbus

#### 介绍
物联网通讯协议，使用netty通讯实现java控制智能设备。服务端采用TCP协议，同时支持设备组多台设备高并发通讯。采用工厂设计模式，代码采用继承和重写的方式实现高度封装，可作为SDK使用。实现了心跳、背光灯、扫码、刷卡、指静脉、温湿度和门锁（支持多锁）等指令控制。代码注释丰富，包括上传和下发指令调用例子，非常容易上手。

#### 软件架构
软件架构说明
基础架构采用Spring Boot2.x + Netty4.X + Maven3.6.x，日志采用logback。

#### 安装教程

1.  安装Jdk1.8以上；
2.  安装Maven3.6以上；
3.  代码以Maven工程导入Eclipse或Idea。

#### 使用说明

1.  工程结构说明：
- iot-modbus                //物联网通讯父工程
- ├── doc                   //文档管理
- ├── iot-modbus-netty      //netty通讯子工程
- └── iot-modbus-test       //使用样例子工程
2.  配置文件查看iot-modbus-test子工程resources目录下的application.yml文件；
3.  启动文件查看iot-modbus-test子工程App.java文件；
4.  服务启动后，服务端端口默认为：8080，netty通讯端口默认为：4000；

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

#### 创作不易，别忘了点亮Star
