# TripMate Mobile App — Technical Specification

## Overview
TripMate is an Android app for travelers with a layered (app/data/domain) MVVM architecture. It supports Firebase authentication and Firestore (NoSQL) storage, external data sources for weather and currency, and differentiated guest vs. authenticated experiences. The goal is to let users browse countries and trips, view detailed pages with media, and save their own trips and favorites.

## Critical Functional Requirements
1. **Authentication (Firebase Auth, email/password).** Minimal login/registration flow with validation and session persistence. Guest mode allows limited browsing.
2. **External service integration (JSON).** Use Retrofit to fetch weather and currency rates (e.g., OpenWeatherMap + Open Exchange Rates or any mockable JSON API). Handle errors and retries, expose results via domain use cases.
3. **Data persistence (Firebase Firestore).** Store users, trips, favorites, and user-generated content. Structure for multi-tenant separation by user ID.
4. **Entity lists with images.** Countries, trips, and favorites displayed in RecyclerView lists with cached images (Coil/Glide). Support pagination or lazy loading and loading/empty/error states.
5. **Entity detail pages.** Country and trip detail screens show metadata, media, weather/currency widgets, and actions (save, favorite, edit if owner).
6. **Role-aware UX.** Guests can browse; authenticated users can create/edit/delete their trips, add media, and save favorites. Admin role is optional; not required by scope.

## Architecture
- **Layers:**
  - **presentation (app module):** Activities/Fragments + ViewModels (LiveData/StateFlow), UI state models, navigation (BottomNavigation + fragment destinations), RecyclerView adapters, view binding/Compose allowed.
  - **domain:** Use cases (e.g., `GetCountries`, `GetTrips`, `GetTripById`, `AddTrip`, `ToggleFavorite`, `Login`, `Register`, `GetWeather`, `GetRates`). Domain models are UI-agnostic.
  - **data:** Repository implementations orchestrating Firebase Auth/Firestore, Retrofit services, local cache (Room optional), and mappers to domain models. Network DTOs isolated from domain.
- **Patterns:** MVVM, repository, use-case invokers, dependency injection (e.g., Hilt/Koin), and unidirectional data flow.
- **Error handling:** Result wrappers with loading/success/error; retry for network; offline-aware reads from cache where available.

## Data Model (Firestore-first)
- **users** (`/users/{userId}`): profile, bio, avatarUrl, email, createdAt.
- **trips** (`/trips/{tripId}`): ownerId, title, description, location, tags, media (list of URLs), rating, createdAt/updatedAt, isPublic, translatedText.
- **favorites** (`/users/{userId}/favorites/{tripId}`): mirrors selected trips for fast lookup.
- **countries** (`/countries/{countryId}`): name, description, heroImage, bestPlaces (list), tags.
- **weatherCache** / **ratesCache** (optional collections) with TTL for offline display.

## External APIs
- **Weather:** OpenWeatherMap `/data/2.5/weather?lat={lat}&lon={lon}&appid=KEY&units=metric` (or any JSON-compatible mock service).
- **Currency rates:** Open Exchange Rates `/latest.json?app_id=KEY&base=USD` (or Fixer/other mock JSON). Show converted estimates on trip details.
- **Mocking:** Allow switching base URL to WireMock/mockapi/json-server for local dev and tests.

## Key Screens & Navigation
- **Bottom tabs:** Countries, Favorites, Trips, Profile (plus auth screen when signed out).
- **Countries list:** Filters (chips), country cards with teaser text and hero images. Tapping opens Country detail.
- **Country detail:** Hero image, description, best places, weather/currency widgets, CTA to view trips in that country.
- **Trips list:** Shows public trips; cards show title, location, rating, badges (e.g., "Recommended"). Favorites toggles on cards for signed-in users.
- **Favorites list:** User-specific favorites; empty state for guests prompting sign-in.
- **Trip detail:** Rich text + media carousel; weather/currency snippet; actions: favorite, edit/delete (owner), add review/notes; translation toggle (auto-translate not mandatory but placeholder allowed).
- **Profile:** Displays avatar, username, bio, email; entry points to "My trips", "Favorites", and "Add trip". Shows login/register form when guest.
- **Auth:** Simple email/password login and registration with validation and Firebase errors surfaced in UI.

## Use Cases (examples)
- `Login(username, password)` / `Register(email, password)`
- `GetCountries()` / `GetCountryById(countryId)`
- `GetTrips()` / `GetTripById(tripId)` / `AddTrip(trip)` / `EditTrip(trip)` / `DeleteTrip(tripId)`
- `ToggleFavorite(tripId)` / `GetFavoriteTrips(userId)`
- `GetWeather(lat, lon)` / `GetRates(baseCurrency)`

## Data Flow Examples
1. **Displaying trips list:** UI requests via ViewModel → `GetTrips` use case → TripRepository → Firestore query (with optional cache) → map to domain → expose `UiState` with loading/error support.
2. **Favoriting a trip:** UI click → `ToggleFavorite` use case → write to `/users/{uid}/favorites` and update local cache → update UI state; guests are routed to sign-in.
3. **Weather widget in trip detail:** ViewModel calls `GetWeather` with trip coords → Retrofit → success mapped to domain; failure shows fallback/last cached value.

## State Management & UI
- Use LiveData or StateFlow/Flow for reactive updates.
- Represent UI as `UiState(content, isLoading, errorMessage)`; emit loading before async calls.
- RecyclerView with `ListAdapter` diffing; image loading via Coil/Glide with placeholders.
- Empty, loading, and error states for lists and detail screens.

## Security & Validation
- Auth tokens handled by Firebase SDK. Secure Firestore rules: users can write only their documents; public trips are readable by guests.
- Client-side validation for emails/passwords, trip titles, and text lengths; sanitize rich text where applicable.

## Offline & Caching (optional but recommended)
- Cache recent lists/details in Room or in-memory with timestamps.
- Graceful degraded mode: show cached data when offline; queue writes to Firestore where feasible.

## Tooling & Libraries
- Kotlin, AndroidX, Coroutines/Flow, ViewModel, LiveData/StateFlow, RecyclerView, Navigation component or equivalent, Hilt/Koin DI, Retrofit + OkHttp + Moshi/Gson, Coil/Glide, Firebase Auth/Firestore.

## Non-Functional Notes
- Target SDK/current stable Android toolchain, min SDK per project needs.
- Basic accessibility: content descriptions for images, focus order, readable contrast.
- Localization-ready strings; Russian as primary example.

## Deliverables
- Layered project structure with modules/classes matching this spec.
- Auth + data flows wired to Firebase and external APIs (or mocks), with UI reflecting guest vs. authenticated capabilities.
- Lists and detail screens with media loading and loading/error states.

