# 사용할 Java 베이스 이미지 지정
FROM openjdk:21

# 컨테이너 내에서의 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 소스 코드 복사 (gradlew, gradle 디렉토리, 소스 코드, build 파일 등)
COPY . .

# 빌드 권한 부여 및 빌드 실행
RUN chmod +x ./gradlew && ./gradlew build

# 빌드된 jar 파일을 작업 디렉토리로 복사
COPY ./build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java","-jar","app.jar"]

