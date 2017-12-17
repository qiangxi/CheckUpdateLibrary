# CheckUpdateLibrary
检查更新库 

#### 重构后具有的功能

本库已经从代码的架构、设计、性能、UI等方面进行彻底的重构，但由于日常工作**极其**繁重，导致整个开发进度严重拖延，本来设计好的一些功能因为时间问题一直处于停滞状态，但幸运的是项目也有一些进展，目前重构后的项目具有以下功能：  
- 具有之前老版本代码的所有功能（1.1.3及之前版本，对应的README见[这里](https://github.com/qiangxi/CheckUpdateLibrary/blob/master/1.1.3%E5%8F%8A%E4%B9%8B%E5%89%8D%E7%89%88%E6%9C%AC%E7%9A%84Readme.md)）
- 根据[#24](https://github.com/qiangxi/CheckUpdateLibrary/issues/24)的需求，关键地方加了Log，并可以控制Log的开关
- 如果使用lib中自带的CheckUpdateDialog，权限管理不用用户操心了，因为lib内部已经做好了
- 使用DialogFragment替换之前的dialog，避免因横竖屏切换导致dialog消失等一系列问题
- 强制更新Dialog与非强制更新Dialog现在采用同一个CheckUpdateDialog，通过CheckUpdateOption配置相关属性
- 更新CheckUpdateDialog的UI，现在可以配置顶部图片（毕竟要跟上设计的步伐）
- 扩展了RuntimeException，这样在遇到一些异常时，可以明确知道哪里出了问题，方便定位

#### 设计好但没来得及写的一些功能：
- 编译时注解（通过配置注解，就可以实现检查更新，下载更新，安装更新的功能）

#### 使用新库时需注意的事情
- 新库不兼容旧版本代码，所以想使用新库的考虑一下替换成本哈（理论上替换成本不高）
- 现在还有一些功能未完成（编译时注解），所以就没上传jCenter，想使用新库的同学目前只能下载项目，然后作为module使用了

#### 使用方式
具体使用方式见sample吧，很简单，如果有不懂的，就提issue（其实就是懒，不想写...）。

#### 新库的项目结构图及UI草图
![项目结构图](https://github.com/qiangxi/CheckUpdateLibrary/blob/master/image/CheckUpdate%E6%9E%B6%E6%9E%84.png?raw=true)  
![dialog UI草图](https://github.com/qiangxi/CheckUpdateLibrary/blob/master/image/%E6%9B%B4%E6%96%B0Dialog.png?raw=true)



