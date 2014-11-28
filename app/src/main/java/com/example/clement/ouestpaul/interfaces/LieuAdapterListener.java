package com.example.clement.ouestpaul.interfaces;

import android.view.View;

import com.example.clement.ouestpaul.lieux.Lieu;

/**
 * Created by Adrien on 19/11/2014.
 */
public interface LieuAdapterListener {
    public void onClickNom(Lieu item, int position);
    public void onClickFavoris(Lieu item, View rowView);
}
