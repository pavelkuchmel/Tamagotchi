package com.example.tamagotchi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  Removable{

    //Button newPlayer = findViewById(R.id.btn_newPlayer);
    //Button selectPlayer = findViewById(R.id.btn_selectPlayer);
    //Button start = findViewById(R.id.btn_start);
    //EditText enterName = findViewById(R.id.ed_name);
    //Spinner spinnerSelect = findViewById(R.id.spinner_select);
    Tamagotchi current;
    List<Tamagotchi> arrayTamagotchi = new ArrayList<Tamagotchi>(Arrays.asList(new Tamagotchi("Test1"), new Tamagotchi("Test2"), new Tamagotchi("Test3"), new Tamagotchi("Test4"), new Tamagotchi("Test5")));
    ArrayAdapter <Tamagotchi> adapter;
    private final int MAX_PLAYERS = 10;
    private final String DISPLAY_MAX_PLAYERS = "Достигнуто максимальное количество игроков";
    private int selectNumPlayer;
    private int countClickVersion = 0;
    TextView testText;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    //TextView textView = findViewById(R.id.textView);
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        Tamagotchi tamagotchi = intent.getParcelableExtra("TestResult");
                        arrayTamagotchi.set(selectNumPlayer, tamagotchi);
                        testText.setText(tamagotchi.toString());
                    }
                    else{
                        //textView.setText("Ошибка доступа");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPlayer = findViewById(R.id.btn_newPlayer);
        Button selectPlayer = findViewById(R.id.btn_selectPlayer);
        Button start = findViewById(R.id.btn_start);
        Button deletePlayer = findViewById(R.id.btn_deletePlayer);
        Button deletePlayerSecond = findViewById(R.id.btn_deletePlayerSecond);
        Button select = findViewById(R.id.btn_select);
        EditText enterName = findViewById(R.id.ed_name);
        Spinner spinnerSelect = findViewById(R.id.spinner_select);
        Spinner spinnerSelectDelete = findViewById(R.id.spinner_selectDelete);
        TextView nameInfo = findViewById(R.id.nameInfo);
        TextView version = findViewById(R.id.tv_version);
        testText = findViewById(R.id.testText);
        ImageButton setting = findViewById(R.id.btn_setting);
        ImageButton login = findViewById(R.id.btn_login);
        ConstraintLayout displayLogin = findViewById(R.id.display_login);
        ConstraintLayout displaySetting = findViewById(R.id.display_setting);

        //Tamagotchi current;
        //ArrayList<Tamagotchi> arrayTamagotchi;
        //Tamagotchi[] test1 = {new Tamagotchi("Test1"), new Tamagotchi("Test2"), new Tamagotchi("Test3"), new Tamagotchi("Test4"), new Tamagotchi("Test5")};

        ImageView animLoading = findViewById(R.id.anim_loading);
        animLoading.setBackgroundResource(R.drawable.loading_animation);
        AnimationDrawable frameAnimationLoading = (AnimationDrawable) animLoading.getBackground();
        frameAnimationLoading.start();

        adapter = new ArrayAdapter<Tamagotchi>(this, android.R.layout.simple_spinner_item, arrayTamagotchi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener choicePlayer = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectNumPlayer = i;
                testText.setText(adapterView.getItemAtPosition(i).toString() + " " + selectNumPlayer + " ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        AdapterView.OnItemSelectedListener choicePlayerDelete = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Tamagotchi tamagotchi = adapter.getItem(i);
                DeletePlayerQuestion dialog = new DeletePlayerQuestion();
                Bundle bundle = new Bundle();
                bundle.putParcelable("tamagotchi", tamagotchi);
                bundle.putParcelableArrayList("arrayTamagotchi", (ArrayList)arrayTamagotchi);
                dialog.setArguments(bundle);
                deletePlayerSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show(getSupportFragmentManager(), "custom");
                    }
                });
                //testText.setText(adapter.getItem(i).getBornDate().getTime().toString()); //обратить внимание, не adapterView, a adapter - имя адаптера
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        /*AdapterView.OnItemClickListener choicePlayer = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                current = (Tamagotchi)parent.getItemAtPosition(i);
                testText.setText((String)parent.getItemAtPosition(i));
                start.setVisibility(View.VISIBLE);
            }
        };*/

        spinnerSelect.setAdapter(adapter);
        spinnerSelect.setOnItemSelectedListener(choicePlayer);
        spinnerSelectDelete.setAdapter(adapter);
        spinnerSelectDelete.setOnItemSelectedListener(choicePlayerDelete);

        newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.setVisibility(View.GONE);
                spinnerSelect.setVisibility(View.GONE);
                if (arrayTamagotchi.size() <= MAX_PLAYERS) {
                    enterName.setVisibility(View.VISIBLE);
                    if (enterName.getText().length() != 0){
                        start.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, DISPLAY_MAX_PLAYERS, Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);
                enterName.setVisibility(View.GONE);
                spinnerSelect.setVisibility(View.VISIBLE);
                nameInfo.setVisibility(View.GONE);
            }
        });

        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (arrayTamagotchi.size() >= MAX_PLAYERS) {
                    Toast.makeText(MainActivity.this, DISPLAY_MAX_PLAYERS, Toast.LENGTH_SHORT).show();
                    start.setVisibility(View.GONE);
                }
                statusBottomStart(testText, charSequence, start, nameInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    current = new Tamagotchi(enterName.getText().toString());
                    arrayTamagotchi.add(current);
                    Tamagotchi test = current;
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("Test", test);
                    mStartForResult.launch(intent);
                    //startActivity(intent);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = arrayTamagotchi.get(selectNumPlayer);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("Test", current);
                mStartForResult.launch(intent);
                //startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLogin.setVisibility(View.GONE);
                displaySetting.setVisibility(View.VISIBLE);
            }
        });

        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerSelectDelete.setVisibility(View.VISIBLE);
                deletePlayerSecond.setVisibility(View.VISIBLE);
            }
        });

        /*deletePlayerSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLogin.setVisibility(View.VISIBLE);
                displaySetting.setVisibility(View.GONE);
            }
        });

        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClickVersion++;
                Toast.makeText(MainActivity.this, countClickVersion+"", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void statusBottomStart(TextView testText, CharSequence charSequence, Button start, TextView nameInfo){
        switch (arrayTamagotchi.size()) {
            case 0:

                break;
            case 1:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 3:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 4:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 5:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 6:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(5).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 7:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(5).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(6).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 8:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(5).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(6).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(7).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 9:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(5).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(6).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(7).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(8).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
            case 10:
                if (charSequence.length() != 0){
                    if (arrayTamagotchi.get(0).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(1).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(2).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(3).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(4).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(5).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(6).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(7).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(8).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else if (arrayTamagotchi.get(9).getName().equals(charSequence.toString())){
                        nameInfo.setVisibility(View.VISIBLE);
                        start.setVisibility(View.GONE);
                    }
                    else {
                        start.setVisibility(View.VISIBLE);
                        nameInfo.setVisibility(View.GONE);
                    }
                }
                else {
                    nameInfo.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
                break;
        }

        /*String str = charSequence.toString();
        String bool = String.valueOf(arrayTamagotchi.contains(new Tamagotchi(str)));
        if (str.length() != 0) {
            if (arrayTamagotchi.contains(new Tamagotchi(str))*//*charSequence.toString().equals(arrayTamagotchi.get(0).getName())*//*){
                nameInfo.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);
            }
            else {
                start.setVisibility(View.VISIBLE);
                nameInfo.setVisibility(View.GONE);
            }
        }
        else {
            nameInfo.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
        }
        testText.setText(charSequence + " " + bool);*/
    }

    @Override
    public void remove(Tamagotchi name) {
        adapter.remove(name);
    }
}