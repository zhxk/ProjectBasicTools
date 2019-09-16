# ProjectBasicTools 适用于 所有Android 项目的一些基础开发工具。与繁琐的代码说拜拜
### 功能包括但不限于：一行代码自定义状态栏样式、自带好看的Toast、一行代码自定义跳转Activity（包括自定义样式） 等等。

1. 先上效果图：

	i. Toast

	![加载失败](https://github.com/zhxk/ProjectBasicTools/blob/master/gif/%E5%90%90%E5%8F%B8.gif?raw=true)

	ii. 状态栏

	![加载失败](https://github.com/zhxk/ProjectBasicTools/blob/master/gif/%E7%8A%B6%E6%80%81%E6%A0%8F.gif?raw=true)

	iii. 界面跳转

	![加载失败](https://github.com/zhxk/ProjectBasicTools/blob/master/gif/%E8%B7%B3%E8%BD%AC.gif?raw=true)
	
	iv. 网络缓存

	![加载失败](https://github.com/zhxk/ProjectBasicTools/blob/master/gif/%E7%BD%91%E7%BB%9C%E7%BC%93%E5%AD%98.gif?raw=true)

2. 使用说明

i. 步骤1.将JitPack存储库添加到构建文件中,将其添加到存储库末尾的根build.gradle中：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
ii.步骤2.添加依赖项：

	dependencies {
	        implementation 'com.github.zhxk:ProjectBasicTools:1.3.4'
	}

项目基础使用工具

一、BaseApplication：

 	setOnNoNetworkListener(OnNoNetworkListener onNoNetworkListener) 全局网络监听器，网络可用后的回调监听器，此接口做刷新数据使用；

二、ToastUtil：提示工具

	1、show(Context context, String msg) 静态方法，用户提示文字；
	2、showEmpty(Context context) 静态方法，“功能尚未完成...”提示；
	
三、TiaoZiUtil：跳字样式工具

	1、TiaoZiUtil(TextView tv, String s, long time) 构造函数调用，文字一个字一个字的逐一显示出来；
	2、setEndListener(EndListener endListener) 外部接口，文字跳完之后触发；
	
四、OkHttpUtil：Http请求工具

	1、OkHttpUtil(StringCallBack stringCallBack) 构造函数调用，此工具的使用第一步先创建StringCallBack回调接口，第二步再调用静态方法requestCall2(final Context context, String url, List<Map<String, Object>> list)；
	2、requestCall2(final Context context, String url, List<Map<String, Object>> list) 静态方法，Http请求数据的路径和参数集合；
	
五、NetworkUtils：网络监测工具

	1、isNetworkConnected(Context context) 静态方法，监测网络是否连接；
	2、getNetworkType(Context context) 静态方法，获取当前网络类型 @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络；

六、LogUtils：日志输出控制类：常用LogUtils.d(String msg);、LogUtils.e(String msg);
	
七、JSONUtil：JSON字符串解析工具，具体方法自己调用；
	
八、ActivityUtil：Activity活动管理，单例模式getInstance()获取Application实例；

	1、addActivity(Activity activity) 静态方法，添加activity到管理集合。一般在BaseActivity的onCreate()中调用，方便后续的activity管理；
	2、removeActivity(Activity activity) 静态方法，从管理集合中移除Activity。一般在onDestory()中调用；
	3、exitSystem()；静态方法，遍历所有Activity并finish.一般退出APP的时候使用；

九、ActivitySkipUtil：activity跳转工具

	1、skipActivity(Activity activity, Class<? extends Activity> targetActivity, boolean isFinishActivity) 静态方法，简单跳转Activity（不带任何数据）；
	2、skipActivity(Activity activity, Class<? extends Activity> targetActivity, HashMap<String, ? extends Object> hashMap, boolean isFinishActivity) 静态方法，带数据跳转Activity；
	
十、TitleVarView：Activity中顶部带返回按钮的导航栏工具，使用方法：继承

	继承后直接调用其内部的方法。详情见代码中的注释；
	
十一、NoNetworkActivity：无网络界面，自带样式。可继承重写；

十二、BaseActivity：所有Activity的基类，把一些公共的方法放到里面，如状态栏基础样式设置，权限封装等。包含了第八条内容；

	1、hasPermission(String... permissions) 继承后直接调用，权限检查方法，false代表没有该权限，ture代表有该权限；
	2、requestPermission(int code, String... permissions) 继承后直接调用，权限请求方法；
	3、doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) 继承后重写，处理请求权限结果回调；
	
十三、BaseFragment：是所有Fragment的基类，设置状态栏基础样式；

十四、IBaseActivity：activity接口基类

	setPresenter(T mIActivityPresenter) 设置逻辑，

十五、IBaseFragment：Fragment接口基类

	setPresenter(T mIFragmentPresenter) 设置逻辑；
	
十六、IBasePresenter：逻辑层基类

	start() 逻辑层开始执行方法；

十七、AnimationUtils：动画工具类

    startShakeByProperty(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) 静态方法，设置震动样式；
    runLayoutAnimation(final View view, int layoutId, Animation.AnimationListener listener) 静态方法，根据自己配置的xml文件执行动画；
    
十八、CommonAdapter：常用的适配器，引用张弘扬大神的加载更多和header加载

	
	

	
