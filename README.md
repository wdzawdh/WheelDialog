## 这是一个仿IOS的Android滚轮选择控件


### 效果图
<img src="http://otjav6lvw.bkt.clouddn.com/17-8-5/32537013.jpg" width="300"/>

### 使用方法
>setLabels方法用来设置选项。

```
WheelDialog wheelDialog = new WheelDialog(this);
wheelDialog.setLabels(list);
wheelDialog.setOnWheelSelectListener(
                 new WheelDialog.OnWheelSelectListener() {
    @Override
    public void onClickOk(int index, String selectLabel) {
        Toast.makeText(getApplicationContext(), selectLabel
        , Toast.LENGTH_SHORT).show();
    }
});
wheelDialog.show();
```

### 关键代码
#### 1.控制Dialog宽度充满屏幕并且在屏幕底部
```
//在Dialog构造中设置window属性
Window window = getWindow();
window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
WindowManager.LayoutParams params = window.getAttributes();
params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//设置dialog宽度充满屏幕
window.getDecorView().setPadding(0, 0, 0, 0);
params.width = WindowManager.LayoutParams.MATCH_PARENT;
params.height = WindowManager.LayoutParams.WRAP_CONTENT;
window.setAttributes(params);
```
#### 2.滚轮使用ListView实现
> 使用ListView实现要克服几个问题。</br>
1.不管怎么滑动，最终item都要两条线之间。</br>
2.要保证所有item都能滚动到两线之间。</br>

#### 问题一：保证不管怎么滑动，最终item都要两条线之间
```
/**
 * 在onScrollStateChanged中调用,监听滚动事件
 */
private void refreshState(int scrollState) {
    //监听SCROLL_STATE_IDLE滑动停止事件
    if (scrollState == SCROLL_STATE_IDLE) {
        View itemView = getChildAt(0);
        if (itemView == null) {
            return;
        }
        float deltaY = itemView.getY();
        if (deltaY == 0) {
            return;
        }
        //控制item滚动到两线之间
        if (Math.abs(deltaY) < mItemHeight / 2) {
            smoothScrollBy(getDistance(deltaY), 50);
        } else {
            smoothScrollBy(getDistance(mItemHeight + deltaY), 50);
        }
    }
}
/**
 * 用于逐渐滑动到目标位置，参数scrollDistance越小返回值越小，滑动越慢。
 * 直到返回0，那么smoothScrollBy(0)就不会再引起refreshState的循环调用。
 */
private int getDistance(float scrollDistance) {
    if (Math.abs(scrollDistance) <= 2) {
        return (int) scrollDistance;
    } else if (Math.abs(scrollDistance) < 12) {
        return scrollDistance > 0 ? 2 : -2;
    } else {
        return (int) (scrollDistance / 6);
    }
}
```

#### 问题二：保证所有item都能滚动到两线之间
>WheelListView.WHEEL_SIZE是滚轮展示的item数，默认是5。</br>
前后添加各添加两个辅助的item来保证所有item都能滚动到两线之间。

```
class WheelAdapter extends BaseAdapter {

    private List<String> mData = new ArrayList<>();
    ...
    @Override
    public int getCount() {
        //这里多加了4个辅助的item是为了前后的item都能滚动到两线之间。
        return mData.size() + WheelListView.WHEEL_SIZE - 1;
    }

    @Override
    public String getItem(int position) {
        return mData.get(position - WheelListView.WHEEL_SIZE / 2);
    }
    ...
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.widget_wheel_item, null);
        }
        TextView textView = (TextView) convertView
                  .findViewById(R.id.tv_label_item_wheel);

        int start = WheelListView.WHEEL_SIZE / 2;
        int end = mData.size() + WheelListView.WHEEL_SIZE / 2 - 1;
        if (position < start || position > end) {
            //范围外的是辅助的item，不显示
            convertView.setVisibility(View.INVISIBLE);
        } else {
            textView.setText(getItem(position));
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
```
