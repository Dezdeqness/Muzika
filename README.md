<h1 align="center" id="title">Aqua</h1>

<p align="center">Unofficial SoundCloud client for Android built with Jetpack Compose and Media3</p>

<h2>⚙️Features</h2>

- **Authentication**
- **Home**
- **Liked Tracks**
- **Player**
- **Playlist**
- **Settings**

<h2>🏗️Architecture</h2>

- Multi-module structure with feature modules
- MVVM pattern with `ViewModel` + `StateFlow`
- Repository pattern with mapper classes at layer boundaries
- Clean data/domain/presentation separation per module

<h2>🛠️Tech stack</h2>

| Category        | Stack                                                               |
|-----------------|---------------------------------------------------------------------|
| **Language**    | Kotlin, Coroutines, kotlinx.serialization                           |
| **UI**          | Jetpack Compose (Material 3), Compose Navigation (type-safe routes) |
| **DI**          | Koin + Koin Annotations (KSP)                                       |
| **Networking**  | Ktor + Ktorfit (OkHttp engine)                                      |
| **Media**       | Media3 ExoPlayer (HLS), MediaSession                                |
| **Persistence** | Room, DataStore Preferences                                         |
| **Paging**      | Paging 3 (RemoteMediator, PagingSource)                             |
| **Images**      | Coil, AndroidX Palette                                              |
| **Build**       | AGP, KSP, Version Catalog                                           |

<h2>🔮Future plans</h2>

- [ ] Advanced search
- [ ] Artist, album view
- [ ] Achievements
- [ ] Tech improvements

<h2>🎨Project Screenshots:</h2>

TBA
