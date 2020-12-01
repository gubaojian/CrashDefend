# 主动安全防护说明

# 主动安全防护说明


## 一、什么是主动安全防护技术
#### 1.1 什么是主动安全防护技术
      主动安全防护是一种在Android编译时对类字节码进行处理，插入防护代码，防止应用崩溃主动安全防护一种技术。
#### 1.2 主动安全防护的技术流程
![image.png](https://intranetproxy.alipay.com/skylark/lark/0/2020/png/2964/1605510803440-270c8355-d780-495f-8604-7c864abfa229.png#align=left&display=inline&height=250&margin=%5Bobject%20Object%5D&name=image.png&originHeight=500&originWidth=1586&size=268057&status=done&style=none&width=793)
#### 1.3 主动安全防护示例子
##### 1.3.1 程序员写的原始代码
```java
public void testDefend(){
    new Thread(new Runnable() {
        public void run() {
            throw new RuntimeException("Hello World Crash");
        }
    }).start();
}
```
##### 1.3.2 安全防护实际运行代码
```java
public void testDefend() {
    new Thread(new Runnable() {
        public void run() {
            try {
                throw new RuntimeException("Hello World Crash");
            } catch (Throwable var2) {
                CrashDefendSdk.onCatch(var2);
            }
        }
    }).start();
}
```


## 二、为什么要做主动安全防护


```xml
1、很多时候由于一些微不足道的bug导致APP崩溃很可惜，崩溃直接导致APP退出，但并不能解决问题，直接忽略更好。
2、Android Support以及Android X等三方库系统性问题通过修改源代码修复，在版本升级代码同步麻烦，很多团队直接无视。
3、应用application activity fragment service的关键性生命周期安全防护，增加应用的健壮性。
4、各种回调生命周期偶然未正确处理导致的异常崩溃。
5、部分三方库代码无法修改，反编译重新打包不方便，bug修复周期过长
6、后台任务异常挂掉，不影响前台应用。
7、应用自己加try catch，代码不好看，有时缺少异常上报流程。
```


## 三、主动安全防护和crash捕获关系：


#### 3.1 主动防护与被动捕获区别
主动安全防护是针对常见性可忽略的异常进行防护，异常时程序主功能正常运行，捕获到异常后正常上报。crash被动捕获是程序异常后，被系统中断捕获，然后回调到捕获，进行上报，上报完成后程序仍然会崩溃退出。 相同之处在于异常上报，不同之处前者程序正常运行，后者程序崩溃。
#### 3.2 主动防护上报流程如下
![image.png](https://intranetproxy.alipay.com/skylark/lark/0/2020/png/2964/1605521855837-1d7d5c1b-29a1-455c-b5c1-31c9b0f64091.png#align=left&display=inline&height=337&margin=%5Bobject%20Object%5D&name=image.png&originHeight=710&originWidth=1436&size=286396&status=done&style=none&width=681)


## 四、主动安全使用说明


#### 4.1、安全防护SDK引入及初始化
```groovy
dependencies {
   api 'com.cainiao.wireless.crashdefendkit:crashdefendkit:0.0.3@aar'
}
```


```java
//初始化主动防护SDK，防护异常，并上报防护的异常
CrashDefendInit.init(context)
```


#### 4.2、安全防护编译插件配置
```groovy
buildscript {
    dependencies {
        //插件依赖
       classpath 'com.crashdefend.tools.build:gradleplugin:0.0.4'
   }
}
apply plugin: 'com.android.application'
//应用主动防护，在编译时对代码进行防护处理
apply plugin: 'com.cainiao.crashdefend'
```


#### 4.3、默认配置为defend.xml，可为每个模块配置单独的保护文件defend-home.xml defend-trans.xml
```groovy
ext {
    defendConfigFiles =["defend.xml", "defend-home.xml"]
}
```
#### 4.4、通过@Defend 注解进行防护处理，示例如下：
```java
import com.cainiao.wireless.crashdefend.Defend;
public class StartUpTest {
    //方法防护
    @Defend
    public static void doTaskOne(){
        // some worker code
        throw new RuntimeException("Hello World Defend");
    }
}

//关键性生命周期防护
@Defend
public class HomeActivity extends Activity{
  
}
```


## 五、主动安全防护defend.xml配置


### 5.1 接口实现类防护


```xml
<!-- 首页点击事件防护， scope配置生效的包名范围，多个模块可配置多条记录 -->
<defendInterfaceImpl interface="android.view.View$.OnClickListener" scope="com.cainiao.home.wireless.home">
</defendInterfaceImpl>
<!-- 我的模块点击事件防护 -->
<defendInterfaceImpl interface="android.view.View$.OnClickListener" scope="com.cainiao.home.wireless.mine">
</defendInterfaceImpl> 

<!-- Handler.Callback 防护 -->
<defendInterfaceImpl interface="android.os.Handler$Callback" scope="com.cainiao.home.wireless.home">
</defendInterfaceImpl>

<!-- 各种Callback回调防护，根据需要自己添加即可 -->
```


### 5.2 类实现子类防护
```xml
<!-- BroadcastReceiver 消息接收防护 -->
<defendSubClass parent="android.content.BroadcastReceiver" scope="com.cainiao.home.wireless.home">
  <defendMethod name="onReceive"/>
</defendSubClass>

<!-- Activity关键性生命周期防护 -->
<defendSubClass parent="android.app.Activity" scope="com.cainiao.home.wireless.home">
  <defendMethod name="onCreate"/>
  <defendMethod name="onStart"/>
  <defendMethod name="onResume"/>
  <defendMethod name="onPause"/>
  <defendMethod name="onStop"/>
  <defendMethod name="onDestroy"/>
  <defendMethod name="onNewIntent"/>
  <defendMethod name="onSaveInstanceState"/>
</defendSubClass>

<!-- Fragment关键性生命周期防护 -->
<defendSubClass parent="android.support.v4.app.Fragment" scope="com.cainiao.home.wireless.home">
  <defendMethod name="onAttach"/>
  <defendMethod name="onCreate"/>
  <defendMethod name="onCreateView"/>
  <defendMethod name="onStart"/>
  <defendMethod name="onResume"/>
  <defendMethod name="onPause"/>
  <defendMethod name="onStop"/>
  <defendMethod name="onDestroyView"/>
  <defendMethod name="onDestroy"/>
  <defendMethod name="onDetach"/>
</defendSubClass>


<!-- Application 启动防护 -->
<defendSubClass parent="android.app.Application" scope="com.cainiao">
  <defendMethod name="onCreate" />
  <defendMethod name="onTerminate" />
  <defendMethod name="onConfigurationChanged" />
</defendSubClass>

<!-- service 生命周期防护 -->
<defendSubClass parent="android.app.Service" scope="com.cainiao">
    <defendMethod name="onCreate" />
    <defendMethod name="onStart" />
    <defendMethod name="onDestroy"/>
</defendSubClass>  

<!--IntendService 防护-->
<defendSubClass parent="android.app.IntentService" scope="com.cainiao">
    <defendMethod name="onHandleIntent" />
</defendSubClass>  

<!-- content provider防护 -->
<defendSubClass parent="android.content.ContentProvider" scope="com.cainiao">
     <defendMethod name="onCreate" />
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

<!-- Fragment 退出防护 -->
<defendClass class="android.support.v4.app.FragmentManagerImpl">
    <defendMethod name="enqueueAction"/>
</defendClass>  
  


```


### 5.4 对指定包自动防护
```xml
<!--防护常见类型的点击，handler，runable等常见异常-->
<defendAuto scope="com.cainiao.wireless.bgx.home"/>
<defendAuto scope="com.cainiao.wireless.zfb.home"/>
```
### 5.5 安全防护开关
```xml
<!-- debug包开启防护功能，默认仅release开启 -->
<defendOnDebug>true</defendOnDebug>
<!-- 关闭 所有防护功能 -->
<defendOff>true</defendOff>
```
### 5.6 内部类名字配置主意事项
匿名内部类名字配置时，请主意配置$符号，示例如下：
```java
<!-- 首页点击事件防护， scope配置生效的包名范围，多个模块可配置多条记录 -->
<defendInterfaceImpl interface="android.view.View$.OnClickListener" scope="com.cainiao.home.wireless.home">
</defendInterfaceImpl>
```
### 5.7 gradlew编译出现异常
执行下面命令，停止后台编译，然后清理工程，重新编译即可
```shell
./gradlew --stop       
```
### 5.8 主动防御对包大小影响
      通过白名单的方式，增加try catch代码，对包大小影响在0.01M以内，对包大小影响可以忽略不计。
## 六、安全防护整体配置示例


```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

  <!-- 首页点击事件防护， scope配置生效的包名范围，根据业务维度进行配置，多个模块可配置多条记录 -->
  <defendInterfaceImpl interface="android.view.View$.OnClickListener" scope="com.cainiao.home.wireless.home">
  </defendInterfaceImpl>
  <!-- 我的模块点击事件防护，根据我的业务模块需求进行配置 -->
  <defendInterfaceImpl interface="android.view.View$.OnClickListener" scope="com.cainiao.home.wireless.mine">
  </defendInterfaceImpl>

  <!-- 事件分发防护， scope配置生效的包名范围 -->
  <defendInterfaceImpl interface="android.view.Window$Callback" scope="com.cainiao.home.wireless.home">
    <defendMethod name="dispatchTouchEvent" returnValue="false"/>
  </defendInterfaceImpl>

  <!-- 键盘点击防护， scope配置生效的包名范围 -->
  <defendInterfaceImpl interface="android.view.KeyEvent$Callback" scope="com.cainiao.home.wireless.home">
  </defendInterfaceImpl>

  <!-- Runnable任务防护， scope配置生效的包名范围-->
  <defendInterfaceImpl interface="java.lang.Runnable" scope="com.cainiao.home.wireless.home">
  </defendInterfaceImpl>

  <!-- Handler.Callback 防护， scope配置生效的包名范围 -->
  <defendInterfaceImpl interface="android.os.Handler$Callback" scope="com.cainiao.home.wireless.home">
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
  <defendSubClass class="android.app.Application" scope="com.cainiao">
    <defendMethod name="onCreate" />
    <defendMethod name="onTerminate" />
    <defendMethod name="onConfigurationChanged" />
  </defendSubClass>

  <!--- 默认常规防护，默认防护以上功能 -->
  <defendAuto scope="com.cainiao.wireless.home"/>


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
##### 7.1.1 原始代码
```java
private View.OnClickListener mOnDistCenterClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
         Toast.makeText(view.getContext(), "Hello Click Defend", Toast.LENGTH_SHORT).show();
      }
};
```


##### 7.1.2 防护代码
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


#### 7.2 Fragment退出压栈等操作防护
##### 7.2.1 FragmentImpl.java原始代码
```java
 public void enqueueAction(OpGenerator action, boolean allowStateLoss) {
    if (!allowStateLoss) {
        checkStateLoss();
    }
    synchronized (this) {
        if (mDestroyed || mHost == null) {
            throw new IllegalStateException("Activity has been destroyed");
        }
        if (mPendingActions == null) {
            mPendingActions = new ArrayList<>();
        }
        mPendingActions.add(action);
        scheduleCommit();
    }
}
```
##### 7.2.2 FragmentImpl.java防护代码
```java
public void enqueueAction(OpGenerator action, boolean allowStateLoss) {
    try {
      if (!allowStateLoss)
        checkStateLoss(); 
      synchronized (this) {
        if (this.mDestroyed || this.mHost == null) {
          if (allowStateLoss)
            return; 
          throw new IllegalStateException("Activity has been destroyed");
        } 
        if (this.mPendingActions == null)
          this.mPendingActions = new ArrayList<>(); 
        this.mPendingActions.add(action);
        scheduleCommit();
      } 
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
```


#### 7.3 RecyclerView布局异常防护
##### 7.3.1 RecyclerView.java原始代码
```java
@Override
protected void onLayout(boolean changed, int l, int t, int r, int b) {
    TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
    dispatchLayout();
    TraceCompat.endSection();
    mFirstLayoutComplete = true;
}
```
##### 7.3.2 RecyclerView.java防护代码
```java
@Override
protected void onLayout(boolean changed, int l, int t, int r, int b) {
    try {
      TraceCompat.beginSection("RV OnLayout");
      dispatchLayout();
      TraceCompat.endSection();
      this.mFirstLayoutComplete = true;
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
```
#### 7.4 BroadcastReceiver消息接收防护
##### 7.4.1 MyBroadcastReceiver.java原始代码
```java
private class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, "onReceive, action = " + intent.getAction());

        if (AccountWrapper.ACTION_LOGIN_SUCCESS.equals(intent.getAction())) {
            LogUtil.i(TAG, "onReceive, login success");
            stopReceiver();
            setWorkState(WORK_STATE_SUCCESS);
            mErrorMessage = null;
            notifyResult();
        } else if (AccountWrapper.ACTION_LOGIN_FAIL.equals(intent.getAction())) {
            LogUtil.e(TAG, "onReceive, login fail");
            //stopReceiver();
            setWorkState(WORK_STATE_FAIL);
            mErrorMessage = intent.getStringExtra(AccountWrapper.KEY_LOGIN_ERROR_MSG);
            notifyResult();
        }
    }
}
```
##### 7.4.2 MyBroadcastReceiver.java防护代码
```java
 private class MyBroadcastReceiver extends BroadcastReceiver {
    private MyBroadcastReceiver() {}
    
    public void onReceive(Context context, Intent intent) {
      try {
        LogUtil.i("LoginState", "onReceive, action = " + intent.getAction());
        if ("action_login_success".equals(intent.getAction())) {
          LogUtil.i("LoginState", "onReceive, login success");
          LoginState.this.stopReceiver();
          LoginState.this.setWorkState(3);
          LoginState.this.mErrorMessage = null;
          LoginState.this.notifyResult();
        } else if ("action_login_fail".equals(intent.getAction())) {
          LogUtil.e("LoginState", "onReceive, login fail");
          LoginState.this.setWorkState(4);
          LoginState.this.mErrorMessage = intent.getStringExtra("key_login_error_msg");
          LoginState.this.notifyResult();
        } 
        return;
      } catch (Throwable throwable) {
        CrashDefendSdk.onCatch(throwable);
        return;
      } 
    }
  }
```
#### 7.5 Activity生命周期防护
##### 7.5.1 SplashActivity.java原始代码
```java
public class SplashActivity extends BaseActivity implements OnClickListener, IContext {
    
    private static final String TAG = "SplashActivity";
    private Button mBtnRetry;
    private IState mState;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreate, pid: " + Process.myPid() + ", tid: " + Thread.currentThread().getId());
        super.onCreate(savedInstanceState);
        setSlideBack(false);
        setContentView(R.layout.activity_splash);
        mBtnRetry = (Button)findViewById(R.id.btn_retry);
        mBtnRetry.setOnClickListener(this);
        BenchmarkUtil.start("RouteInit");
        mState = new RouterState(this);
        mState.doWork();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume");
        mState.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG, "onPause");
        mState.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");
        mState.onDestroy();
        mState = null;
    }
}
```
##### 7.5.2 SplashActivity.java防护代码
```java
public class SplashActivity extends BaseActivity implements View.OnClickListener, IContext {
  private static final String TAG = "SplashActivity";
  private Button mBtnRetry;
  private IState mState;
    
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    try {
      LogUtil.i("SplashActivity", "onCreate, pid: " + Process.myPid() + ", tid: " + Thread.currentThread().getId());
      super.onCreate(savedInstanceState);
      setSlideBack(false);
      setContentView(2131427423);
      this.mBtnRetry = (Button)findViewById(2131296956);
      this.mBtnRetry.setOnClickListener(this);
      BenchmarkUtil.start("RouteInit");
      this.mState = new RouterState(this);
      this.mState.doWork();
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
    
  @Override
  protected void onResume() {
    try {
      super.onResume();
      LogUtil.i("SplashActivity", "onResume");
      this.mState.onResume();
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
    
  @Override
  protected void onPause() {
    try {
      super.onPause();
      LogUtil.i("SplashActivity", "onPause");
      this.mState.onPause();
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
    
  @Override
  protected void onDestroy() {
    try {
      super.onDestroy();
      LogUtil.i("SplashActivity", "onDestroy");
      this.mState.onDestroy();
      this.mState = null;
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
}
```
#### 7.6 Applicaition启动防护
##### 7.6.1 CApplication原始代码
```java
public class CApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadedApkHuaWei.hookHuaWeiVerifierOnlyHuawei(this);
        mInstance = this;
        disableAPIDialog();
        com.alipay.mobile.liteprocess.Util.setContext(this.getApplicationContext());
        RPSDK.setContext(this.getApplicationContext());
        CDSSContext.setAppContext(this);
        XCommonManager.setContext(this);
        new InitManager().init(this);
        AppInitializeHelper.initialize();
        ToneManager.getInstance();
        initOSSWrapper();
    }
}
```
##### 7.6.1 CApplication防护代码
```java
public class CApplication extends MultiDexApplication {
    
  public void onCreate() {
    try {
      super.onCreate();
      LoadedApkHuaWei.hookHuaWeiVerifierOnlyHuawei((Application)this);
      mInstance = this;
      disableAPIDialog();
      Util.setContext(getApplicationContext());
      RPSDK.setContext(getApplicationContext());
      CDSSContext.setAppContext((Application)this);
      XCommonManager.setContext((Application)this);
      (new InitManager()).init((Application)this);
      AppInitializeHelper.initialize();
      ToneManager.getInstance();
      initOSSWrapper();
      return;
    } catch (Throwable throwable) {
      CrashDefendSdk.onCatch(throwable);
      return;
    } 
  }
}
```
#### 7.7 Runnable防护
##### 7.7.1 DisableLayerRunnable.java原始代码
```java
 private class DisableLayerRunnable implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View childView) {
            mChildView = childView;
        }

        @Override
        public void run() {
            if (mChildView.getParent() == SlideFrameLayout.this) {
                ViewCompat.setLayerType(mChildView, ViewCompat.LAYER_TYPE_NONE, null);
                invalidateChildRegion(mChildView);
            }

            mPostedRunnables.remove(this);
        }
}
```
##### 7.7.2 DisableLayerRunnable.java防护代码
```java
 private class DisableLayerRunnable implements Runnable {
    final View mChildView;
    
    DisableLayerRunnable(View childView) {
      this.mChildView = childView;
    }
     
    public void run() {
      try {
        if (this.mChildView.getParent() == SlideFrameLayout.this) {
          ViewCompat.setLayerType(this.mChildView, 0, null);
          SlideFrameLayout.this.invalidateChildRegion(this.mChildView);
        } 
        SlideFrameLayout.this.mPostedRunnables.remove(this);
        return;
      } catch (Throwable throwable) {
        CrashDefendSdk.onCatch(throwable);
        return;
      } 
    }
}
```
#### 7.8 自定义回调防护，如MTOP回调


#### 7.9 更多防护效果，如Touch、Attach、Detach事件等，可参考主动防护功能，根据业务需求进行定制。


