package com.example.andriodchapterprojects;


import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        setForEditing(false);
        initChangeDateButton();

        changeScreen();
    }
    private void initListButton(){
        ImageButton ibList=findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ContactListActivty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initMapButton(){
        ImageButton ibList=findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton(){
        ImageButton ibList=findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initToggleButton(){
        final ToggleButton edit=(ToggleButton)findViewById(R.id.toggleButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForEditing(edit.isChecked());
            }
        });
    }
    private void setForEditing(boolean enabled){
        EditText editName=findViewById(R.id.editName);
        EditText editAddress=findViewById(R.id.editAddress);
        EditText editCity=findViewById(R.id.editCity);
        EditText editState=findViewById(R.id.editState);
        EditText editZipcode=findViewById(R.id.editZipcode);
        EditText phone=findViewById(R.id.editHome);
        EditText cell=findViewById(R.id.editCell);
        EditText editEmail=findViewById(R.id.editEmail);
        Button buttonchange=findViewById(R.id.btnBirthday);
        Button buttonsave=findViewById(R.id.savebutton);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipcode.setEnabled(enabled);
        phone.setEnabled(enabled);
        cell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonchange.setEnabled(enabled);
        buttonsave.setEnabled(enabled);
        if(enabled){
            editName.requestFocus();
        }

    }
    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.btnBirthday);
        changeDate.setOnClickListener(v -> {
            // Get the current date as the default selected date
            Calendar calendar = Calendar.getInstance();
            // Pass the current date to the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(calendar);
            datePickerDialog.show(getSupportFragmentManager(), "date_picker");
        });
    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        // Set the selected date in your TextView
        TextView birthDay = findViewById(R.id.textBirthday);
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
    }



    private void changeScreen() {
        Button b = findViewById(R.id.savebutton);
        b.setOnClickListener(l -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}