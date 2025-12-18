package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.usecase.GetFavoritesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.ToggleTripFavoriteUseCase;

import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private final GetFavoritesUseCase favoritesUseCase;
    private final ToggleTripFavoriteUseCase toggleTripFavoriteUseCase;

    public FavoritesViewModel(GetFavoritesUseCase favoritesUseCase, ToggleTripFavoriteUseCase toggleTripFavoriteUseCase) {
        this.favoritesUseCase = favoritesUseCase;
        this.toggleTripFavoriteUseCase = toggleTripFavoriteUseCase;
    }

    public LiveData<List<Trip>> getFavorites() {
        return favoritesUseCase.execute();
    }

    public void toggleFavorite(String id) {
        toggleTripFavoriteUseCase.execute(id);
    }
}
