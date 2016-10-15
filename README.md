# CheckUpdateLibrary
检查更新库

一个Android专用检查软件更新的库  
##效果图:  
非强制更新:  
![非强制更新](http://img.blog.csdn.net/20161015170249346?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  
强制更新:  
![强制更新](http://img.blog.csdn.net/20161015170311847?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
##简介:  
####该库的功能:  
    1,get/post方式检查更新,下载更新操作  
    2,有更新时,弹出升级对话框  
    3,可以根据各种参数设置是否强制更新,是否在通知栏显示进度条等,具体用法请参考demo  
    4,该库同时提供相关工具类,方便下载和安装软件等操作  
####该库的特点:    
1,零耦合:CheckUpdateLibrary从检查更新到下载更新全部采用HttpURLConnection,Dialog同样自定义,没有引入任何其他第三方库,而其他库要么是用的第三方网络请求框架,要么用的是别人封装好的dialog.所以开发者可能会吐槽,这只是一个单纯的检查更新和下载更新功能,我却需要多引入很多第三方库,这无形之中增加了我APK的体积,我很不爽!!!而用这个库,开发者完全不用担心这些.  
2,体积小:基于第一点,所以CheckUpdateLibrary库体积超小,只有10多kb  
3,兼容6.0:因为使用的是HttpURLConnection,所以6.0系统同样适配  
4,可扩展性:CheckUpdateLibrary内置了一个实体类CheckUpdateInfo,用来接收检查更新时返回的json数据,当然前提得和服务端商定好,对接好字段.而如果不想使用内置实体,开发者完全可以自定义自己的实体类,但是必须保证自定义的实体类中包含newAppVersionCode字段,该字段在库中用来判断是否有更新,所以必须包含,不然会检查不到是否有更新.目前CheckUpdateLibrary只支持实体类的扩展,Dialog还只能用它提供的.  
5,其他功能:比如可以自定义apk文件的存储路径,文件名,后台下载时是否在通知栏显示实时下载进度等.  

##用法:  
####Android Studio添加依赖:  
```groovy
 dependencies {  
    compile 'com.qiangxi.checkupdatelibrary:checkupdatelibrary:1.0.2' 
    }
```
####Eclipse:  
使用eclipse的小伙伴只能下载library文件,然后引入到项目中使用了 

####检查更新:    
```java
Q.checkUpdate("post", getVersionCode(), "checkUpdateUrl", new CheckUpdateCallback() {
            @Override
            public void onCheckUpdateSuccess(String result, boolean hasUpdate) {
                //result:服务端返回的json
                CheckUpdateInfo checkUpdateInfo = new Gson().fromJson(result, CheckUpdateInfo.class);
                //有更新,显示dialog等
                if (hasUpdate) {
                    //强制更新,这里用0表示强制更新,实际情况中可与服务端商定什么数字代表强制更新即可
                    if (checkUpdateInfo.getIsForceUpdate() == 0) {
                        //show ForceUpdateDialog
                    }
                    //非强制更新
                    else {
                        //show UpdateDialog
                    }
                } else {
                    //无更新,提示已是最新版等
                }
            }

            @Override
            public void onCheckUpdateFailure(String failureMessage, int errorCode) {
                //failureMessage:一般为try{}catch(){}捕获的异常信息
                //errorCode:暂时没什么用
            }
        });
```  

####强制更新对话框:  
```java
ForceUpdateDialog dialog = new ForceUpdateDialog(MainActivity.this);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                    .show();
```  
####非强制更新对话框:  

```java
UpdateDialog dialog = new UpdateDialog(MainActivity.this);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                    //该方法需设为true,才会在通知栏显示下载进度,默认为false,即不显示
                    //该方法只会控制下载进度的展示,当下载完成或下载失败时展示的通知不受该方法影响
                    //即不管该方法是置为false还是true,当下载完成或下载失败时都会在通知栏展示一个通知
                    .setShowProgress(true)
                    .setIconResId(R.mipmap.ic_launcher)
                    .setAppName(mCheckUpdateInfo.getAppName())
                    .show();
```  
##Issues
各位小伙伴使用过程中有任何问题或发现什么bug或有什么好的意见或建议,请通过上面的issue提交给我,谢谢啦~~
