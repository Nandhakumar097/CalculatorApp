package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import butterknife.BindView;

import static android.icu.util.IslamicCalendar.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,HistoryAdapter.ItemListener {

    TextView one,two,three,four,five,six,seven,eight,nine,plus,
            minus,multiply,divide,dot,zero,result,equal,ac,back,total,clearHistory;
    javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("rhino");
    ImageView history;
    LinearLayout numberLinear;
    RecyclerView recyclerview;
    List<HistoryData> historyData=new ArrayList<>();
    List<HistoryData> userHistoryData=new ArrayList<>();

    String tempKey,expStr,totalStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        multiply=findViewById(R.id.multiply);
        divide=findViewById(R.id.divide);
        dot=findViewById(R.id.dot);
        zero=findViewById(R.id.zero);
        result=findViewById(R.id.result);
        equal=findViewById(R.id.equal);
        ac=findViewById(R.id.ac);
        back=findViewById(R.id.back);
        history=findViewById(R.id.history);
        numberLinear=findViewById(R.id.numberLinear);
        recyclerview=findViewById(R.id.recyclerview);
        total=findViewById(R.id.total);
        clearHistory=findViewById(R.id.clearHistory);
        clearHistory.setOnClickListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        dot.setOnClickListener(this);
        zero.setOnClickListener(this);
        result.setOnClickListener(this);
        equal.setOnClickListener(this);
        ac.setOnClickListener(this);
        back.setOnClickListener(this);
        history.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                result.setText(result.getText().toString()+"1");
                one.setTextColor(getBaseContext().getColor(R.color.gold));
                android.os.Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        one.setTextColor(getBaseContext().getColor(R.color.white));
                    }
                }, 200);
                break;
            case R.id.two:
                result.setText(result.getText().toString()+"2");
                indicate(two);
                break;
            case R.id.three:
                result.setText(result.getText().toString()+"3");
                indicate(three);
                break;
            case R.id.four:
                result.setText(result.getText().toString()+"4");
                indicate(four);
                break;
            case R.id.five:
                result.setText(result.getText().toString()+"5");
                indicate(five);
                break;
            case R.id.six:
                result.setText(result.getText().toString()+"6");
                indicate(six);
                break;
            case R.id.seven:
                result.setText(result.getText().toString()+"7");
                indicate(seven);
                break;
            case R.id.eight:
                result.setText(result.getText().toString()+"8");
                indicate(eight);
                break;
            case R.id.nine:
                result.setText(result.getText().toString()+"9");
                indicate(nine);
                break;
            case R.id.zero:
                result.setText(result.getText().toString()+"0");
                indicate(zero);
                break;
            case R.id.dot:
                result.setText(result.getText().toString()+".");
                indicate(dot);
                break;
            case R.id.plus:
                result.setText(result.getText().toString()+"+");
                indicate(plus);
                break;
            case R.id.minus:
                result.setText(result.getText().toString()+"-");
                indicate(minus);
                break;
            case R.id.multiply:
                result.setText(result.getText().toString()+"x");
                indicate(multiply);
                break;
            case R.id.divide:
                result.setText(result.getText().toString()+"/");
                indicate(divide);
                break;
            case R.id.ac:
                result.setText("");
                total.setText("");
                indicateWhite(ac);
                break;
            case R.id.back:
                if (!result.getText().toString().isEmpty())
                result.setText(result.getText().toString().substring(0,result.getText().toString().length()-1));
                indicateWhite(back);
                break;

            case R.id.equal:
                calculate(result.getText().toString());
                equal.setBackgroundResource(R.drawable.equal_bg_green);
                android.os.Handler h2 = new Handler();
                h2.postDelayed(new Runnable() {
                    public void run() {
                        equal.setBackgroundResource(R.drawable.equal_bg);
                    }
                }, 200);

                break;
            case R.id.history:
                if (numberLinear.getVisibility()==View.VISIBLE){
                    numberLinear.setVisibility(View.INVISIBLE);
                    history.setBackgroundResource(R.drawable.back);
                    recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
                    HistoryAdapter historyAdapter=new HistoryAdapter(historyData,MainActivity.this,MainActivity.this);
                    recyclerview.setAdapter(historyAdapter);
                    recyclerview.setVisibility(View.VISIBLE);
                    clearHistory.setVisibility(View.VISIBLE);

                    if (historyData.isEmpty()){
                        clearHistory.setText("No History");
                    }else {
                        clearHistory.setText("Clear History");

                    }
                }else {
                    numberLinear.setVisibility(View.VISIBLE);
                    history.setBackgroundResource(R.drawable.ic_baseline_history_24);
                    recyclerview.setVisibility(View.GONE);
                    clearHistory.setVisibility(View.GONE);

                }
                break;
            case R.id.clearHistory:

                historyData.removeAll(historyData);
                recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
                HistoryAdapter historyAdapter=new HistoryAdapter(historyData,MainActivity.this,MainActivity.this);
                recyclerview.setAdapter(historyAdapter);
