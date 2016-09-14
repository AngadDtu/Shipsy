package com.example.angad.shipsy;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.angad.shipsy.Event.CustomLog;

import org.greenrobot.eventbus.EventBus;

public class FragmentView extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayout layout=(LinearLayout)view.findViewById(R.id.ll_root);
        Button mButton=(Button)view.findViewById(R.id.bt_button);
        final EditText mEditText=(EditText)view.findViewById(R.id.et_riderLabel);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditText.getText().toString().trim();
                if (name.matches("") || name.isEmpty()) {
                    Snackbar.make(layout, "Rider Id is empty,please enter the id", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(layout, "Password send to rider id " + name, Snackbar.LENGTH_LONG).show();
                    CustomLog log=new CustomLog();
                    log.setLog(name);
                    EventBus.getDefault().post(log);
                }
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment, container, false);
        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.frag_menu,menu);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Log.i("Back",String.valueOf(id));
        if(id==android.R.id.home){
            Log.i("Back","Back from child activity to parent");
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }





}
