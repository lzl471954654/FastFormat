# FastFormat

FastFormat is a function to replace **String.format()** , FastFormat compatible the libaray function usage, but use less memory, and faster than libaray function.


## Usage

1. Download the FormatUtils.kt , or Copy the code into your project
2. Invoke the function , or replace the String.format()


### Libaray Usage

```java

String.format(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)

```

### fastFormat Usage

```java

fastFormat(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)

```

## Support Features

Completely compatible the libaray funtion format

> %[index$][Flag]*[width][.precision]ConvertChar

![](https://github.com/lzl471954654/FastFormat/blob/master/features_english.jpg?raw=true)

### Flag

|Flag|description|
|---|---|
|-|align left|
|0|If the content length is less than the minimum width, fill it with 0 on the left|
|#|For base Octal and base Hexadecimal, add a 0 before base Octal, and a 0x before base Hexadecimal|

But there are somes features are not supported now , these features are not usually used , in the future some features will be added .

If you think some features are inneed , please create a issue.



## Performance

### Test environment

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

speed , 100000 tests

![](https://github.com/lzl471954654/FastFormat/blob/master/speed.jpg?raw=true)

Memory

![](https://github.com/lzl471954654/FastFormat/blob/master/memory_all.jpg?raw=true)

Object Count

![](https://github.com/lzl471954654/FastFormat/blob/master/object_count.jpg?raw=true)


**Android Test**

speed , 1000 tests

![](https://github.com/lzl471954654/FastFormat/blob/master/android_speed.jpg?raw=true)
