package com.example.mycards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;


    private static final String DATABASE_NAME = "CardDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Card";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String CARD1_FIELD = "card1";
    private static final String CARD2_FIELD = "card2";
    private static final String CARD3_FIELD = "card3";
    private static final String CARD4_FIELD = "card4";
    private static final String CARD_HOLDER_FIELD = "cardholder";
    private static final String EXPIRY_MM_FIELD = "expirymm";
    private static final String EXPIRY_YY_FIELD = "expiryyy";
    private static final String CARD_CVV_FIELD = "cardcvv";
    private static final String CARD_BANK_FIELD = "cardbank";
    private static final String CARD_COMPANY_FIELD = "cardcompany";
    private static final String DELETED_FIELD = "deleted";
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD).append(" INT, ")
                .append(CARD1_FIELD).append(" TEXT, ")
                .append(CARD2_FIELD).append(" TEXT, ")
                .append(CARD3_FIELD).append(" TEXT, ")
                .append(CARD4_FIELD).append(" TEXT, ")
                .append(CARD_HOLDER_FIELD).append(" TEXT, ")
                .append(EXPIRY_MM_FIELD).append(" TEXT, ")
                .append(EXPIRY_YY_FIELD).append(" TEXT, ")
                .append(CARD_CVV_FIELD).append(" TEXT, ")
                .append(CARD_BANK_FIELD).append(" TEXT, ")
                .append(CARD_COMPANY_FIELD).append(" TEXT, ")
                .append(DELETED_FIELD).append(" TEXT)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCardToDatabase(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, card.getId());
        contentValues.put(CARD1_FIELD, card.getN1());
        contentValues.put(CARD2_FIELD, card.getN2());
        contentValues.put(CARD3_FIELD, card.getN3());
        contentValues.put(CARD4_FIELD, card.getN4());
        contentValues.put(CARD_HOLDER_FIELD, card.getCardHolder());
        contentValues.put(EXPIRY_MM_FIELD, card.getMm());
        contentValues.put(EXPIRY_YY_FIELD, card.getYy());
        contentValues.put(CARD_CVV_FIELD, card.getCvv());
        contentValues.put(CARD_BANK_FIELD, card.getCardBank());
        contentValues.put(CARD_COMPANY_FIELD, card.getCardCompany());
        contentValues.put(DELETED_FIELD, getStringFromDate(card.getDeleted()));

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void populateCardListArray() {
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String c1 = result.getString(2);
                    String c2 = result.getString(3);
                    String c3 = result.getString(4);
                    String c4 = result.getString(5);
                    String card_Holder = result.getString(6);
                    String expiry_mm = result.getString(7);
                    String expiry_yy = result.getString(8);
                    String card_cvv = result.getString(9);
                    String card_Bank = result.getString(10);
                    String card_Company = result.getString(11);
                    String stringDeleted = result.getString(12);
                    Date deleted = getDateFromString(stringDeleted);
                    Card card = new Card(id, c1, c2, c3, c4, card_Holder, expiry_mm, expiry_yy, card_cvv, card_Bank, card_Company, deleted);
//                    Log.d("card", "saveCard: " + id + c1 + c2 + c3 + c4 + card_Holder + expiry_mm + expiry_yy + card_cvv + card_Bank + card_Company+deleted);
                    Card.cardArrayList.add(card);
                }
            }
        }
    }

    public void updateCardInDB(Card card){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID_FIELD, card.getId());
        contentValues.put(CARD1_FIELD, card.getN1());
        contentValues.put(CARD2_FIELD, card.getN2());
        contentValues.put(CARD3_FIELD, card.getN3());
        contentValues.put(CARD4_FIELD, card.getN4());
        contentValues.put(CARD_HOLDER_FIELD, card.getCardHolder());
        contentValues.put(EXPIRY_MM_FIELD, card.getMm());
        contentValues.put(EXPIRY_YY_FIELD, card.getYy());
        contentValues.put(CARD_CVV_FIELD, card.getCvv());
        contentValues.put(CARD_BANK_FIELD, card.getCardBank());
        contentValues.put(CARD_COMPANY_FIELD, card.getCardCompany());
        contentValues.put(DELETED_FIELD, getStringFromDate(card.getDeleted()));

        db.update(TABLE_NAME,contentValues,ID_FIELD+"=? ",new String[]{String.valueOf(card.getId())});
    }

    private String getStringFromDate(Date deleted) {
        if (deleted == null)
            return null;
        return dateFormat.format(deleted);
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

}
