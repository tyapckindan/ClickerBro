package com.example.clickerbro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    TextView goldview, message;
    // count_gold - кол-во золота, add_gold - прибавка золота за клик, rab_mesto - рабочие места,
    // count_shahta - кол-во шахт, dop_add_gold - кол-во золота, которое добывают рабы
    Integer count_gold = 0, add_gold = 0, rab_mesto = 10, count_shahta = 1, dop_add_gold = 0;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_shop);

        // Таймер будет идти сутки, тик раз в 2 секунды
        timer = new CountDownTimer(86400000, 2000) {
            @Override
            public void onTick(long l) {
                if (count_gold < MainActivity.win) {
                    count_gold += dop_add_gold;
                    goldview = findViewById(R.id.txt_score);
                    goldview.setText(count_gold + " золота");
                }
            }
            @Override
            public void onFinish() {

            }
        }.start();

        // получить данные из родительской активити
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            count_gold = extras.getInt(MainActivity.KEY_COUNT_GOLD);
            add_gold = extras.getInt(MainActivity.KEY_ADD_GOLD);
            rab_mesto = extras.getInt(MainActivity.KEY_RAB_MESTO );
            count_shahta = extras.getInt(MainActivity.KEY_COUNT_SHAHTA);
            dop_add_gold = extras.getInt(MainActivity.KEY_DOP_ADD_GOLD);
        }

        goldview = findViewById(R.id.txt_score);
        message = findViewById(R.id.txt_message);
        goldview.setText(count_gold.toString() + " золота");
    }

    // Кнопка улучшения кирки
    public void btn_click_up_kirka (View view) {
        int price = 10;
        // Если золота больше чем цена, то улучшаем, иначе недостаточно золота
        if (count_gold >= price){
            count_gold -= price;

            add_gold += 1;

            goldview = findViewById(R.id.txt_score);
            goldview.setText(count_gold + " золота");
            message.setText("Вы улучшили свой инструмент!");
        }
        else {
            message.setText("У вас недостаточно золота!");
        }
    }
    // Кнопка покупки шахты
    public void btn_click_bought_shahta (View view) {
        int price = 500;
        // Если золота больше чем цена, то покупаем, иначе недостаточно золота
        if (count_gold >= price){
            count_gold -= price;
            count_shahta += 1;
            rab_mesto += 10;

            goldview = findViewById(R.id.txt_score);
            goldview.setText(count_gold + " золота");
            message.setText("Вы купили новую шахту!");
        }
        else {
            message.setText("У вас недостаточно золота!");
        }
    }
    // Кнопка покупки раба
    public void btn_click_bought_rab (View view) {
        int price = 20;
        // Если золота больше чем цена и есть место, то покупаем, иначе недостаточно золота, либо не хватает места
        if (count_gold >= price && rab_mesto >= 1){
            count_gold -= price;
            rab_mesto -= 1;
            dop_add_gold += 1;

            goldview = findViewById(R.id.txt_score);
            goldview.setText(count_gold + " золота");
            message.setText("Вы купили раба!");
        }
        else {
            message.setText("У вас недостаточно золота, либо не хватает места!");
        }
    }

    // Кнопка выхода - возврат на родительскую активити
    public void btn_click_exit_shop (View view) {

        Intent data = new Intent();
        data.putExtra(MainActivity.KEY_COUNT_GOLD,count_gold);
        data.putExtra(MainActivity.KEY_ADD_GOLD, add_gold);
        data.putExtra(MainActivity.KEY_RAB_MESTO,rab_mesto);
        data.putExtra(MainActivity.KEY_COUNT_SHAHTA, count_shahta);
        data.putExtra(MainActivity.KEY_DOP_ADD_GOLD, dop_add_gold);
        setResult(RESULT_OK, data);
        finish();
    }
}