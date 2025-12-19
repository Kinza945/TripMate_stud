# TripMate_stud compliance review

## Gaps versus the technical specification

- **Firebase authentication is not wired.** `AuthRepositoryImpl` relies on a local `FakeAuthService` that stores a mock session instead of Firebase Auth, so login/registration never touch Firebase.
- **Trips persist locally, not in Firebase.** `TripRepositoryImpl` saves added trips and favorites into `LocalTripStorage` (shared preferences) and never writes to a Firebase database, which fails the requirement to store data in Firebase.
- **Guest vs. authorized logic is shallow.** The app only blocks trip creation with a toast in `TravelFragment`; favorites and deletions are still tracked locally for guests instead of being protected behind authenticated Firebase state.
- **External data source is only MockAPI.** The currency API interface exists, but the UI never surfaces currency/weather data; only the MockAPI countries/trips feed is used.

## Design deviations from provided mockups

- **Profile screen lacks the “Мои поездки” shortcut.** The current layout shows only auth controls and helper text with no entry point to the trips tab from the profile screen.
- **Card hierarchy differs.** Country and trip cards are single-column lists without the tabbed filters (“Популярные страны/Городской ритм” or “Поезда/Любимые поездки”) shown in the mockups, and typography sizes/spacings differ from the provided wireframes.
- **Detail screen structure is simplified.** The country detail page omits the segmented pill controls and dense metadata stack shown in the mockup; it only shows a hero image, description, and three key-value rows.

## MockAPI content suggestions

- Keep `temperature` values within human ranges (e.g., -50..60) or add a secondary numeric field to avoid clamping; current mock data contains extreme strings that force fallback values.
- Provide shorter teaser descriptions (1–2 sentences) and consistent `imageUrl` aspect ratios so list cards have even heights and text does not overflow.
- Add stable IDs and optional categories (e.g., `tag` or `type`) to support the segmented filters shown in the design (“Популярные”, “Городской ритм”, “Любимые”).

## Required changes to meet the spec

- Integrate real Firebase Auth (email/password) and replace `FakeAuthService` with Firebase-backed flows, wiring auth state into the UI.
- Persist user-created trips and favorites to a Firebase database (Firestore/Realtime DB), scoped per user, while continuing to read countries/trips from MockAPI.
- Gate creation/favorite/deletion actions behind authenticated state so guests can only browse.
- Surface at least one external JSON data source beyond MockAPI in the UI (e.g., display live currency/weather data).
- Add the “Мои поездки” entry in the profile that navigates to the trips tab, matching the provided mockup.
- Align layouts and typography with the mockups: introduce the segmented filters for countries/trips/favorites, larger titles, and consistent spacing to match the reference screens.
