## J-HttpClient

[![Fork me on Gitee](https://gitee.com/qianyi-community/J-HttpClient/widgets/widget_4.svg)](https://gitee.com/qianyi-community/J-HttpClient)

![JAVA](https://img.shields.io/badge/JAVA-17+-green.svg)
[![star](https://gitee.com/qianyi-community/J-HttpClient/badge/star.svg?theme=dark)](https://gitee.com/qianyi-community/J-HttpClient/stargazers)
[![fork](https://gitee.com/qianyi-community/J-HttpClient/badge/fork.svg?theme=dark)](https://gitee.com/qianyi-community/J-HttpClient/members)
[![star](https://img.shields.io/github/stars/jiangqiang1996/J-HttpClient?style=social)](https://github.com/jiangqiang1996/J-HttpClient/stargazers)
[![fork](https://img.shields.io/github/forks/jiangqiang1996/J-HttpClient?style=social)](https://github.com/jiangqiang1996/J-HttpClient/network/members)
[![千异社区/J-HttpClient](https://gitee.com/qianyi-community/J-HttpClient/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/qianyi-community/J-HttpClient)

很久以前利用业余时间开发的一个http请求客户端，如果有时间就继续维护下吧。

#### 目前支持的特性:
1. 支持同步或异步请求
2. 支持修改请求报文的任意位置,如果你对协议结构足够熟悉
3. 支持提交cookie参数,获取cookie参数
4. 支持HTTP中定义的多种请求方式(GET,PUT,DELETE,POST)
5. 支持表单提交,JSON提交,文件参数提交等多种提交方式,文件参数已经支持多文件上传
6. 支持请求拦截器和响应拦截器

#### 计划未来支持的功能:
1. 支持HTTPS协议
2. 支持代理

###### 关于项目
纯粹是为了学习http协议而开发的玩具，我自己也没想到能实现大部分基础功能。其实如果你对http协议了解够深入，足够打脸很多面试官。
很多面试官经常面试会问到http协议的问题，事实上他们对http协议也是一知半解，甚至把tcp的握手和挥手与http混为一谈。
事实上，我阅读了http标准文档，也查阅了很多资料，并没有哪一句明确地规定http就应该是基于tcp实现的，
所以，你完完全全可以使用udp去实现他，然而，客户端和服务端的传输层协议必须是一致的，所以即使你使用udp去实现，也需要对应的服务端支持才行。
目前最常见的客户端（浏览器，移动端软件，pc联网软件），以及常见的服务端（nginx，apache，IIS）等等都是基于tcp的。
所以目前为止，http基本上都是基于tcp协议实现而已，但是保不准未来某一天，http就会基于udp实现。
写这么多，我只是想告诉大家，不要把http和tcp混为一谈，所谓的握手和挥手，跟他没有半毛钱关系。
最后，希望大家通过此项目，从报文结构了解http协议。