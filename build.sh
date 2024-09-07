echo build Frontend
pnpm -F ./front-end build

echo build Backend
mvn clean package -DskipTests

echo build Docker Image
docker buildx build --platform linux/amd64 -t pulsar-console:0.0.1-alpha .