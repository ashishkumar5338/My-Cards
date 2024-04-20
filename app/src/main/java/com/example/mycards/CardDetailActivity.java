package com.example.mycards;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class CardDetailActivity extends AppCompatActivity {

    String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] yy = {"24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
    String[] company = {"RUPAY", "VISA", "MasterCard"};

    ArrayAdapter<String> adaptermm, adapteryy, adaptercompany;

    private EditText card1, card2, card3, card4, cardHolder, cvv, cardBank;
    private AutoCompleteTextView autoCompleteTextViewmm, autoCompleteTextViewyy, autoCompleteTextViewcompany;
    private Card selectedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        initWidgets();

        adaptermm = new ArrayAdapter<>(this, R.layout.list, mm);
        adapteryy = new ArrayAdapter<>(this, R.layout.list, yy);
        adaptercompany = new ArrayAdapter<>(this, R.layout.list, company);

        autoCompleteTextViewmm.setAdapter(adaptermm);
        autoCompleteTextViewyy.setAdapter(adapteryy);
        autoCompleteTextViewcompany.setAdapter(adaptercompany);

        formatter();

        checkForEditCard();


    }

    private void checkForEditCard() {
        Intent previousIntent = getIntent();

        int passedCardID = previousIntent.getIntExtra(Card.NOTE_EDIT_EXTRA, -1);
        selectedCard = Card.getCardForID(passedCardID);

        if (selectedCard != null) {
            card1.setText(selectedCard.getN1());
            card2.setText(selectedCard.getN2());
            card3.setText(selectedCard.getN3());
            card4.setText(selectedCard.getN4());
            cardHolder.setText(selectedCard.getCardHolder());
            autoCompleteTextViewmm.setText(selectedCard.getMm());
            autoCompleteTextViewyy.setText(selectedCard.getYy());
            cvv.setText(selectedCard.getCvv());
            cardBank.setText(selectedCard.getCardBank());
            autoCompleteTextViewcompany.setText(selectedCard.getCardCompany());
        }
    }

    private void initWidgets() {
        card1 = findViewById(R.id.e1);
        card2 = findViewById(R.id.e2);
        card3 = findViewById(R.id.e3);
        card4 = findViewById(R.id.e4);
        cardHolder = findViewById(R.id.e5);
        autoCompleteTextViewmm = findViewById(R.id.autocomplete_mm);
        autoCompleteTextViewyy = findViewById(R.id.autocomplete_yy);
        cvv = findViewById(R.id.e8);
        cardBank = findViewById(R.id.e9);
        autoCompleteTextViewcompany = findViewById(R.id.autocomplete_company);
    }

    public void saveCard(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String c1 = String.valueOf(card1.getText());
        String c2 = String.valueOf(card2.getText());
        String c3 = String.valueOf(card3.getText());
        String c4 = String.valueOf(card4.getText());
        String card_Holder = String.valueOf(cardHolder.getText());
        String expiry_mm = String.valueOf(autoCompleteTextViewmm.getText());
        String expiry_yy = String.valueOf(autoCompleteTextViewyy.getText());
        String card_cvv = String.valueOf(cvv.getText());
        String card_Bank = String.valueOf(cardBank.getText());
        String card_Company = String.valueOf(autoCompleteTextViewcompany.getText());

        if (selectedCard == null) {
            int id = Card.cardArrayList.size();
            Card newCard = new Card(id, c1, c2, c3, c4, card_Holder, expiry_mm, expiry_yy, card_cvv, card_Bank, card_Company);
            Card.cardArrayList.add(newCard);
            sqLiteManager.addCardToDatabase(newCard);
        } else {
            selectedCard.setN1(c1);
            selectedCard.setN2(c2);
            selectedCard.setN3(c3);
            selectedCard.setN4(c4);
            selectedCard.setCardHolder(card_Holder);
            selectedCard.setMm(expiry_mm);
            selectedCard.setYy(expiry_yy);
            selectedCard.setCvv(card_cvv);
            selectedCard.setCardBank(card_Bank);
            selectedCard.setCardCompany(card_Company);
            sqLiteManager.updateCardInDB(selectedCard);
        }
//        Log.d("card", "saveCard: " + id + c1 + c2 + c3 + c4 + card_Holder + expiry_mm + expiry_yy + card_cvv + card_Bank + card_Company);
//        Intent newCardIntent = new Intent(this, MainActivity.class);
//        startActivity(newCardIntent);
        finish();
    }


    //    for formatting and focusing the input box.
    public void formatter() {

        final List<EditText> editTexts = Arrays.asList(card1, card2, card3, card4);

        for (int i = 0; i < editTexts.size(); i++) {
            final EditText currentEditText = editTexts.get(i);
            final int nextIndex = i + 1;
            currentEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 4 && nextIndex < editTexts.size()) {
                        editTexts.get(nextIndex).requestFocus();
                    } else if (s.length() == 0) {
                        editTexts.get(nextIndex - 1).requestFocus();
                    } else if (s.length() == 4 && nextIndex == editTexts.size()) {
                        cardHolder.requestFocus();
                    }
                }
            });
        }

//        card4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 4) {
//                    cardHolder.requestFocus();
//                }
//            }
//        });

        cardHolder.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                autoCompleteTextViewmm.requestFocus();
                return true;
            }
            return false;
        });


//        cardHolder.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                for (int i = 0; i < s.length(); i++) {
//                    if (s.charAt(i) == '\n') {
//                        s.delete(i, i + 1);
//                    }
//                }
//            }
//        });


        autoCompleteTextViewmm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    autoCompleteTextViewyy.requestFocus();
                }
            }
        });

        autoCompleteTextViewyy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    cvv.requestFocus();
                }
            }
        });

        cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3) {
                    cardBank.requestFocus();
                }
            }
        });


//        cardBank.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                for (int i = 0; i < s.length(); i++) {
//                    if (s.charAt(i) == '\n') {
//                        s.delete(i, i + 1);
//                    }
//                }
//            }
//        });

    }

}



