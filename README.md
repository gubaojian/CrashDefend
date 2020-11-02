# 主动安全防护

## 为什么要做安全防护
        1、很多时候由于一些微不足道的bug导致APP崩溃很可惜，崩溃直接导致APP退出，但并不能解决问题。
        2、Android Supported以及Android X等三方库系统问题修复，修改源代码，在版本升级代码同步麻烦。
        3、应用关键生命周期增加如activity fragment service的关键性生命周期保护可增加应用的健壮性。
        4、各种回调生命周期未正确处理导致的异常防护。
        
## 安全防护能做什么
        1、onclick事件、scroll事件、touch事件等防护
        2、应用各种callback生命周期的防护
        3、android support库内部bug防护，如RecyclerView防护
        4、应用关键生命周期防护
        5、自定义代码防护，根据自己业务需求，通过自己增加白名单的方式进行防护。
        
## 安全防护和crash捕获的关系
       安全防护是针对常见性异常进行防护，异常时程序主功能正常运行，捕获到异常后正常上报。crash捕获是程序异常后捕获上报，上报完成后程序仍然会崩溃退出。
 
 ## 使用说明
     
     1、依赖引入：
     
     2、初始化SDK,
          CrashDefendSdk.getInstance().setOnCrashCatchListener(new OnCrashCatchListener(){
      
         });
     2、
 
 ## 防护功能说明
 ## 使用说明
## 默认安全防护功能
      默认设定防护包下面或类的所有public protected返回值为void方法默认增加 try{}catch处理；可通过加@DefendIgnore或者下面xml配置对不需要防护的类进行排除。

      onclick事件
      activity的生命周期
      recycler的view系统性问题。
      broadcastreceiver
      service
      fragment


## 设计仅拦截主现场特定异常崩溃？ 


      
### 安全代码防护配置 使用说明
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
        
        <!-- 实现防护 -->
        <defendSubClass class="android.os.Handler" scope="com.cainiao.home.wireless.home"">
            <defendMethod name="handleMessage"/>
        </defendSubClass>

        <!-- BroadcastReceiver 消息接收防护 -->
        <defendSubClass class="android.content.BroadcastReceiver" scope="com.cainiao.home.wireless.home"">
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
    
    
 ### 异步任务防护
