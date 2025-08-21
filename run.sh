docker build -t monolith .
docker run --name monolith --network finance-network -p 9000:8080 monolith