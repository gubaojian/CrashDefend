## 项目背景
   通过沉淀Android通用Crash解决方案及Crash预防方案，将沉淀的最佳实践输出到各种应用中， 提升应用稳定性，减少应用Crash。 Android版本的安全气垫


## 一、增加一下ok异常的捕获

android.app.RemoteServiceException: Bad notification(tag=null, id=1225) posted from package com.cainiao.android.zyb, crashing app(uid=10229, pid=5346): Couldn't inflate contentViewsandroid.view.InflateException: Binary XML file line #2: Binary XML file line #2: Error inflating class com.cainiao.ntms.lib.widget.shortcut.ShortcutLinearLayout
    at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1768)
    at android.os.Handler.dispatchMessage(Handler.java:106)
    at android.os.Looper.loop(Looper.java:201)
    at android.app.ActivityThread.main(ActivityThread.java:6864)
    at java.lang.reflect.Method.invoke(Native Method)
    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:547)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:873)
    
    
## 二、线程管控治理

1


## 分析crash治理的文章
https://www.atatech.org/articles/187341?spm=ata.13269424.0.0.50193753nzCi83




