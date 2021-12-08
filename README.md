# iot-modbus

#### 介绍
物联网通讯协议，使用netty通讯实现java控制智能设备。服务端采用TCP协议，同时支持设备组多台设备高并发通讯。采用工厂设计模式，代码采用继承和重写的方式实现高度封装，可作为SDK提供封装的接口，让具体的业务开发人员无需关心通讯协议的底层实现，直接调用接口即可使用。实现了心跳、背光灯、扫码、刷卡、指静脉、温湿度和门锁（支持多锁）等指令控制。代码注释丰富，包括上传和下发指令调用例子，非常容易上手。

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
- ├── iot-modbus-test       //使用样例子工程
- └── tools                 //通讯指令调试工具
2.  配置文件查看iot-modbus-test子工程resources目录下的application.yml文件；
3.  启动文件查看iot-modbus-test子工程App.java文件；
4.  服务启动后，服务端端口默认为：8080，netty通讯端口默认为：4000；
5.  通讯指令调试工具，查看tools目录下的NetAssist.exe；
6.  通讯指令采用Hex编码（十六进制）。

#### 指令格式

1.  以心跳指令（7E 04 00 BE 01 00 00 74 77 7F）作为样例说明，下标从0开始；
2.  第0位为起始符，长度固定占1个字节，固定格式：7E；
3.  第1、2位为数据长度，计算方法是从命令符到数据位（即：从3位到指令长度-3位），长度固定占2个字节，例如：04 00，表示长度为4；
4.  第3位为指令符，长度固定占1个字节，例如：BE，表示心跳指令；
5.  第4位为设备号，长度固定占1个字节，例如：01，表示设备号为1；
6.  第5位为层地址，长度固定占1个字节，例如：00，表示设备所有的层不执行；
7.  第6位为槽地址，长度固定占1个字节，例如：00，表示设备所有的槽不执行；
8.  指令长度-3位到-2位为校验位，采用CRC16_MODBUS（长度,命令,地址,数据）校验，例如：74 77，详细查看：ModbusCrc16Utils.java工具类；
9.  末位为结束符，长度固定占1个字节，固定格式：7F。

#### 通讯指令

1.  心跳上传指令
- iot-modbus作为服务端，通过心跳来维持通讯，启动服务端后，打开NetAssist.exe指令调试工具，先往服务端发送心跳指令；
- 硬件往服务端发送：7E 04 00 BE 01 00 00 74 77 7F ，为必要指令。
2.  背光灯上传指令
- 硬件往服务端发送：7E 05 00 88 01 00 00 00 AF E3 7F 
3.  扫码指令下发
- 服务端往硬件下发：7E 05 00 08 01 00 00 01 6F FD 7F 
- 第7位为数据位，长度固定占1个字节，例如：01，表示开开启扫码头。
4.  扫码指令上传
- 硬件往服务端发送：7E 24 00 8F 01 00 00 03 45 30 30 34 30 31 30 38 32 38 30 32 41 36 39 33 0D 02 00 00 01 02 13 73 02 00 00 01 02 13 73 9B 79 7F
- 数据为：03 45 30 30 34 30 31 30 38 32 38 30 32 41 36 39 33 0D 02 00 00 01 02 13 73 02 00 00 01 02 13 73为条码信息。
5.  刷卡指令上传
- 硬件往服务端发送：7E 08 00 84 01 00 00 86 14 AE 02 7C 53 7F 
- 数据位：86 14 AE 02为卡号信息。
6.  单开锁下发指令
- 服务端往硬件下发：7E 05 00 03 01 00 00 01 CA 3C 7F
- 第7位为数据位，长度固定占1个字节，例如：01，表示开1号锁。
7.  多开锁下发指令
- 服务端往硬件下发：7E 08 00 03 FF FF FF 01 00 02 01 7F B0 7F 
- FF FF FF为指令做兼容填补位，后面 01 00 02 01是数据位，其中：01表示1号锁，00表示上锁；02表示2号锁，01表示开锁。
8.  锁状态上传指令
- 硬件往服务端发送：7E 0D 00 83 01 00 00 FF FF FF 01 00 05 02 00 01 EE 99 7F 
- FF FF FF为指令做兼容填补位，后面 01 00 05 02 00 01是数据位，其中：01表示1号锁，00表示上锁，05表示传感器状态码；02表示2号锁，00表示上锁，01表示传感器状态码。
9. 指静脉和温湿度指令（不作说明，详细查看代码）。

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

#### 创作不易，别忘了点亮Star，你们的支持，是我源源不断的动力。