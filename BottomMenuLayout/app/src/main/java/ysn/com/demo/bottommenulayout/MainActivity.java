package ysn.com.demo.bottommenulayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MenuItemBuilder builder = new MenuItemBuilder(this)
                .text("ss")
                .normalIcon(R.mipmap.ic_launcher);
        builder.create();
        BottomMenuLayout menuLayout = new BottomMenuLayout(this);
        menuLayout.addItem(builder.create());
//        menuLayout.setViewPager1();
    }
}
