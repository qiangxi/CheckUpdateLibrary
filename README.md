# CheckUpdateLibrary
检查更新库 
### 更新日志v1.1.0(2017-03-17):
- 修复使用1.0.8和1.0.9版本安装应用失败的bug,错误提示为"Installation failed with message INSTALL_FAILED_CONFLICTING_PROVIDER".  
#### 安装失败的原因:
因为v1.0.8和v1.0.9为了兼容7.0使用了provider,其中在provider中有个android:authorities属性,该属性的值对于不同的应用来说必须要有不同的值,在v1.0.8和v1.0.9中, android:authorities的值都必须是"com.qiangxi.checkupdatelibrary",这种做法就造成如果A应用使用了这种配置且先安装到设备中,当B应用也使用这种配置再进行安装时,就会安装失败并报上面那个错,提示provider冲突,解决方式就是为不同的应用提供不同的android:authorities属性值,而区分不同应用的根本方式就是应用的包名,所以在v1.1.0版本中,每个应用使用自己的包名作为android:authorities属性的属性值即可.  
#### provider新的配置代码:
```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="your packageName"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/checkupdatelibrary_uri_grant"/>
</provider>
```
### 更新日志v1.0.9(2017-03-15):
- 修复当设备为6.0以上时,点击立即更新按钮没反应的bug
- 优化内置dialog关于更新日志TextView的内容过多时,可以上下滑动的体验
- 统一资源命名规范
### 更新日志v1.0.8(2017-02-27):
- 兼容Android 7.0,修复7.0严格模式下安装应用崩溃的bug(兼容性bug)  

如果您的app兼容Android 7.0,需要在您主项目的manifest.xml文件中额外添加以下代码(对provider的声明):  
```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="com.qiangxi.checkupdatelibrary"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/checkupdatelibrary_uri_grant"/>
</provider>
```
其中,checkupdatelibrary_uri_grant文件已经在library中配置完毕,无需再额外配置.  
### 更新日志v1.0.7(2017-01-17):  
- 优化无数据时,内置Dialog的界面显示  
- 优化下载过程中,message的获取方式,更加节省性能  

---

