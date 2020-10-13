package com.StackBuffers.baqala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class StoreInfoActivity extends AppCompatActivity {

    Toolbar mToolBar;

    String sname, cname, sphone, saddress;
    TextView sName, cName, sPhone, sAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        //getSupportActionBar().hide();
        sName = findViewById(R.id.store_name_view);
        cName = findViewById(R.id.client_name_view);
        sPhone = findViewById(R.id.number_view);
        sAddress = findViewById(R.id.address_view);

        sname = getIntent().getExtras().get("sname").toString();
        cname = getIntent().getExtras().get("cname").toString();
        sphone = getIntent().getExtras().get("sphone").toString();
        saddress = getIntent().getExtras().get("saddress").toString();


        mToolBar = findViewById(R.id.storeinfo_Toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Store Info");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        sName.setText(sname);
        cName.setText(cname);
        sPhone.setText(sphone);
        sAddress.setText(saddress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
