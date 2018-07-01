package com.example.root.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editt;
    Button btn2;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editt = (EditText)findViewById(R.id.et2) ;
        String valfromMain = getIntent().getStringExtra("textedit");
        editt.setText(valfromMain);
        if (getIntent().getExtras() != null){
            position = getIntent().getIntExtra("pos",0);
        }
    }

    public void onSubmit(View v) {
        Intent data = new Intent();
        data.putExtra("editte", editt.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
        this.finish();
    }

    public void onCancel(View view){
        this.finish();
    }


}
