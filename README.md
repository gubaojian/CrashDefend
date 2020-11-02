# 主动安全防护

## 一、什么是主动安全防护技术
    主动安全防护是一种在编译时对类字节码进行处理，插入防护代码，防止应用崩溃主动安全防护一种技术。示例如下：
#### 程序员写的代码
```java
public void testDefend(){
    new Thread(new Runnable() {
        public void run() {
            throw new RuntimeException("Hello World Crash");
        }
    }).start();
}
```

#### 安全防护后实际运行代码
```java
public void testDefend() {
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        throw new RuntimeException("Hello World Crash");
                    } catch (Throwable var2) {
                        CrashDefendSdk.onCatch(var2);
                    }
                }
            }).start();
        } catch (Throwable var2) {
            CrashDefendSdk.onCatch(var2);
        }
}
```

## 二、为什么要做主动安全防护
    1、很多时候由于一些微不足道的bug导致APP崩溃很可惜，崩溃直接导致APP退出，但并不能解决问题。
    2、Android Supported以及Android X等三方库系统性问题通过修改源代码修复，在版本升级代码同步麻烦。
    3、应用关键生命周期增加如activity fragment service的关键性生命周期保护，增加应用的健壮性。
    4、各种回调生命周期偶然未正确处理导致的异常崩溃。 
    
## 三、主动安全防护和crash捕获关系：
  主动安全防护是针对常见性可忽略的异常进行防护，异常时程序主功能正常运行，捕获到异常后正常上报。crash捕获是程序异常后捕获上报，上报完成后程序仍然会崩溃退出。 相同之处在于异常上报，不同之处前者程序正常运行，后者程序崩溃。
 
 ## 四、主动安全使用说明
4.1、安全防护SDK引入及初始化
```groovy
dependencies {
    api 'com.cainiao.wireless.crashdefendsdk:crashdefendsdk:0.0.0.1@aar'
}
```
```java
//初始化主动防护SDK，上报拦截的异常
CrashDefendSdk.getInstance().setOnDefendCrashListener(new OnDefendCrashListener(){
     //上报拦截的异常
});
```
4.2、安全防护编译插件配置
```groovy
buildscript {
    dependencies {
        //插件依赖
       classpath 'com.crashdefend.tools.build:gradleplugin:0.0.1-SNAPSHOT'
   }
}

apply plugin: 'com.android.application'
//应用主动防护插件，在编译时对代码进行防护处理
apply plugin: 'com.crashdefend'
```
```groovy
apply plugin: 'com.android.application'
//应用插件库
apply plugin: 'com.crashdefend'
```
4.3、配置配置defend.xml，可为每个模块配置单独的保护文件defend-home.xml defend-trans.xml
 
## 五、主动安全防护defend.xml配置
### 5.1 接口实现类防护
```xml
 <!-- OnClickListener 点击事件防护 -->
<defendInterfaceImpl interface="android.view.OnClickListener" scope="com.cainiao.home.wireless.home">
</defendInterfaceImpl>

<!-- Handler.Callback 防护 -->
<defendInterfaceImpl interface="android.os.Handler.Callback" scope="com.cainiao.home.wireless.home">
</defendInterfaceImpl>

<!-- 各种Callback回调防护，根据需要自己添加即可 -->

```
### 5.2 类实现子类防护
```xml
<!-- BroadcastReceiver 消息接收防护 -->
<defendSubClass class="android.content.BroadcastReceiver" scope="com.cainiao.home.wireless.home">
  <defendMethod name="onReceive"/>
</defendSubClass>

<!-- Activity关键性生命周期防护 -->
<defendSubClass class="android.app.Activity" scope="com.cainiao.home.wireless.home">
  <defendMethod name="onCreate"/>
  <defendMethod name="onStart"/>
  <defendMethod name="onResume"/>
  <defendMethod name="onPause"/>
  <defendMethod name="onStop"/>
  <defendMethod name="onDestroy"/>
  <defendMethod name="onNewIntent"/>
  <defendMethod name="onSaveInstanceState"/>
</defendSubClass>

<!-- Application 启动防护 -->
<defendSubClass class="android.app.Application">
  <defendMethod name="onCreate" />
  <defendMethod name="onTerminate" />
  <defendMethod name="onConfigurationChanged" />
</defendSubClass>
```

### 5.3 指定类和方法防护
```xml
<!-- LinearLayoutManager 部分布局异常防护 -->
<defendClass class="android.support.v7.widget.LinearLayoutManager">
  <defendMethod name="scrollBy" returnValue="0"/>
  <defendMethod name="layoutChunk" />
  <defendMethod name="onLayoutChildren"/>
</defendClass>


<!-- DefaultItemAnimator runPendingAnimations异常防护 -->
<defendClass class="android.support.v7.widget.DefaultItemAnimator">
  <defendMethod name="runPendingAnimations"/>
</defendClass>

<!-- AndroidX ForceStopRunnable异常防护 -->
<defendClass class="androidx.work.impl.utils.ForceStopRunnable" >
  <defendMethod name="run"/>
</defendClass>

<!--RecyclerView 部分异常防护 -->
<defendClass class="android.support.v7.widget.RecyclerView">
  <defendMethod name="setScrollState"/>
  <defendMethod name="dispatchOnScrollStateChanged"/>
  <defendMethod name="scrollToPosition"/>
  <defendMethod name="onLayout"/>
  <defendMethod name="dispatchLayout"/>
  <defendMethod name="draw"/>
  <defendMethod name="onAttachedToWindow"/>
  <defendMethod name="removeAnimatingView"/>
</defendClass>

```

