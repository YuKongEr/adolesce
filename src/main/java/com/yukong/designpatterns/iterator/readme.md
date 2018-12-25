-  `Aggregate` 表示集合的jiek
-  `Iterator` 遍历集合的接口
-  `Book`  书本类
-  `BookShelf`  书架类
-  `BookShelfIterator` 书架迭代器类
-  `Main` 测试类


## 主要角色
### Iterator(迭代器)
该角色负责定义按顺序逐个遍历元素的接口。在示例程序中`Iterator`扮演这个角色，它定义了两个方法
hasNext方法用于判断是否还有下一个元素，next方法用于获取下一个元素。

### ConcreteIterator(具体的迭代器)
该角色负责实现Iterator角色所定义的接口，在示例程序中`BookShelfIterator`类扮演这个角色，该角色
包含了遍历集合所必须的信息，在示例程序中,`BookShelf`类保存在booShelf字段中，被指向的书的下标保存在index字段中

### Aggregate(集合)
该角色负责定义创建`Iterator`角色的接口，这个接口是一个方法，会创建出'顺序方法保存在我内部元素的人'

### ConcreteAggregate(具体的集合)
该角色负责实现`Aggregate`接口


## 思路要点
为什么要引入`Iterato`r这种复杂的设计模式呢？，如果是数组，直接使用for循环不就可以了吗？为什么要在集合
之外引入`Iterator`这个角色呢？

重要的原因是，引入`Iterator`后可以将遍历与实现分离。
```java
 while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book.getName());
        }
```
这里使用了`Iterator`的hasNext方法和next方法，并没有调用`BookShelf`的方法，也就是说，**这里的while循环并不依赖于BookShelf的实现**
如果`BookShelf`的内部实现从数组改成`java.util.Vector` 我们都不需要对上面的while循环做任何的修改。



![image-20181225140411103](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/designpatterns/iterator/readme.assets/image-20181225140411103-5717851.png)
