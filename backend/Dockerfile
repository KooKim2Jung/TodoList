# 사용할 Java 베이스 이미지 지정
FROM openjdk:21

# 컨테이너 내에서의 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 작업 디렉토리로 복사
COPY ./build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java","-jar","/app/app.jar"]