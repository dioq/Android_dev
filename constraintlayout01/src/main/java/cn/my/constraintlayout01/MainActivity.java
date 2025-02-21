package cn.my.constraintlayout01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends Activity {

    private static final String TAG = "dlog";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;

//        setLayout();
        setLayout2();
    }

    @SuppressLint("SetTextI18n")
    private void setLayout() {
        // 创建约束布局
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        // 创建按钮
        Button button = new Button(context);
        button.setText("Button");
        // 添加按钮到约束布局
        constraintLayout.addView(button);

        // 创建约束布局参数
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        // 设置按钮的约束参数
        button.setLayoutParams(params);

        // 添加约束关系，使按钮居中
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;

        // 更新布局
        constraintLayout.requestLayout();

        setContentView(constraintLayout);

        // 点击事件
        ButtonListener buttonListener = new ButtonListener();
        button.setOnClickListener(buttonListener);
    }

    public void setLayout2() {
        // Create a ConstraintLayout
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        // Create a TextView
        TextView textView = new TextView(this);
        textView.setId(TextView.generateViewId());
        textView.setText("Hello, ConstraintLayout!");
        textView.setTextSize(18);

        // Create a Button
        Button button = new Button(this);
        button.setId(Button.generateViewId());
        button.setText("Click Me");
//        button.setOnClickListener(v ->
//                Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show()
//        );
        // 点击事件
        ButtonListener buttonListener = new ButtonListener();
        button.setOnClickListener(buttonListener);

        // Add TextView and Button to the ConstraintLayout
        constraintLayout.addView(textView);
        constraintLayout.addView(button);

        // Create ConstraintSet and apply constraints
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.connect(button.getId(), ConstraintSet.TOP, textView.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(button.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.applyTo(constraintLayout);

        // Set the ConstraintLayout as the content view
        setContentView(constraintLayout);
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "click");
            Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
