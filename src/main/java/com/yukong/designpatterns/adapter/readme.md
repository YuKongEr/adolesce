>如果我们想让额定电压是直流12
伏特的笔记本在交流100伏特的AC电源下工作，通过我们需要一个AC适配器。
通过的，在程序世界中，经常会存在现用程序无法直接使用，需要做适当的变换之后才能使用。
这种用于填补"现用的程序"和"所需的程序"之间差异的设计模式就是`Adapter`模式

`Adapter`模式通常有两种实现方法
-  类适配器模式（使用继承的适配器）
-  对象适配器模式（使用委托的适配器）

|          | 电源的比喻  |                示例程序                |
| -------- | :---------: | :------------------------------------: |
| 实际情况 | 交流100伏特 | Banner类(showWithParen、showWithAster) |
| 变换装置 |   适配器    |             PrintBanner类              |
| 需求     | 直流12伏特  |   Print接口(printWeak、printStrong)    |



## 主要角色

### Target(对象)

该角色负责定义所需的方法，也就是实际需求。在我们的实际例子中`Print`就是扮演此对象，在电脑示例中，直流12伏特就是扮演此对象。



### Adaptee(被适配)

注意这里不是`Adapt-er`，而是`Adapt-ee`（被适配）角色。`Adaptee` 是一个持有既定方法的角色，在我们实际例子中`Banner`就是扮演此对象，在电脑示例中，交流100伏特就是扮演此对象。



### Adapter(适配)

`Adapter`模式的主人公，使用`Adapter`角色的方法来满足`Target`角色的需求，这就是`Adapter`模式的木器，也就是`Adapter`角色的作用，在我们实际例子中`PrintBanner`扮演此角色，在电脑示例中，AC适配器扮演此角色。

![extend](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/designpatterns/adapter/readme.assets/image-20181225145359358-5720839.png)

在类适配器模式，`Adapter`角色通过继承来使用`Adaptee`角色，在对象适配器模式中，`Adapter`角色通过委托来使用`Adaptee`角色

![entrust](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/designpatterns/adapter/readme.assets/image-20181225150114013-5721274.png)