一个Android专用检查软件更新的库  
## 效果图:  
非强制更新:  
![非强制更新](http://img.blog.csdn.net/20161015170249346?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  
强制更新:  
![强制更新](http://img.blog.csdn.net/20161015170311847?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  

## 简介:  
#### 该库的功能:  
- get/post方式检查更新,下载更新操作  
- 有更新时,弹出升级对话框  
- 可以根据各种参数设置是否强制更新,是否在通知栏显示进度条等,具体用法请参考demo  
- 该库同时提供相关工具类,方便下载和安装软件等操作  

#### 该库的特点:  
1,零耦合:CheckUpdateLibrary从检查更新到下载更新全部采用HttpURLConnection,Dialog同样自定义,没有引入任何其他第三方库,而其他库要么是用的第三方网络请求框架,要么用的是别人封装好的dialog.所以开发者可能会吐槽,这只是一个单纯的检查更新和下载更新功能,我却需要多引入很多第三方库,这无形之中增加了我APK的体积,我很不爽!!!而用这个库,开发者完全不用担心这些.  
2,体积小:基于第一点,所以CheckUpdateLibrary库体积超小,只有10多kb  
3,兼容6.0:因为使用的是HttpURLConnection,所以6.0系统同样适配.另外,在1.0.4版本中,全面兼容了6.0的运行时权限,后面会详细介绍  
4,可扩展性:CheckUpdateLibrary具有超强扩展性,下面会单独一个标题细说.  
5,其他功能:比如可以自定义apk文件的存储路径,文件名,后台下载时是否在通知栏显示实时下载进度等.  
## 兼容6.0运行时权限  
**首先明确一点,如果您的app只兼容到了sdk 22,即5.1及以前的版本,可以忽略这一小节不看,直接看下一小节"扩展性".**  
对于sdk 23,即6.0版本,CheckUpdateLibrary在1.0.4版本进行了完全的适配.所以在使用CheckUpdateLibrary的时候需要注意使用方式.而需要获取的权限为读写SD卡的权限,这个权限在我们进行下载的时候会用到,所以在使用到CheckUpdateLibrary提供的两个默认对话框ForceUpdateDialog和UpdateDialog时,在点击更新按钮之后,会触发权限的获取操作,当然,有获取操作就得有接收操作,CheckUpdateLibrary已经默认帮开发者实现了获取操作,而对于接收操作就得需要开发者自己实现了,其实接收操作也很简单,可以参考demo也可以参考下面的方式:  
```java
   /**
     * 若要兼容6.0系统,则需要重写此方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果用户同意所请求的权限
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //UPDATE_DIALOG_PERMISSION_REQUEST_CODE和FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE这两个常量是library中定义好的
            //所以在进行判断时,必须要结合这两个常量进行判断.
            //非强制更新对话框
            if (requestCode == UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                //进行下载操作
                mUpdateDialog.download();
            }
            //强制更新对话框
            else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                //进行下载操作
                mForceUpdateDialog.download();
            }
        } else {
            //用户不同意,提示用户,如下载失败,因为您拒绝了相关权限
            Toast.makeText(this, "some description...", Toast.LENGTH_SHORT).show();
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Log.e("tag", "false.请开启读写sd卡权限,不然无法正常工作");
//            } else {
//                Log.e("tag", "true.请开启读写sd卡权限,不然无法正常工作");
//            }
            
        }
    }
```
onRequestPermissionsResult回调方法是系统提供的,所以需要开发者在Dialog所在的Activity或Fragment中重写此方法,并实现下载逻辑,具体的逻辑就可以参考上面的代码,顺便提一句,如果您使用的不是CheckUpdateLibrary提供的两个Dialog,而是自定义的,为了兼容6.0系统,您同样需要进行权限的请求或接收操作,您可以参考CheckUpdateLibrary中的请求权限的实现方式来完善您自定义的Dialog.  
## 扩展性:  
CheckUpdateLibrary具有超强扩展性:  
1,CheckUpdateLibrary有两种方式进行检查更新,对应着两种回调方式:CheckUpdateCallback和CheckUpdateCallback2,前者为了使用方便,所以需要开发者必须保证自定义的实体类中包含newAppVersionCode字段,该字段在库中用来判断是否有更新,而如果开发者想完全自定义实体,那么可以使用后者,即CheckUpdateCallback2,但是需要自己进行判断是否软件有更新.  
2,可自定义Dialog.虽然CheckUpdateLibrary中提供了两种Dialog:ForceUpdateDialog和UpdateDialg用来展示强制更新和非强制更新对话框,但还是会有很多开发者想使用自己自定义的Dialog,然后借助于CheckUpdateLibrary中的后台下载功能和通知展示功能来实现后台下载,而CheckUpdateLibrary也考虑到开发者的这种需求,开发者只需要继承CheckUpdateLibrary中的BaseService,然后实现相关方法即可实现自己的需求,具体的使用方式请参考下面的教程.  
## 用法:  
#### Android Studio添加依赖:  
**gradle方式:**
```groovy
 dependencies {  
    compile 'com.qiangxi.checkupdatelibrary:checkupdatelibrary:1.1.0' 
    }
```
**Maven方式:**  
```maven
<dependency>
  <groupId>com.qiangxi.checkupdatelibrary</groupId>
  <artifactId>checkupdatelibrary</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```  
#### Eclipse:  
使用eclipse的小伙伴只能下载library文件,然后引入到项目中使用了 
#### 基本配置:  
在1.0.5版本中移除了manifest文件中的相关权限和配置,所以在配置好checkupdatelibrary的依赖之后,需要在自己项目的manifest.xml文件中配置相关权限和配置,具体的权限和配置如下:  
**需要的权限:**  
```xml
 <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
```  
**DownloadService配置:**  
```xml
 <service android:name="com.qiangxi.checkupdatelibrary.service.DownloadService"/>
```
上面两步配置必不可少,配置完毕之后就可以正常使用了.  
#### 检查更新:    
检查更新方式在1.0.4版本中有一些变化,在1.0.4版本中,加入了可以添加请求参数的功能,具体的使用可以参考demo教程,下面贴出核心示例代码:  
**1.0.4版本的用法:**  
```java  
Map<String,String> param = new HashMap<>();
param.put("key1","value1");
...
param.put("keyN", "valueN");
//若无需传参,param参数的位置传入null即可
Q.checkUpdate(Q.GET, getVersionCode(), "检查更新地址", param,new CheckUpdateCallback() {
     @Override
     public void onCheckUpdateSuccess(String result, boolean hasUpdate) {
         Log.e("tag",result);
         //result:服务端返回的json,解析成自己的实体类,当然您也可以使用checkupdatelibrary中自带的实体类CheckUpdateInfo
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
**或**  
```java
 Map<String, String> params = new HashMap<>();
 params.put("key1", "value1");
 //...
 params.put("keyN", "valueN");
 //若无需传参,param参数的位置传入null即可
 Q.checkUpdate(Q.GET, "检查更新地址", params, new CheckUpdateCallback2() {
     @Override
     public void onCheckUpdateSuccess(String result) {
         Log.e("tag", result);
         //result:服务端返回的json,需要自己判断有无更新,解析成自己的实体类进行判断是否有版本更新
     }
     @Override
     public void onCheckUpdateFailure(String failureMessage) {
         Log.e("tag", failureMessage);
     }
```
**1.0.3版本及以前版本的用法:**  
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
**或**
```java  
Q.checkUpdate("post", "checkUpdateUrl", new CheckUpdateCallback2() {
    @Override
    public void onCheckUpdateSuccess(String result) {
        //result:服务端返回的json,需要自己判断有无更新,解析成自己的实体类进行判断是否有版本更新
    }
    @Override
    public void onCheckUpdateFailure(String failureMessage) {
    }
});  
```  
### 强制更新对话框:  
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
#### 非强制更新对话框:  

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
#### 使用自定义Dialog:  
##### 对于强制更新方式:  
**step1:**自定义自己的Dialog,这个就不贴代码了,大家应该都会  
**step2:**在自定义的dialog中,比如button1是用来下载文件的,那么可以使用HttpRequest类中提供的download方法,然后在各个回调中实现自己的逻辑,比如进度条监听,下载完成后安装等,示例代码如下:  
```java  
button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防抖动,两次点击间隔小于500ms都return;
                if (System.currentTimeMillis() - timeRange < 500) {
                    return;
                }
                timeRange = System.currentTimeMillis();
                setNetWorkState();
                if (!NetWorkUtil.hasNetConnection(context)) {
                    Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("点击安装".equals(button1.getText().toString().trim())) {
                    File file = new File(mFilePath, mFileName);
                    if (file.exists()) {
                        ApplicationUtil.installApk(context, file);
                    } else {
                        download();
                    }
                    return;
                }
                download();
            }
        });  
private void download() {
        forceUpdateProgress.setVisibility(View.VISIBLE);
        HttpRequest.download(mDownloadUrl, mFilePath, mFileName, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(File file) {
                button1.setEnabled(true);
                button1.setText("点击安装");
                ApplicationUtil.installApk(context, file);
            }

            @Override
            public void onProgress(long currentProgress, long totalProgress) {
                button1.setEnabled(false);
                button1.setText("正在下载");
                forceUpdateProgress.setProgress((int) (currentProgress));
                forceUpdateProgress.setMax((int) (totalProgress));
            }

            @Override
            public void onDownloadFailure(String failureMessage) {
                button1.setEnabled(true);
                button1.setText("重新下载");
            }
        });
    }  
```  
##### 对于非强制更新方式:  
**step1:**自定义自己的Dialog,这个就不贴代码了,大家应该都会  
**step2:**继承CheckUpdateLibrary中的BaseService,实现相关方法,其中Service中自带的方法onStartCommand必须重写,一会说为什么.  
示例代码如下:  
```java  
public class DownloadService extends BaseService {
    private int iconResId;
    private String appName;
    private Intent mIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_NOT_STICKY;
        }
        mIntent = intent;
        appName = intent.getStringExtra("appName");
        iconResId = intent.getIntExtra("iconResId", -1);
        if (iconResId == -1) {
            iconResId = R.drawable.icon_downloading;
        }
        download(intent.getStringExtra("downloadUrl"), intent.getStringExtra("filePath"), intent.getStringExtra("fileName"), true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void downloadFailure(String failureMessage) {
        NotificationUtil.showDownloadFailureNotification(this, mIntent, iconResId, appName, "下载失败,点击重新下载", true);
    }

    @Override
    public void downloadSuccess(File file) {
        NotificationUtil.showDownloadSuccessNotification(this, file, iconResId, appName, "下载完成,点击安装", false);
    }

    @Override
    public void downloading(int currentProgress, int totalProgress) {
        NotificationUtil.showDownloadingNotification(this, currentProgress, totalProgress, iconResId, appName, false);
    }
}
```  
**step3**:在自定义的dialog中,比如button1是用来下载文件的,那么可以这么写:  
```java  
button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防抖动,两次点击间隔小于1s都return;
                if (System.currentTimeMillis() - timeRange < 1000) {
                    return;
                }
                timeRange = System.currentTimeMillis();
                setNetWorkState();
                if (!NetWorkUtil.hasNetConnection(context)) {
                    Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, DownloadService.class);
                intent.putExtra("downloadUrl", mDownloadUrl);
                intent.putExtra("filePath", mFilePath);
                intent.putExtra("fileName", mFileName);
                intent.putExtra("iconResId", mIconResId);
                intent.putExtra("isShowProgress", isShowProgress);
                intent.putExtra("appName", mAppName);
                context.startService(intent);
                dismiss();
                Toast.makeText(context, "正在后台为您下载", Toast.LENGTH_SHORT).show();
            }
        });  
```  
ok,到此,自定义非强制更新Dialog的后台下载功能就实现了.  
###### 下面说一说为什么必须要重写onStartCommand方法,主要有两点原因:  
**1,**其实从上面的代码也可以看出,启动Service采用的是context.startService(intent);方法,而不是bind方法,所以在Service中接收intent必须重写onStartCommand方法才行.  
**2,**如果下载失败时,点击通知是可以重新下载应用的,其中应用到PendingIntent.getService()方法,而该方法启动Service就是通过context.startService(intent);方式启动的,所以从这一点出发,仍然必须重写onStartCommand方法才行.  
## Issues  
各位小伙伴使用过程中有任何问题或发现什么bug或有什么好的意见或建议,请通过上面的issue提交给我,谢谢啦~~  
另外,跪求Star或Fork,这是对开发者最好的激励!!!
