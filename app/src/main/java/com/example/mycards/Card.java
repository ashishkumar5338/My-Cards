package com.example.mycards;

import java.util.ArrayList;
import java.util.Date;

public class Card {
    public static ArrayList<Card> cardArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA = "noteEdit";
    private int id;
    private String n1;
    private String n2;
    private String n3;
    private String n4;
    private String cardHolder;
    private String mm;
    private String yy;
    private String cvv;
    private String cardBank;
    private String cardCompany;
    private Date deleted;


    public Card(int id, String n1, String n2, String n3, String n4, String cardHolder, String mm, String yy, String cvv, String cardBank, String cardCompany, Date deleted) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
        this.cardHolder = cardHolder;
        this.mm = mm;
        this.yy = yy;
        this.cvv = cvv;
        this.cardBank = cardBank;
        this.cardCompany = cardCompany;
        this.deleted = deleted;
    }

    public Card(int id, String n1, String n2, String n3, String n4, String cardHolder, String mm, String yy, String cvv, String cardBank, String cardCompany) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
        this.cardHolder = cardHolder;
        this.mm = mm;
        this.yy = yy;
        this.cvv = cvv;
        this.cardBank = cardBank;
        this.cardCompany = cardCompany;
        deleted = null;
    }

    public static Card getCardForID(int passedCardID) {
        for (Card card : cardArrayList) {
            if (card.getId() == passedCardID)
                return card;
        }

        return null;
    }

    public static ArrayList<Card> nonDeletedCards() {
        ArrayList<Card> nonDeletedCards = new ArrayList<>();
        for (Card card : cardArrayList) {
            if (card.getDeleted() == null)
                nonDeletedCards.add(card);
        }
        return nonDeletedCards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public String getN3() {
        return n3;
    }

    public void setN3(String n3) {
        this.n3 = n3;
    }

    public String getN4() {
        return n4;
    }

    public void setN4(String n4) {
        this.n4 = n4;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardBank() {
        return cardBank;
    }

    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
