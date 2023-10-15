# registration with email

### 介绍
本项目是使用邮箱注册登录和邮箱验证的后端项目，没有前端，支持 Docker 一键部署启动

### 功能
- 使用邮箱注册，并给注册用户发送验证邮件，验证链接在 15 分钟内有效
- 重复注册用户，如果账户未验证，将重新发送验证邮件

### 项目框架
- 后端：SpringBoot + MySQL + MyBatis Plus + Spring Security + Spring Email
- 邮箱服务：使用 [maildev](https://github.com/maildev/maildev)

### 接口
- 注册：
  ```bash
  curl --location --request POST 'localhost:8080/api/v1/registration' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "firstName": "Checo",
  "lastName": "Chan",
  "email": "checo.chan@gmail.com",
  "password": "password"
  }'
  ```

- 验证账户：
  ```bash
  # token 为注册接口返回值
  curl --request GET 'localhost:8080/api/v1/registration/confirm?token=e2ab277b-ebed-42fb-8e59-169f339d0617'
  ```

- 登录：

  ```bash
  curl --request POST 'localhost:8080/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data 'username=checo.chan@gmail.com&password=password'
  ```
  
- 登录成功：
  ```bash
  curl --request GET 'localhost:8080/login/success'
  ```

### 项目启动和使用
- 环境要求：
  - Docker
  - JDK 8
- 打包：`mvn clean package`
- 运行 [docker-compose.yml](https://github.com/ChecoChan/registration-with-email/blob/master/docker-compose.yml) 一键启动项目
- 使用注册接口注册
- 打开 [http://localhost:1080](http://localhost:1080) 接收验证邮件
- 使用登录接口验证登录