package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private TextView mTvListBadge;
    private MenuItem mMiAbnormal;
    private PopupMenu mPopupMenu;
    private boolean a = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a ^= true;
                setAbnormalBadge(a);
                if (bt.getText().toString() == getResources().getString(R.string.bt_abnormal_status))
                    bt.setText(R.string.bt_not_abnormal_status);
                else
                    bt.setText(R.string.bt_abnormal_status);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mMiAbnormal = menu.findItem(R.id.item_list);
        View avItemList = mMiAbnormal.getActionView();
        mTvListBadge = (TextView) avItemList.findViewById(R.id.tv_list_badge);

        avItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(mMiAbnormal);
            }
        });

        mPopupMenu = new PopupMenu(MainActivity.this, avItemList);
        mPopupMenu.getMenuInflater().inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onOptionsItemSelected(item);
                return true;
            }
        });
        try {
            Field field = mPopupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(mPopupMenu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_list:
                Toast.makeText(this, "list clicked", Toast.LENGTH_SHORT).show();
                mPopupMenu.show();
                break;
            case R.id.item_abnormal_status:
                Toast.makeText(this, "abnormal!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_reload_data:
                Toast.makeText(this, "reload!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_setting_rf_power:
                Toast.makeText(this, "item_setting_rf_power!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_setting_inventory_beep:
                Toast.makeText(this, "item_setting_inventory_beep!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAbnormalBadge(boolean show) {
        MenuItem item = mPopupMenu.getMenu().findItem(R.id.item_abnormal_status);
        if (show) {
            mTvListBadge.setVisibility(View.VISIBLE);
            item.setIcon(R.drawable.ic_action_abnormal_red);
        } else {
            mTvListBadge.setVisibility(View.GONE);
            item.setIcon(R.drawable.ic_action_abnormal);
        }
    }
}