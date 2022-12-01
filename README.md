# iot-modbus

#### 介绍
物联网通讯协议，基于netty框架，支持COM（串口）和TCP协议，支持服务端和客户端两种模式，实现Java控制智能设备，同时支持设备组多台设备高并发通讯。采用工厂设计模式，代码采用继承和重写的方式实现高度封装，可作为SDK提供封装的接口，让具体的业务开发人员无需关心通讯协议的底层实现，直接调用接口即可使用。实现了心跳、背光灯、扫码、刷卡、指静脉、温湿度和门锁（支持多锁）、LCD显示屏等指令控制、三色报警灯控制。代码注释丰富，包括上传和下发指令调用例子，非常容易上手。

#### 版本说明
1.  V1.0.0版本仅支持TCP服务端通讯模式；
2.  V2.0.0版本支持TCP服务端和客户端两种模式，客户端模式还增加了心跳重连机制。
3.  V3.0.0版本支持COM（串口）和TCP协议，增加logback日志按文件大小和时间切割输出。
4.  V3.1.0版本代码优化，抽取公共模块子工程。
5.  V3.2.0版本TCP通讯增加支持LCD显示屏控制指令，支持批量控制LCD显示屏。
6.  V3.2.1版本串口通讯增加支持LCD显示屏控制指令，支持批量控制LCD显示屏。
7.  V3.2.2版本串口通讯接收指令数据拆包处理代码优化，网口通讯增加支持三色报警灯控制指令。
8.  V3.2.3版本串口通讯增加支持三色报警灯控制指令，串口通讯接收指令数据拆包处理代码优化。
9.  V3.2.4版本使用netty集成Rxtx对串口数据进行数据拆包处理，并且对指静脉指令进行优化。
10. V3.2.5版本客户端模式支持同时连接多个服务端下发和接收指令数据。

#### 软件架构
软件架构说明
基础架构采用Spring Boot2.x + Netty4.X + Maven3.6.x，日志采用logback。

#### 安装教程

1.  系统Windows7以上；
2.  安装Jdk1.8以上；
2.  安装Maven3.6以上；
3.  代码以Maven工程导入Eclipse或Idea。

#### 使用说明

1.  工程结构说明：
- iot-modbus                //物联网通讯父工程
- ├── doc                   //文档管理
- ├── iot-modbus-client     //netty通讯客户端
- ├── iot-modbus-common     //公共模块子工程
- ├── iot-modbus-netty      //netty通讯子工程
- ├── iot-modbus-serialport //串口通讯子工程
- ├── iot-modbus-server     //netty通讯服务端
- ├── iot-modbus-test       //使用样例子工程
- └── tools                 //通讯指令调试工具
2.  配置文件查看iot-modbus-test子工程resources目录下的application.yml文件；
3.  启动文件查看iot-modbus-test子工程App.java文件；
4.  服务启动后，服务端端口默认为：8080，网口通讯端口默认为：4000，串口通讯默认串口为：COM1；
5.  通讯指令调试工具，TCP通讯模式使用tools目录下的NetAssist.exe，串口通讯模式使用tools目录下的UartAssist.exe；
6.  通讯指令采用Hex编码（十六进制）；
7.  串口通讯依赖文件查看doc/serialport目录，Windows环境下rxtxParallel.dll和rxtxSerial.dll文件拷贝到JDK安装的bin目录下，Linux环境将librxtxParallel.so和librxtxSerial.so文件拷贝到JDK安装的bin目录下；
8.  postman指令下发样例请查看doc/postman/通讯指令下发.postman_collection.json文件，包括门锁（单锁/多锁）、扫码、背光灯、LCD显示屏、三色报警灯指令。

#### 指令格式

指令被网站误认为内容可能含有违规信息无法展示，更多详细内容，请查看doc目录下的《iot-modbus开放指南.pdf》文档。

#### 创作不易，别忘了点亮Star，你们的支持，是我源源不断的动力。

#### 欢迎加入交流群

- QQ群
- ![输入图片说明](doc/picture/9%E7%89%A9%E8%81%94%E7%BD%91%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE%EF%BC%88iot-modbus%EF%BC%89%E4%BA%A4%E6%B5%81%E7%BE%A4%E7%BE%A4%E4%BA%8C%E7%BB%B4%E7%A0%81.png)
