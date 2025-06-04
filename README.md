Что это за проект?
Тестовое задание для Kolsanovafit

Что делает проект?
- Получает список тренировок с тестового сервера
- Позволяет искать тренировку по названию
- Позволяет фильтровать список по типу тренировки
- Позволяет отобразить экран с деталями тренировки и посмотреть видео

Скриншоты:
![image](https://github.com/user-attachments/assets/e3ee1bb3-7e62-4fde-b42a-238f0445f62b)

Добавленные зависимости в проект:
- Dagger2
- Retrofit
- Gson
- Safe args
- Coroutines
- Navigation Component
- ViewModel
- ExoPlayer

Структура проекта:
Проект построен на Clean Architecture + MVVM
![image](https://github.com/user-attachments/assets/85446a78-1fe1-44e0-962a-0d2e935dcaa0)

слой data содержит:
- WorkoutApiService
- Dto модели
- Mappers
- RepositoryImpl
- 
слой domain содержит:
- Domain models
- Repository interface
- UseCases
- 
слой presentation содержит:
- workouts (Fragment, UiState, ViewModel, Adapter)
- player (Fragment, UiState, ViewModel)
- 
папка di содержит:
- AppComponent
- NetworkModule
- RepositoryModule
- ViewModelModule
- ViewModelFactory


