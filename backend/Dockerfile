FROM --platform=linux/amd64 gradle:8.4-jdk21-alpine as build

ENV APP_HOME=/apps

WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME

COPY gradle $APP_HOME/gradle

RUN chmod +x gradlew

RUN ./gradlew build || return 0

COPY src $APP_HOME/src

RUN ./gradlew clean build -x check --parallel

FROM --platform=linux/amd64 openjdk:21

ENV APP_HOME=/apps
ARG ARTIFACT_NAME=app.jar
ARG JAR_FILE_PATH=build/libs/todo-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME

COPY --from=build $APP_HOME/$JAR_FILE_PATH $ARTIFACT_NAME

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

## 사용할 Java 베이스 이미지 지정
#FROM openjdk:21
#
## 컨테이너 내에서의 작업 디렉토리 설정
#WORKDIR /app
#
## 프로젝트 소스 코드 복사 (gradlew, gradle 디렉토리, 소스 코드, build 파일 등)
#COPY . .
#
## 빌드 권한 부여 및 빌드 실행
#RUN chmod +x ./gradlew && ./gradlew build
#
## 빌드된 jar 파일을 작업 디렉토리로 복사
#COPY ./build/libs/*.jar app.jar
#
## 컨테이너 시작 시 실행할 명령어
#ENTRYPOINT ["java","-jar","app.jar"]

