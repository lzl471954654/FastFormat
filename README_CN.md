[简体中文](https://github.com/lzl471954654/FastFormat/blob/master/README_CN.md) | [English](https://github.com/lzl471954654/FastFormat/blob/master/README.md)

# FastFormat

FastFormat 是一个用来替代 **String.format()** 的函数，兼容库函数的用法，并且速度比库函数更快、比库函数占用更少的内存。

## 用法

1. 下载 FormatUtils.kt , 或者将源码拷贝到工程目录中
2. 替换掉工程中的 String.format() 函数 （部分不支持的特性仍需使用库函数，具体支持的特性在下面）


### 库函数用法

```java

String.format(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)

```

### fastFormat 用法

```java

fastFormat(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)

```

## 支持的特性

完全兼容库函数的调用格式

>   %[index$][标识]*[最小宽度][.精度]转换符

![](https://github.com/lzl471954654/FastFormat/blob/master/features_cn.jpg?raw=true)

### 标识

|标识|描述|
|---|---|
|-|在最小宽度内左对齐,不可以与0标识一起使用|
|0|若内容长度不足最小宽度，则在左边用0来填充|
|#|对8进制和16进制，8进制前添加一个0,16进制前添加0x|

目前部分库函数的特性还不支持，**但是目前已经支持的特性是非常常用的，使用频次高的，不支持的特性通常使用的非常的少**，所以暂时没有支持

**但是后续会考虑加入库函数中的其他特性**

如果你认为有哪些特性非常的必要，可以提issues



## 性能测试

### 测试环境

PC


> CPU ：i7 7700

> Memory ： 16G DDR4 2400Mhz

> Disk ： Samsung PM981 256G

> System ：Windows 10 1803

> IDE  ：Idea 2018.2

> JDK  ： JDK 1.8.0_181

> Kotlin ：1.2.51-release

> Memory tools：jvisualvm

Android Phone

> Phone Type：XiaoMi 4C

> CPU：Snapdragon 808 （Six cores 1.8 GHZ）

> Memory：3GB Double Channels LPDDR3 

> Rom：32GB  eMMC 5.0

> NetWork：WIFI

> System：Android 7.0 ，MIUI 10

**PC Test**

耗时 , 100000 次循环

![](https://github.com/lzl471954654/FastFormat/blob/master/speed.jpg?raw=true)

内存使用

![](https://github.com/lzl471954654/FastFormat/blob/master/memory_all.jpg?raw=true)

创建的对象数量

![](https://github.com/lzl471954654/FastFormat/blob/master/object_count.jpg?raw=true)


**Android Test**

耗时 , 1000 次循环

![](https://github.com/lzl471954654/FastFormat/blob/master/android_speed.jpg?raw=true)
