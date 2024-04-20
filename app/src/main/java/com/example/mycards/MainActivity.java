package com.example.mycards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ListView cardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setCardAdapter();
    }


    private void initWidgets() {
        cardListView = findViewById(R.id.cardListView);
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateCardListArray();
        if (Card.cardArrayList.isEmpty()) {
            int id = 0;
            Card newCard = new Card(id, "1234", "5678", "1234", "5678", "SAMPLE NAME", "01", "40", "999", "SAMPLE BANK", "MasterCard");
            Card.cardArrayList.add(newCard);
            sqLiteManager.addCardToDatabase(newCard);
        }
    }

    private void setCardAdapter() {
        CardAdapter cardAdapter = new CardAdapter(this, Card.nonDeletedCards());
        cardListView.setAdapter(cardAdapter);
    }

    public void newCard(View view) {
        Intent newCardIntent = new Intent(this, CardDetailActivity.class);
        startActivity(newCardIntent);
//        finish();
    }

    protected void onResume() {
        super.onResume();
        setCardAdapter();
    }

    public void refreshActivity() {
        setCardAdapter();
    }


}