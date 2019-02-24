# C9Fragment框架使用

------

在该项目中，主要分成3个模块：

 - cloud：主要框架的包，其中主要是使用了一个Activity配备多个Fragment的框架来实现
 - tool：工具包，其中包括一些常用的工具类，以及Adapter适配器等
 - sample：对工程中的工具、框架进行使用，实现例子

## 框架的使用
### 框架初始化
可以创建一个新的业务模块，而不要在sample模块中直接修改。
初始化框架时，首先需要在gradle中导入：
```

    implementation project(':cloud')
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.5.0'
    implementation project(':tool')
```
以上依赖是必须填写的。

### application的初始化

创建一个新的Application类，在该类中初始化
```
        Cloud9.init(this)
                .withHost("http://localhost:8080")
                .withLogger("Cloud9")
                .withHideBar(true)
                .withSpName("CLOUD9")
                .configure();


        Logger.initLogger(new DefaultConverters(Cloud9.getConfig(ConfigurationKey.LOGGER)),
                new LoggerPrint()
                /*new FilePrint("/test/", "log_" + System.currentTimeMillis() + ".txt")*/);
```
其中Cloud9.init()为初始化C9框架，Logger.initLogger()为初始化Log工具。
C9框架的初始化内容可参考com.cloud.common.base.ConfigurationKey类（初始版本如下，以后可能会有添加其他属性，可在该类中查看）：
```
    /**
     * 全局请求的API
     */
    API_HOST,
    /**
     * 全局上下文
     */
    APPLICATION_CONTEXT,
    /**
     * 控制参数是否已经配置成功
     */
    CONFIG_READY,
    /**
     * 请求拦截器
     */
    INTERCEPTOR,
    /**
     * 自定义配置的项目
     */
    ICON,
    /**
     * 日志
     */
    LOGGER,
    /**
     * 隐藏虚拟按键
     */
    HIDE_BAR,
    /**
     * SharedPreference的name
     */
    SP_NAME
```

而Logger工具初始化中可以将log保存成文件，只需将上面的注释内容打开即可：
```
Logger.initLogger(new DefaultConverters(Cloud9.getConfig(ConfigurationKey.LOGGER)),
                new LoggerPrint()
                new FilePrint("/test/", "log_" + System.currentTimeMillis() + ".txt"));
```

### 创建Activity
```
public class SampleActivity extends ProxyActivity {

    @Override
    public Cloud9Delegate setRootDelegate() {
        return new SampleDelegate();
    }
}
```
该Activity实现ProxyActivity，重写setRootDelegate()方法，该方法返回一个根Fragment，也就是首个显示的界面，可以是Splash，也可以是Main。

### 创建Delegate（Fragment）
```
public class SampleDelegate extends Cloud9Delegate {
    @BindView(R.id.btn_test_net)
    AppCompatButton btnTestNet;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
    }


    @OnClick({R.id.btn_test_net, R.id.btn_test_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_net:
//                start(new SampleNetDelegate(), SupportFragment.SINGLETASK);
                start(new SampleNetDelegate());
                break;
            case R.id.btn_test_permission:
                start(new SamplePermissionDelegate());
                break;
        }
    }
}
```
Delegate只需要继承Cloud9Delegate，并在setLayout()方法中写入该Delegate的布局，该方法可以返回View或layoutId。onBindedView()为控件绑定后回调的，这里可以避免控件绑定过程中误操作控件的问题。
同时，Delegate提供了Butternife来绑定控件。


