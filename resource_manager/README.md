# 资源

资源通常来自 res 和 assets 两个目录,表面上看起来似乎是不同的加载方式,内部实际上会先拿到 ResourcesImpl 中的 AssetManager 再去获取相应资源

## 两种加载资源方式区别

Resources 主要用来访问被编译过的应用资源文件,在访问这些文件之前,会先根据资源 ID 查找得到对应的资源文件名
AssetManager 既可以通过文件名访问那些被编译过的,也可以访问没有被编译过的资源文件
两种加载方式的不同其实也映射出 res 和 assets 两个资源目录的差别：

res：系统会为 res 目录下的所有资源文件生成一个 ID,这意味着很容易就可以访问到这个资源,甚至在 xml
中都是可以访问的,使用 ID 访问的速度是最快的
assets：不会生成 ID,只能通过 AssetManager 访问,xml 中不能访问,访问速度会慢些,不过操作更加方便

## assets 中资源

目录	            资源类型
anim/	        定义动画属性的XML文件。它们被保存在res/anim/文件夹下,通过R.anim类访问
color/	        定义颜色状态列表的XML文件。它们被保存在res/color/文件夹下,通过R.color类访问
drawable/	    图片文件,如.png,.jpg,.gif或者XML文件,被编译为位图、状态列表、形状、动画图片。它们被保存在res/drawable/文件夹下,通过R.drawable类访问
layout/	        定义用户界面布局的XML文件。它们被保存在res/layout/文件夹下,通过R.layout类访问
menu/	        定义应用程序菜单的XML文件,如选项菜单,上下文菜单,子菜单等。它们被保存在res/menu/文件夹下,通过R.menu类访问
raw/	        任意的文件以它们的原始形式保存。需要根据名为R.raw.filename的资源ID,通过调用Resource.openRawResource()来打开raw文件
values/	        包含简单值(如字符串,整数,颜色等)的XML文件。这里有一些文件夹下的资源命名规范。arrays.xml代表数组资源,通过R.array类访问；integers.xml代表整数资源,通过R.integer类访问；bools.xml代表布尔值资源,通过R.bool类访问；colors.xml代表颜色资源,通过R.color类访问；dimens.xml代表维度值,通过R.dimen类访问；strings.xml代表字符串资源,通过R.string类访问；styles.xml代表样式资源,通过R.style类访问
xml/	        可以通过调用Resources.getXML()来在运行时读取任意的XML文件。可以在这里保存运行时使用的各种配置文件
