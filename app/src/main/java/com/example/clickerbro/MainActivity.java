package com.example.clickerbro;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static String KEY_COUNT_GOLD = "COUNT_GOLD";
    int count_gold = 0; // кол-во золота
    static String KEY_ADD_GOLD = "ADD_GOLD";
    int add_gold = 1; // буст золота
    static String KEY_RAB_MESTO = "RAB_MESTO";
    int rab_mesto = 10; // рабочие места
    static String KEY_COUNT_SHAHTA = "COUNT_SHAHTA";
    int count_shahta = 1; // Количество шахт
    static String KEY_DOP_ADD_GOLD = "DOP_ADD_GOLD";
    int dop_add_gold = 0; // Золото, что добывают рабы
    static final int win = 5000; // Условие для победы
    TextView goldview;

    CountDownTimer timer;

    ActivityResultLauncher<Intent> ShahtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // все ли хорошо при получении данных из дочерней активити?
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //получить данные
                        Intent intent = result.getData();
                        count_gold = intent.getIntExtra("COUNT_GOLD", 0);
                        goldview = findViewById(R.id.txt_score_gold);
                        goldview.setText(count_gold + " золота");
                        add_gold = intent.getIntExtra("ADD_GOLD", 0);
                        rab_mesto = intent.getIntExtra("RAB_MESTO", 0);
                        count_shahta = intent.getIntExtra("COUNT_SHAHTA", 0);
                        dop_add_gold = intent.getIntExtra("DOP_ADD_GOLD", 0);
                        assert intent != null;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        // Таймер будет идти сутки, тик раз в 2 секунды
        timer = new CountDownTimer(86400000, 2000) {
            @Override
            public void onTick(long l) {
                if (count_gold < win) {
                    count_gold += dop_add_gold;
                    goldview = findViewById(R.id.txt_score_gold);
                    goldview.setText(count_gold + " золота");
                }
            }
            @Override
            public void onFinish() {

                    }
                }.start();
            }

    // Кнопка добыть золото
    public void AddGold(View view) {
        // Если условия для победы не выполнены, то добываем золото, иначе победа
        if (count_gold < win) {
            count_gold += add_gold;
            goldview = findViewById(R.id.txt_score_gold);
            goldview.setText(count_gold + " золота");
        }
        else
        {
            goldview.setText("Победа! Наслаждайтесь своим рабным гаремом...");
        }
    }

    // Кнопка магазин, переход на активити магазина
    public void Shop(View view) {
        Intent intent = new Intent(
                MainActivity.this,
                ShopActivity.class);
        // Передача данных в дочернюю активити
        intent.putExtra(KEY_COUNT_GOLD, count_gold);
        intent.putExtra(KEY_ADD_GOLD, add_gold);
        intent.putExtra(KEY_RAB_MESTO, rab_mesto);
        intent.putExtra(KEY_COUNT_SHAHTA, count_shahta);
        intent.putExtra(KEY_DOP_ADD_GOLD, dop_add_gold);
        ShahtaLauncher.launch(intent);
    }
}