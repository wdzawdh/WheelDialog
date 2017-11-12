## 这是一个仿IOS的Android滚轮选择控件


### 效果图
<img src="http://otjav6lvw.bkt.clouddn.com/17-8-5/32537013.jpg" width="300"/>

### 使用方法
>setLabels方法用来设置选项。

```
WheelDialog wheelDialog = new WheelDialog(this);
wheelDialog.setLabels(list);
wheelDialog.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
    @Override
    public void onClickOk(int index, String selectLabel) {
        Toast.makeText(getApplicationContext(), selectLabel, Toast.LENGTH_SHORT).show();
    }
});
wheelDialog.show();
```
