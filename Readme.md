Команды для запуска образа в докере:
-

1. sudo docker build -f Dockerfile -t arh-with-sheduler .
2. sudo docker run -e LANG=C.UTF-8 -e LC_ALL=C.UTF-8 --network=host -v /home/danil/Documents/test_arhives:/home/danil/Documents/test_arhives arh-with-sheduler
    
"LANG=C.UTF-8 -e LC_ALL=C.UTF-8" -  ствить кодировку в докере при работе с файловой системой

"--network=host" - для запуска на локальной машине (для запуска на порту 8080: "p8080-8080"

"-v /home/danil/Documents/test_arhives:/home/danil/Documents/test_arhives" - монтирует файловую систему хоста и докера