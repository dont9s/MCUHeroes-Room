package com.mcu.nikhil.mcuheroes.character.cache.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.BR;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    private CharacterModel character;
    private ViewDataBinding binding;

    public CharacterViewHolder(View view) {
        super(view);
        this.view = view;

        binding  = DataBindingUtil.bind(view);

    }

    public CharacterModel getCharacter(){return character;}

    public void setCharacter(CharacterModel character){
        this.character = character;

        binding.setVariable(BR.character, character);
        binding.executePendingBindings();
    }

}
