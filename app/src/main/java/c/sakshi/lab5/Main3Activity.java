package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    private Button saveButton;
    int noteid = -1;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.editText);
        noteid = getIntent().getIntExtra("noteid", -1);

        if(noteid != -1){
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }

        saveButton = (Button) findViewById(R.id.button2);
        saveButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        EditText myTextField = (EditText) findViewById(R.id.editText);
        content = myTextField.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1){
            title = "NOTE_" + (Main2Activity.notes.size()+1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid+1);
            dbHelper.updateNote(title,date, content ,username);
        }

        goToActivity2();
    }

    public void goToActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