## 六、安全防护整体配置示例
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <!-- OnClickListener 防护 -->
    <defendInterfaceImpl interface="android.view.OnClickListener" scope="com.cainiao.home.wireless.home">
    </defendInterfaceImpl>

    <!-- 事件分发防护 -->
    <defendInterfaceImpl interface="android.view.Window.Callback" scope="com.cainiao.home.wireless.home">
      <defendMethod name="dispatchTouchEvent" returnValue="false"/>
    </defendInterfaceImpl>

    <!-- 键盘点击防护 -->
    <defendInterfaceImpl interface="android.view.KeyEvent.Callback" scope="com.cainiao.home.wireless.home">
    </defendInterfaceImpl>

    <!-- Runnable任务防护 -->
    <defendInterfaceImpl interface="java.lang.Runnable" scope="com.cainiao.home.wireless.home">
    </defendInterfaceImpl>

    <!-- Handler.Callback 防护 -->
    <defendInterfaceImpl interface="android.os.Handler.Callback" scope="com.cainiao.home.wireless.home">
    </defendInterfaceImpl>

    <!-- Handler实现防护 -->
    <defendSubClass class="android.os.Handler" scope="com.cainiao.home.wireless.home">
      <defendMethod name="handleMessage"/>
    </defendSubClass>

    <!-- BroadcastReceiver 消息接收防护 -->
    <defendSubClass class="android.content.BroadcastReceiver" scope="com.cainiao.home.wireless.home">
      <defendMethod name="onReceive"/>
    </defendSubClass>


    <!-- Activity关键性生命周期防护 -->
    <defendSubClass class="android.app.Activity" scope="com.cainiao.home.wireless.home">
      <defendMethod name="onCreate"/>
      <defendMethod name="onStart"/>
      <defendMethod name="onResume"/>
      <defendMethod name="onPause"/>
      <defendMethod name="onStop"/>
      <defendMethod name="onDestroy"/>
      <defendMethod name="onNewIntent"/>
      <defendMethod name="onSaveInstanceState"/>
    </defendSubClass>

    <!-- Application 启动防护 -->
    <defendSubClass class="android.app.Application">
      <defendMethod name="onCreate" />
      <defendMethod name="onTerminate" />
      <defendMethod name="onConfigurationChanged" />
    </defendSubClass>

    <!--- 默认包防护，默认防护以上功能 -->
    <defendAuto scope="com.cainiao.home.wireless.home"/>


    <!--RecyclerView 部分异常防护 -->
    <defendClass class="android.support.v7.widget.RecyclerView">
      <defendMethod name="setScrollState"/>
      <defendMethod name="dispatchOnScrollStateChanged"/>
      <defendMethod name="scrollToPosition"/>
      <defendMethod name="onLayout"/>
      <defendMethod name="dispatchLayout"/>
      <defendMethod name="draw"/>
      <defendMethod name="onAttachedToWindow"/>
      <defendMethod name="removeAnimatingView"/>
    </defendClass>

    <!-- LinearLayoutManager 部分布局异常防护 -->
    <defendClass class="android.support.v7.widget.LinearLayoutManager">
      <defendMethod name="scrollBy" returnValue="0"/>
      <defendMethod name="layoutChunk" />
      <defendMethod name="onLayoutChildren"/>
    </defendClass>


    <!-- DefaultItemAnimator runPendingAnimations异常防护 -->
    <defendClass class="android.support.v7.widget.DefaultItemAnimator">
      <defendMethod name="runPendingAnimations"/>
    </defendClass>

    <!-- AndroidX ForceStopRunnable异常防护 -->
    <defendClass class="androidx.work.impl.utils.ForceStopRunnable" >
      <defendMethod name="run"/>
    </defendClass>

    <!-- RecyclerView 后台获取异常防护 -->
    <defendClass class="android.support.v7.widget.GapWorker" >
      <defendMethod name="run"/>
    </defendClass>
</resources>
```

## 七、安全防护效果示例
#### 7.1 点击事件安全防护代码效果示例
程序书写代码
```java
private View.OnClickListener mOnDistCenterClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
         Toast.makeText(view.getContext(), "Hello Click Defend", Toast.LENGTH_SHORT).show();
      }
};
```
实际运行代码
```java
private OnClickListener mOnDistCenterClickListener = new OnClickListener() {
    public void onClick(View view) {
        try {
            Toast.makeText(view.getContext(), "Hello Click Defend", Toast.LENGTH_SHORT).show();
        } catch (Throwable th) {
            CrashDefendSdk.onCatch(th);
        }
    }
};
```


 
 
 
