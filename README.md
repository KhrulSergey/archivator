# Консольное приложение для управления сжатием файлов и папок

Запуск архивации выполняется командой: 
`java -jar archivator-1.0.0.jar ./file1 ./file2 > ./archive.zip`
, где 
`./file1 ./file2` - список путей к файлам и папкам, которые необходимо поместить в архив,
`./archive.zip` - имя и путь для сохранения архива.

C:\Users\Khsa\IdeaProjects\arvhiver\archiver-master\archiverTestFolder1 > ./archive

Запуск разархивирования выполняется командой: 
`cat ./archive.zip | java -jar archivator-1.0.0.jar` 
, где 
`./archive.zip`- путь до архива

 ## Особенности проекта 
Используется ZipInputStream / ZipOutputStream для управления потоками сжатия файлов.

 
 ## Получение исходного кода и старт проекта 
 
- Получить исходный код проекта командой
 ```
 git clone https://github.com/KhrulSergey/archivator
 ```

- Перейти в рабочую папку проекта
`cd ./archivator`

- Запустить сборку и unit-тестирование проекта командой
`mvn package`

- Перейти в папку с релизом приложения
`cd ./target`
