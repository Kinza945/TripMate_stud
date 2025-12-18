package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.RemoteInfo;
import com.kynzai.tripmate_stud.domain.usecase.GetCountriesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetRemoteInfoUseCase;
import com.kynzai.tripmate_stud.domain.usecase.ToggleCountryFavoriteUseCase;

import java.util.List;

public class CountryViewModel extends ViewModel {
    private final GetCountriesUseCase countriesUseCase;
    private final GetRemoteInfoUseCase remoteInfoUseCase;
    private final ToggleCountryFavoriteUseCase toggleCountryFavoriteUseCase;

    public CountryViewModel(GetCountriesUseCase countriesUseCase,
                            GetRemoteInfoUseCase remoteInfoUseCase,
                            ToggleCountryFavoriteUseCase toggleCountryFavoriteUseCase) {
        this.countriesUseCase = countriesUseCase;
        this.remoteInfoUseCase = remoteInfoUseCase;
        this.toggleCountryFavoriteUseCase = toggleCountryFavoriteUseCase;
    }

    public LiveData<List<Country>> getCountries() {
        return countriesUseCase.execute();
    }

    public LiveData<RemoteInfo> getRemoteInfo() {
        return remoteInfoUseCase.execute();
    }

    public void toggleFavorite(String id) {
        toggleCountryFavoriteUseCase.execute(id);
    }
}
