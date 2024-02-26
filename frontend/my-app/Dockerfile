# 베이스 이미지 설정
FROM node:latest

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사 및 설치
COPY ./package.json /app/
RUN npm install

# 나머지 파일 복사
COPY . /app

# 애플리케이션 빌드
RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]
