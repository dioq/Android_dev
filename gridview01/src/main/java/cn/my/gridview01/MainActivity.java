package cn.my.gridview01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "dlog";

    private GridView gridView;
    private GridAdapter adapter;
    private List<GridItem> itemList;

    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 右按钮需要定制
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.gridView);

        itemList = new ArrayList<>();

        itemList.add(new GridItem(R.drawable.icon, "example1"));
        itemList.add(new GridItem(R.drawable.icon, "example2"));

        adapter = new GridAdapter(this, itemList);
        gridView.setAdapter(adapter); // 加载数据
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_custom) {
            if (!itemList.isEmpty()) {
                Log.d(TAG, "delete item");
                itemList.remove(0);
                gridView.setAdapter(adapter);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void add(View view) {
        Log.d(TAG, "add item");
        itemList.add(new GridItem(R.drawable.icon, "index:" + num));
        gridView.setAdapter(adapter);
        num++;
    }
}