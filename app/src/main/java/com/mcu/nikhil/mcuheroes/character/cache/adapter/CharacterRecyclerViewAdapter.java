package com.mcu.nikhil.mcuheroes.character.cache.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CharacterRecyclerViewAdapter extends RecyclerView.Adapter<CharacterViewHolder>{

    private ArrayList<CharacterModel> characters = new ArrayList<>();
    private PublishSubject<CharacterModel> notify = PublishSubject.create();

    @Inject
    public CharacterRecyclerViewAdapter() {
    }

    public void setCharacters(List<CharacterModel> characters){
        this.characters = new ArrayList<>(characters);
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        final CharacterModel character = characters.get(position);

        holder.setCharacter(character);

        RxView.clicks(holder.view)
                .map(aVoid->holder.getCharacter())
                .subscribe(notify::onNext);
    }

    public Observable<CharacterModel> asObservable(){return notify.hide();}

    @Override
    public int getItemCount() {
        return characters.size();
    }
}
