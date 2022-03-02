## NMedia [![BCH compliance](https://bettercodehub.com/edge/badge/Gnucheva/NMedia?branch=main)](https://bettercodehub.com/)


### О приложении
Приложение " NMedia " - это аналог ленты социальной сети. 

Пример приложения для Android, реализованный с использованием шаблона MVVM, LiveData, ViewModel, Room, Fragments & Navigation Components, Firebase и Data Binding.

---

<p align="left"><a><img src="https://github.com/Gnucheva/NMedia/blob/main/gif/NMedia.gif" width="300"></a></p>
  

### Возможности приложения 
* Просмотр списка всех имеющихся постов.
* Добавление нового поста.
* Удаление поста.
* Редактирование поста.
* Возможность поделиться постом.
* Возможность поставить / удалить свой лайк.
* Прсмотр карточки отдельного поста.
* Получение push - уведомлений и notification.

### Архитектура
Архитектура этого приложения опирается на следующие пункты и соответствует им:
* Паттерн [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)(MVVM) который облегчает разделение разработки от графического пользовательского интерфейса.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) , которые помогают поддерживать надежность, тестируемость и поддержку приложения.

### Используемые технологии

* Kotlin
* Multimodule Gradle Project
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) для хранения и управления данными, связанными с пользовательским интерфейсом, с учетом жизненного цикла.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) для обработки данных с учетом жизненного цикла.
* [Navigation Component](https://developer.android.com/guide/navigation) для обработки всех навигаций, а также передачи данных между пунктами назначения.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) для привязки компонентов пользовательского интерфейса в layouts к источникам данных.
* [Room](https://developer.android.com/topic/libraries/architecture/room) библиотека, которая обеспечивает уровень абстракции над SQLite, чтобы обеспечить более надежный доступ к базе данных, используя всю мощь SQLite.
* [Firebase](https://firebase.google.com/) сервис, предназначенный для отправки Cloud Messaging (что позволяет организовавать Push-уведомления) различным устройствам.

### Установка 
Требуется минимальный уровень API 23. Clone repository and run. 
