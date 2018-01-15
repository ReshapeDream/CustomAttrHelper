# CustomAttrHelper
android 自定义属性帮助类

使用步骤</br>
1.[引入](https://jitpack.io/#ReshapeDream/CustomAttrHelper/v1.0.3)</br>
```
Step 1. Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.ReshapeDream:CustomAttrHelper:v1.0.3'
	}
```
2.在attr.xml自定义属性</br>
```
	<item name="test1" format="float"/>
	<item name="test2" format="string"/>
	<item name="test3" format="int"/>
	<item name="test4" format="color"/>
	<item name="test5" format="boolean"/>
```
3.在布局文件中使用自定义属性</br>
4.在Activity或Fragment中替换LayoutInflater
```
//新建个自定义LayoutInflater
CustomAttrsLayoutInflater inflater=new CustomAttrsLayoutInflater(LayoutInflater.from(this), this);
//设置自定义属性
CustomAttr[] customAttrs = new CustomAttr[]{
        new CustomAttr(R.attr.test1, 0f),
        new CustomAttr(R.attr.test2, ""),
        new CustomAttr(R.attr.test3, 0),
        new CustomAttr(R.attr.test4, false),
        new CustomAttr(R.attr.test5, 0)
};
//给自定义的LayoutInflater添加要监控的属性集合
inflater.setCustomAttrs(customAttrs);
View inflate = inflater.inflate(R.layout.custom_attr_demo, null);
setContentView(inflate);
```
5.使用自定义的属性
```
ArrayList<CustomAttr> tag = (ArrayList<CustomAttr>) view.getTag(CustomAttrsLayoutInflater.getTagId());
	if(tag==null||tag.size()==0){
	    return;
	}
	for (CustomAttr customAttr : tag) {
	    switch (customAttr.attrId) {
		case R.attr.test1:
		    float f=customAttr.getFloatValue();
		    //TodoSomething
		    break;
		case R.attr.test2:
		    String str=customAttr.getStringValue();
		    //TodoSomething
		    break;
		case R.attr.test3:
		    int i=customAttr.getIntValue();
		    //TodoSomething
		    break;
		case R.attr.test4:
		    int color=customAttr.getIntValue();
		    //TodoSomething
		    break;
		case R.attr.test5:
		    boolean b=customAttr.getBooleanValue();
		    //TodoSomething
		    break;
	    }
	}
```

