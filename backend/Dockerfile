# 사용할 Java 베이스 이미지 지정
FROM openjdk:21

# 애플리케이션 코드를 컨테이너로 복사
COPY . /app
WORKDIR /app

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew build -x test

# 빌드된 jar 파일을 작업 디렉토리로 복사
COPY ./build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java","-jar","/app/app.jar"]
