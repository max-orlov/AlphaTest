# AlphaTest
Для работы приложения необходимо создать в PostgreSQL базу данных "storage",
затем в консоли БД запустить файл "database.sql" для создания таблиц.
  Для заполнения БД данными нужно запустить класс Application.
  При этом в классе XMLParser необходимо указать корректный путь к xml файлую
  Конкретно здесь он лежит в корне проекта.
Затем перейти в режим работы REST, запустив Tomcat;
  Сервлет "MainServlet" обрабатывает входные данные и выдает результат из 
базы данных.
