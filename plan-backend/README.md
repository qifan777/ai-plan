```shell
docker run --name redis \
    -p 6379:6379 \
    --network=app \
    --network-alias=redis \
    --restart=always \
    -v redis-data:/data \
    -d redis:7.2 redis-server \
    --save 60 1 \
    --loglevel warning
```
```shell
docker run -p 3306:3306 \
    -d \
    --name mysql8 \
    -e MYSQL_ROOT_PASSWORD=qifan123. \
    -e TZ=Asia/Shanghai  \
    -e MYSQL_DATABASE=bottle_post \
    -v mysql-data:/var/lib/mysql \
    --network=app \
    --network-alias=mysql \
    --restart=always \
    mysql:8.0.26 \
    mysqld --character-set-server=utf8mb4 \
    --collation-server=utf8mb4_unicode_ci  

```