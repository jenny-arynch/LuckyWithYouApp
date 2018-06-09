package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;

public class WebForumActivity  extends AppCompatActivity implements View.OnClickListener{

    private WebView myWebView;
    private AppCompatButton back;
    private String userEmail;
    private String userPassword;
    private FireBaseHelper firebaseData;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webforum);
        setEmail(savedInstanceState);
        firebaseData=new FireBaseHelper(this.getApplicationContext());
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        initViews();
        initListeners();


        myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("http://evgenar-001-site1.gtempurl.com/");
        myWebView.setWebViewClient(new WebViewClient());
        getSupportActionBar().hide();//new
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    private void initListeners() {

        back.setOnClickListener(this);
    }
    private void setEmail(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userEmail= null;
                userPassword=null;
            } else {
                userEmail= extras.getString("EMAIL");
                userPassword= extras.getString("PASSWORD");
            }
        } else {
            userEmail= (String) savedInstanceState.getSerializable("EMAIL");
            userPassword= (String) savedInstanceState.getSerializable("PASSWORD");
        }
    }
    public void onClick(View v) {
        Intent intentRegister = null;

        switch (v.getId()) {
            case R.id.back:
            {
                intentRegister = new Intent(getApplicationContext(), MainActivity.class);
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                intentRegister.putExtra("currentUser", currentUser);

                startActivity(intentRegister);
                break;
            }
        }

    }
    private void initViews() {
        back = (AppCompatButton) findViewById(R.id.back);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}