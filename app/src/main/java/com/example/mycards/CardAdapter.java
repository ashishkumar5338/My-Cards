package com.example.mycards;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {
    private final Context mContext; // Context variable to hold the context

    public CardAdapter(Context context, List<Card> cards) {
        super(context, 0, cards);
        mContext = context; // Initialize mContext in the constructor
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Card card = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_cell, parent, false);

        LinearLayout mainLayout = convertView.findViewById(R.id.mainLayout);
        mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        TextView card_Bank = convertView.findViewById(R.id.card_Bank);
        TextView card_Holder = convertView.findViewById(R.id.card_Holder);

        TextView cardBank = convertView.findViewById(R.id.cardBank);
        TextView cardNumber = convertView.findViewById(R.id.cardNumber);
        TextView cardExpiry = convertView.findViewById(R.id.cardExpiry);
        TextView cardCvv = convertView.findViewById(R.id.cardCvv);
        TextView cardHolder = convertView.findViewById(R.id.cardHolder);

        LinearLayout myCardView = convertView.findViewById(R.id.card_View);
        View cardView = convertView.findViewById(R.id.card_image);
        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);
        ImageView copyCardIcon = convertView.findViewById(R.id.copyCardIcon);
        ImageView copyExpiryIcon = convertView.findViewById(R.id.copyExpiryIcon);
        ImageView copyCvvIcon = convertView.findViewById(R.id.copyCvvIcon);
        ImageView cardCompany = convertView.findViewById(R.id.cardCompany);

        assert card != null;
        card_Bank.setText(card.getCardBank());
        card_Holder.setText(card.getCardHolder());

        cardBank.setText(card.getCardBank());
        cardNumber.setText(String.format("%s  %s  %s  %s", card.getN1(), card.getN2(), card.getN3(), card.getN4()));
        cardExpiry.setText(String.format("%s / %s", card.getMm(), card.getYy()));
        cardCvv.setText(card.getCvv());
        cardHolder.setText(card.getCardHolder());
        cardCompanySetImageResource(cardCompany, card);

        // Register myCardView for context menu
        myCardView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            MenuItem editItem = menu.add(Menu.NONE, R.id.menu_edit, Menu.NONE, "Edit");
            MenuItem deleteItem = menu.add(Menu.NONE, R.id.menu_delete, Menu.NONE, "Delete");

            editItem.setOnMenuItemClickListener(menuItem -> {
                // Handle edit action
                editItem(card);
                return true;
            });

            deleteItem.setOnMenuItemClickListener(menuItem -> {
                // Handle delete action
                deleteItem(card);
                return true;
            });
        });

        // Set click listener for expanding/collapsing card details
        myCardView.setOnClickListener(v -> {
            if (cardView.getVisibility() == View.VISIBLE) {
                cardView.setVisibility(View.GONE);
                iconImageView.setImageResource(R.drawable.downicon);
            } else {
                cardView.setVisibility(View.VISIBLE);
                iconImageView.setImageResource(R.drawable.upicon);
            }
        });

        // Set click listeners for copying card details to clipboard
        copyCardIcon.setOnClickListener(v -> {
            copyToClipboard("Card Number", card.getN1() + card.getN2() + card.getN3() + card.getN4());
        });

        copyExpiryIcon.setOnClickListener(v -> {
            copyToClipboard("Card Expiry", card.getMm() + card.getYy());
        });

        copyCvvIcon.setOnClickListener(v -> {
            copyToClipboard("Card CVV", card.getCvv());
        });

        return convertView;
    }

    private void cardCompanySetImageResource(ImageView cardCompany, Card card) {
        switch (card.getCardCompany()) {
            case "VISA":
                cardCompany.setVisibility(View.VISIBLE);
                cardCompany.setImageResource(R.drawable.visa);
                break;
            case "MasterCard":
                cardCompany.setVisibility(View.VISIBLE);
                cardCompany.setImageResource(R.drawable.mastercard);
                break;
            default: // Default case is "RUPAY"
                cardCompany.setVisibility(View.VISIBLE);
                cardCompany.setImageResource(R.drawable.rupay);
                break;
        }
    }

    // Method to copy text to clipboard
    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mContext, label + " Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    }
    private void editItem(Card card) {
        // Implement your edit logic here
        Toast.makeText(mContext, "edit Clicked"+card.getCardBank(), Toast.LENGTH_SHORT).show();
    }

    private void deleteItem(Card card) {
        // Implement your delete logic here
        Toast.makeText(mContext, "delete Clicked"+card.getCardHolder(), Toast.LENGTH_SHORT).show();
    }




}
