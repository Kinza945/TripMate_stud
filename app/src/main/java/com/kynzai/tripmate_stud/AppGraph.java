package com.kynzai.tripmate_stud;

import android.content.Context;

import com.kynzai.tripmate_stud.data.local.FakeFirebaseAuthDataSource;
import com.kynzai.tripmate_stud.data.local.FakeFirestoreDataSource;
import com.kynzai.tripmate_stud.data.network.RemoteInfoDataSource;
import com.kynzai.tripmate_stud.data.repository.AuthRepositoryImpl;
import com.kynzai.tripmate_stud.data.repository.CountryRepositoryImpl;
import com.kynzai.tripmate_stud.data.repository.RemoteRepositoryImpl;
import com.kynzai.tripmate_stud.data.repository.TripRepositoryImpl;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;
import com.kynzai.tripmate_stud.domain.repository.RemoteRepository;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;
import com.kynzai.tripmate_stud.domain.usecase.AddTripUseCase;
import com.kynzai.tripmate_stud.domain.usecase.AuthUseCases;
import com.kynzai.tripmate_stud.domain.usecase.GetCountriesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetFavoritesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetRemoteInfoUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetSessionUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetTripsUseCase;
import com.kynzai.tripmate_stud.domain.usecase.ToggleCountryFavoriteUseCase;
import com.kynzai.tripmate_stud.domain.usecase.ToggleTripFavoriteUseCase;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.FavoritesViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.ProfileViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;

/**
 * Very small dependency holder that wires data/domain layers without DI frameworks.
 */
public class AppGraph {
    private static AppGraph instance;
    private final CountryRepository countryRepository;
    private final TripRepository tripRepository;
    private final AuthRepository authRepository;
    private final RemoteRepository remoteRepository;

    private AppGraph(Context context) {
        FakeFirestoreDataSource firestoreDataSource = new FakeFirestoreDataSource();
        FakeFirebaseAuthDataSource authDataSource = new FakeFirebaseAuthDataSource();
        RemoteInfoDataSource remoteInfoDataSource = new RemoteInfoDataSource();

        countryRepository = new CountryRepositoryImpl(firestoreDataSource);
        tripRepository = new TripRepositoryImpl(firestoreDataSource);
        authRepository = new AuthRepositoryImpl(authDataSource);
        remoteRepository = new RemoteRepositoryImpl(remoteInfoDataSource);
    }

    public static AppGraph getInstance(Context context) {
        if (instance == null) {
            instance = new AppGraph(context.getApplicationContext());
        }
        return instance;
    }

    public CountryViewModel provideCountryViewModel() {
        return new CountryViewModel(new GetCountriesUseCase(countryRepository),
                new GetRemoteInfoUseCase(remoteRepository),
                new ToggleCountryFavoriteUseCase(countryRepository));
    }

    public TripViewModel provideTripViewModel() {
        return new TripViewModel(new GetTripsUseCase(tripRepository), new ToggleTripFavoriteUseCase(tripRepository), new AddTripUseCase(tripRepository));
    }

    public FavoritesViewModel provideFavoritesViewModel() {
        return new FavoritesViewModel(new GetFavoritesUseCase(tripRepository), new ToggleTripFavoriteUseCase(tripRepository));
    }

    public ProfileViewModel provideProfileViewModel() {
        return new ProfileViewModel(new GetSessionUseCase(authRepository), new AuthUseCases(authRepository));
    }
}
