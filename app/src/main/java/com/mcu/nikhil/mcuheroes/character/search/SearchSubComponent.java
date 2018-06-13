package com.mcu.nikhil.mcuheroes.character.search;

import dagger.Subcomponent;

@Search
@Subcomponent(modules = {
        SearchModule.class
})
public interface SearchSubComponent {
    void inject(SearchFragment fragment);
}
