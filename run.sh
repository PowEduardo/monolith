cd ..
cd database-library/
mvn clean install -DskipTests
cd ..
cd monolith/
mvn clean install -DskipTests
docker build -t monolith .
docker rm -f monolith 2>/dev/null || true
docker run --name monolith --network finance-network -p 9000:8080 -m 1g -d monolith