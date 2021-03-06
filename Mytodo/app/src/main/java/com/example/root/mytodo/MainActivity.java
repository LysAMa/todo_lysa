package com.example.root.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lv;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        lv = (ListView)findViewById(R.id.lv);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(itemsAdapter);
        setupListViewListener();
        editListViewListener();

    }
    public void setupListViewListener(){
        lv.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    public void editListViewListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(MainActivity.this, EditItemActivity.class);
                String itempos = lv.getItemAtPosition(i).toString();
                intent.putExtra("textedit", itempos);
                intent.putExtra("pos", i);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.et);
        String itemtext = etNewItem.getText().toString();
        itemsAdapter.add(itemtext);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
        writeItems();
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String theitem = data.getExtras().getString("editte");
            int pos = data.getExtras().getInt("position");
            try{
                if(!theitem.isEmpty() && theitem.length()>0){

                    items.set(pos, theitem);
                    writeItems();
                    lv.invalidate();
                    lv.setAdapter(itemsAdapter);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Toast.makeText(this, theitem, Toast.LENGTH_SHORT).show();
        }
    }

}