//                clearHistory.setVisibility(View.GONE);
                clearHistory.setText("No History");

                break;
        }
    }

    private void indicate(final TextView textView) {
        textView.setTextColor(getBaseContext().getColor(R.color.gold));
        android.os.Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                textView.setTextColor(getBaseContext().getColor(R.color.white));
            }
        }, 200);
    }
    private void indicateWhite(final TextView textView) {
        textView.setTextColor(getBaseContext().getColor(R.color.white));
        android.os.Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                textView.setTextColor(getBaseContext().getColor(R.color.gold));
            }
        }, 200);
    }
    //mads
    //BODMAS
    private void calculate(String val) {

        String[] sub=val.split("-");
        List<String> data=new ArrayList<>();
            for (int i=0;i<sub.length;i++){
                if (sub[i].contains("/")){
                    String[] div=sub[i].split("/");
                        String conDiv="";
                    for (int j=0;j<div.length;j++){
                       if (j==0){
                           try {
                               conDiv=engine.eval(div[j].replace("x","*")).toString();
                           } catch (Exception e) {
                               e.printStackTrace();
                               Toast.makeText(this, "Wrong Expression", Toast.LENGTH_SHORT).show();
                           }


                       }else {
                           try {
                               conDiv+="/"+engine.eval(div[j].replace("x","*")).toString();
                           } catch (Exception e) {
                               e.printStackTrace();
                               Toast.makeText(this, "Wrong Expression", Toast.LENGTH_SHORT).show();
                           }
                       }
                    }
                    try {
                        data.add(engine.eval(conDiv.replace("x","*")).toString()) ;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Wrong Expression", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    String str="";
                    try {
                         str=engine.eval(sub[i].replace("x","*")).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Wrong Expression", Toast.LENGTH_SHORT).show();
                    }
                    data.add(str);
                }
            }
            String minus="";
            for (int z=0;z<data.size();z++){
                if (z==0){
                    minus=data.get(z);}else {


                minus+="-"+data.get(z);}
            }

            Log.d("minus",minus);
        String total="";
        try {
             total=engine.eval(minus.replace("x","*")).toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Wrong Expression", Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this, "total - "+total, Toast.LENGTH_SHORT).show();

        this.total.setText(total);
        historyData.add(new HistoryData(val,total));
        Log.d("historyData",historyData.toString());

    }

    @Override
    public void onFSelected(HistoryData data) {
        this.result.setText(data.getExpression());
        this.total.setText(data.getTotal());

    }
    private void appendChatConversation(DataSnapshot snapshot) {

        Iterator iterator=snapshot.getChildren().iterator();
        userHistoryData.containsAll(userHistoryData);
        while (iterator.hasNext()){
            expStr=(String) ((DataSnapshot)iterator.next()).getValue();
            totalStr=(String) ((DataSnapshot)iterator.next()).getValue();
            userHistoryData.add(new HistoryData(expStr,totalStr));

        }

//        charRoomRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        MessageAdapter messageAdapter=new MessageAdapter(ChatActivity.this,messageData,userName);
//        charRoomRecyclerview.setAdapter(messageAdapter);


    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to Logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_warning)
                .create()
                .show();


    }

}