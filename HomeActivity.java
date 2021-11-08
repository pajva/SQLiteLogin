package com.example.sqlitelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button btn1,btn2;
    TextView t1,t3;
    EditText et2,et3;
    DBHelper dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dB = new DBHelper(this);
        t1=findViewById(R.id.tv1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        t3 = findViewById(R.id.tv3);
        btn1 = findViewById(R.id.viewall);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = dB.getAllData();
                if (res.getCount() == 0)
                {
                    showMessage("Error", "Nothing Found");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append("User : "+res.getString(0).toUpperCase()+"\n");
                        buffer.append("Password : "+res.getString(1)+"\n");
                        buffer.append("\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        });
        btn2 = findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = et2.getText().toString();
                if (user.equals("")) {
                    Toast.makeText(HomeActivity.this, "Please enter the username", Toast.LENGTH_SHORT).show();
                }else{
                    Cursor cursor =dB.getData(user);
                    if (cursor.getCount()==0){
                        Toast.makeText(HomeActivity.this, "No User", Toast.LENGTH_SHORT).show();
                    }else{
                        cursor.moveToFirst();
                        t3.setText(cursor.getString(1));
                        et3.setText(cursor.getString(0));

                    }
                }

            }
        });

    }


    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}