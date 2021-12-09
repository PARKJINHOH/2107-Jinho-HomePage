# 2107_Jinho_HomePage

# Stack
0. JDK : 11
1. Spring Boot 2.5.2
2. Spring Data JPA
3. [Server] Oracle Cloud - Ubuntu Server 20.03
   1. MariaDB
   2. Nginx
   3. Tomcat 10.0.12
      t.ly/tUkx
### ETC
1. Google OAuth2
2. Jenkins

# Todo List
* [로그인 페이지]
1. ~~Google OAuth2 구현하기~~
   * bit.ly/3mvjD3p // 구현 방법
   * bit.ly/3lmK7Vb // Google Cloud Platform
2. ~~Email 인증~~
3. ~~이메일 중복 구현~~
4. Remember Me 구현
5. [application.yml Git Ignore 분리하기]
   * properties 환경 분리하기 ex) 리눅스 내부 폴더에 있는 파일 읽게 하기.
     이후 properties 파일이 변경되면 해당 디렉토리에 덮어쓰기6
6. Dev Server - Prod Server 배포과정 구축하기
   1. Jenkins 구축
   2. Jenkins - Github 연동


* [Error]
1. OAuth2 Google에서 제공하는 name이 안나옴. -> db에 null값 저장.