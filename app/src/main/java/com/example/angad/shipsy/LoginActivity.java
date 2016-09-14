package com.example.angad.shipsy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.angad.shipsy.Event.CustomLog;
import com.example.angad.shipsy.Event.CustomMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG =LoginActivity.class.getSimpleName() ;
    EditText userName;
    EditText passWord;
    Button mButton;
    PercentRelativeLayout mLayout;
    TextView mTextView;
    FragmentView fragmentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        userName = (EditText) findViewById(R.id.et_user_label);
        passWord = (EditText) findViewById(R.id.et_pass_label);
        mButton = (Button) findViewById(R.id.bt_button);
        mLayout = (PercentRelativeLayout) findViewById(R.id.per_root);
        mTextView = (TextView) findViewById(R.id.tv_forgot_label);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String pass = passWord.getText().toString().trim();
                if ((name.matches("") || name.isEmpty()) && (pass.matches("") || pass.isEmpty())) {
                    Snackbar.make(mLayout, "Both the fields are empty, please fill them", Snackbar.LENGTH_LONG).show();
                } else if (name.matches("") || name.isEmpty()) {
                    Snackbar.make(mLayout, "Username is empty ,please enter the username", Snackbar.LENGTH_LONG).show();
                } else if (pass.matches("") || pass.isEmpty()) {
                    Snackbar.make(mLayout, "Password is empty ,please enter the password", Snackbar.LENGTH_LONG).show();
                } else {
                    //Snackbar.make(mLayout, "Login successful", Snackbar.LENGTH_LONG).show();
                    Intent i=new Intent(LoginActivity.this,Dummy.class);
                    CustomMessageEvent event=new CustomMessageEvent();
                    event.setIntent(i);
                    EventBus.getDefault().post(event);

                }
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setContentView(R.layout.activity_main);
                fragmentView = new FragmentView();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(android.R.id.content, fragmentView, "a");
                transaction.commit();


            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.getCurrentFocus();
        hideKeyboard(view);
        return super.onTouchEvent(event);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    public void onBackPressed() {
        Fragment f = getFragmentManager().findFragmentByTag("a");
        Log.d("A","1111"+f);
        if (f instanceof FragmentView) {
            getFragmentManager().beginTransaction().remove(f).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    @Subscribe
    public void onEvent(CustomMessageEvent event) {
        startActivity(event.getIntent());

    }
    @Subscribe
    public void onEvent(CustomLog event) {
        Log.d(TAG,event.getLog());

    }


}