## 网络框架的使用
```
public class SampleNetDelegate extends Cloud9Delegate {
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_net;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", "18814182530");
        RequestBody body
                = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        HttpClient.builder(new RestBuilder(this.getProxyActivity()))
                .url("http://www.baidu.com")
//                .url("http://47.106.66.5:9090/kiosk/landlord/captcha")
//                .params("mobile", "18814182533")
                /*.body(body)
                .success((ISuccess<RestResponse>) response -> {
                    Logger.d("【请求成功】" + response.getResponseCode());
                    Logger.d("【请求成功】" + response.getResponseContent());
                })
                .failure(throwMsg -> Logger.d("【请求失败】" + throwMsg))*/
                .loader(new CloudFullScreenDialog(this.getProxyActivity()))
                .build()
//                .post();
                .get();
    }

    @HttpCallback
    public void onResponse(RestResponse restResponse) {
        Logger.d("【请求成功onResponse】" + restResponse.getResponseCode());
        tvContent.setText(restResponse.getResponseContent());
    }

    @HttpCallback
    public void onResponse(String failMsg) {
        Logger.d("【请求失败onResponse】" + failMsg);
        tvContent.setText("【请求失败onResponse】" + failMsg);
    }
}
```
这里传递参数的方式有两种，一种是直接通过params()进行传递的，一种是通过body来进行传递。
而请求网络的返回方式有两种
一种通过接口回调：可通过ISuccess、IFailure来处理返回数据
一种通过注解回调：可通过@HttpCallback来处理，这种方法需要注意的是返回参数必须和所采用的网络请求策略返回的类型一致，即在接收成功信息时使用如 @HttpCallback public void onSuccessResponse(RestResponse successResponse){}的方法，而接收失败时信息则使用如 @HttpCallback public void onFailureResponse(String failMsg){}的方法，方法名称、参数名称可以自取


## 权限的申请
```
public class SamplePermissionDelegate extends Cloud9Delegate {
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;
    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_permission;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        requestPermission(true, PermissionCode.Group.STORAGE, PermissionCode.Group.CONTACTS);
    }

    @Override
    public void onHasPermission(List<String> granted, boolean isAll) {
        super.onHasPermission(granted, isAll);

        mStringBuilder.append("通过的权限：\n");
        for (String permission: granted) {
            mStringBuilder.append(permission + "\n");
        }

        tvContent.setText(mStringBuilder.toString());
    }

    @Override
    public void onNoPermission(List<String> denied, boolean quick) {
        super.onNoPermission(denied, quick);

        mStringBuilder.append("\n\n未通过通过的权限：\n");
        for (String permission: denied) {
            mStringBuilder.append(permission + "\n");
        }

        tvContent.setText(mStringBuilder.toString());
    }
}
```
首先需要在AndroidManifest清单文件中去注册该权限（否则会抛异常）,在Delegate中只需调用requestPermission()方法，此时即可对权限进行申请。权限可以输入权限组，也可以传入单个权限，当然，传入多个也是可以的。具体的权限可以参考com.cloud.common.util.permission.PermissionCode类，其中对权限进行解释已经分组。
而申请后的返回结果则回调下面两个方法：

 - onHasPermission()：已经申请成功的权限
 - onNoPermission()：申请失败的权限


## 侧边栏的使用
侧边栏的使用请参考Sample模块中的SampleSlidingDelegate，只需要在layout中使用：
```
 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <com.cloud.common.component.design.C9SlidingMenu
        android:id="@+id/my_slidingMenu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/menu_background"
        app:RightPadding="150dp" >

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:orientation="horizontal" >

            <LinearLayout
                android:id="@+id/layout_menu"
                android:layout_width="100dp"
                android:layout_height="match_parent"
                android:orientation="vertical"
                android:background="@color/transparent"/>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@drawable/img"/>
        </LinearLayout>
    </com.cloud.common.component.design.C9SlidingMenu>

 </RelativeLayout>
```
其中，第一个LinearLayout为侧边栏内容，可以使用include来自定义界面，第二个LinearLayout为主界面
如果要设置侧边栏出现时，主界面是否缩小(默认)，则在代码中使用：
```
mC9SlidingMenu.setScale(true);
```


## 列表的使用
### RecyclerView的使用
RecyclerView的使用复杂度在于对Adapter的创建，这里隐藏了其复杂的实现，而提供了一个抽象类来简化Adapter的实现（BaseRecyclerViewAdapter）。在使用时，只需要对BaseRecyclerViewAdapter进行继承，并传入item的布局以及Context即可：
```
    private class SampleAdapter extends BaseRecyclerViewAdapter<String> {

        public SampleAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(BaseRecyclerViewHolder holder, int position) {
            TextView tvSample = holder.getView(R.id.tv_sample);
            String data = mDataList.get(position);
            tvSample.setText(data);
            tvSample.setOnClickListener(v -> {
                showToast(data);
                Logger.d(data);
                mAdapter.remove(position);
            });
        }
    }
```
继承类中主要重写其构造方法以及convert()方法，主要实现在convert()方法中。
可以通过控件的ID来调用BaseRecyclerViewHolder的getView()方法获取到该控件，并对该控件进行操作。
接着对RecyclerView进行初始化操作：
```
//设置布局
recyclerView.setLayoutManager(new LinearLayoutManager(this.getProxyActivity()));
mAdapter = new SampleAdapter(this.getProxyActivity(), R.layout.list_sample_item);
//设置适配器
recyclerView.setAdapter(mAdapter);
mAdapter.setData(mDataList);
```
其中SampleAdapter为继承自BaseRecyclerViewAdapter的类。
BaseRecyclerViewAdapter提供了几个方法来对数据进行出来：

 - setData(List<T> itemList)：为该列表设置数据 -- 所有数据
 - addData(@Nullable T data)：为列表中的数据继续添加数据 -- 默认将数据添加到最后
 - addData(@IntRange(from = 0) int position, @Nullable T data)：将数据添加到某个下标下
 - remove(int position)：删除列表中某一项

 这里对数据的操作都不需要再调用Adapter的notifyDataSetChanged()方法。

为列表添加分割线时，由于系统并没有提供分割线的类，所以自定义了一个SimpleDividerItemDecoration类。
使用方法比较简单，需要自己创建一个Drawable对象，可以是图片，也可以是XML，例如：
```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <size
        android:width="1dp"
        android:height="1dp" />
    <solid android:color="@color/white" />
</shape>
```
调用：
```
recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getProxyActivity(), 5));
```
支持自定义分割线，可以参考SimpleDividerItemDecoration去继承RecyclerView.ItemDecoration实现自己的分割线类，也可以自定义Drawable（SimpleDividerItemDecoration(Context context, Drawable divider, int dividerHeight)）来实现。

## 抖动效果的使用
在抖动效果中，需要使用插值器来设定某个属性要变化的规则，例如是线性变化，是波浪型变化等等，默认实现了3种插值器，如果要设置不同的效果，最主要的是自定义插值器，可参考默认的插值器即可。
### 使用
```
        C9PropertyValuesHolder holder = new C9PropertyValuesHolder();
        holder.registerInterpolator(new LinearInterpolator(View.SCALE_X));
        holder.registerInterpolator(new LinearInterpolator(View.SCALE_Y));
        holder.registerInterpolator(new ShockInterpolator(View.ROTATION));

        C9Shake.with(holder)
                .duration(1000)
                .repeatCount(2)
                .repeateMode(Animation.RESTART)
                .startShake(ivShake);
```
可以有多种效果重叠在一起组成一个复杂的效果，也可以只有一个，只需要将插值器作为参数注册到C9PropertyValuesHolder中。


## 为ViewPager设置标题条、设置指示点
### 提供多个样式
例子请参考com.cloud.c9fragment.module.delegate.SampleIndicatorDelegate，其中提供了多个样式例子。

## 工具清单
### 图片加载框架
框架中使用了Glide框架来加载图片，主要提供了一个圆形ImageView来装载。
例子可以参考SampleImgDelegate。

### 文件工具
com.cloud.tool.util.file.FileTool

### 尺寸工具类
com.cloud.tool.util.dimen.DimenTool

### 哈希计算工具类：MD5/SHA1/SHA256
com.cloud.tool.util.hash.HashTool

### SharedPreference 文件存储工具类
com.cloud.tool.util.sharedpreference.SpTool

### 计时器
com.cloud.tool.util.timedown.TimeDownTool

### 二维码工具
com.cloud.tool.util.qr.QrTool

### 生成随机数
com.cloud.tool.util.random.RandomTool
