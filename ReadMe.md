## Setting up Postgres (Port 8082)

- Get the latest docker image for postgres
```shell
docker pull postgres
```
- Create a volume for postgres
```shell
docker volume create postgres_volume
```
- Run postgres container (at port 8082) with the volume
```shell
docker run -d \
  --name postgres \
  -e POSTGRES_PASSWORD=123 \
  -v postgres_volume:/var/lib/postgresql/data \
  -p 8082:5432 \
  postgres:latest
```
- Run psql to create database as needed
```shell
docker exec -it postgres psql -U postgres
```

- SQL Script for creating Postgres Table
```sql
create database loan_db;
\c loan_db
create table user_loan (loan_id varchar(50) primary key, user_id varchar(10), user_name varchar(255), loan_status varchar(10));
insert into user_loan values
    ('l123', 'u123', 'John Doe', 'APPLIED'),
    ('l124', 'u124', 'Joe Smith', 'APPROVED'),
    ('l125', 'u125', 'Kenny Rogers', 'REJECTED');
```
- Verify the details available
```sql
loan_db=# select * from user_loan;
 loan_id | user_id |  user_name   | loan_status 
---------+---------+--------------+-------------
 l123    | u123    | John Doe     | APPLIED
 l124    | u124    | Joe Smith    | APPROVED
 l125    | u125    | Kenny Rogers | REJECTED
(3 rows)
```

## Setting up MongoDB (Port 8092)

- Get the latest docker image for MongoDB Community Server
```shell
docker pull mongodb/mongodb-community-server:latest
```
- Create a volume for mongodb
```shell
docker volume create mongodb_volume
```
- Run mongodb container (at port 8092) with the volume
```shell
docker run -d \
  --name mongodb \
  -e MONGO_INITDB_ROOT_USERNAME=user \
  -e MONGO_INITDB_ROOT_PASSWORD=123 \
  -v mongodb_volume:/data/db \
  -p 8092:27017 \
  mongodb/mongodb-community-server:latest
```
- Run mongosh to create database as needed
```shell
docker exec -it mongodb mongosh -u user -p 123
```
- Check existing DBs
```sql
test> show dbs
admin   100.00 KiB
config   12.00 KiB
local    72.00 KiB
test>
```

- Script for creating Database and Collections
```sql
use credit_report_db
db.credit_report.insertOne({_id: 'u123', creditScore: 700, report: 'A sample report'})
db.credit_report.insertOne({_id: 'u124', creditScore: 800, report: 'A sample report'})
db.credit_report.insertOne({_id: 'u125', creditScore: 650, report: 'A sample report'})
```
- Verify the DB created
```sql
test> show dbs
admin             100.00 KiB
config             92.00 KiB
credit_report_db   72.00 KiB
local              72.00 KiB
```
- Verify the details inserted
```sql
credit_report_db> db.credit_report.find({})
[
  { _id: 'u123', creditScore: 700, report: 'A sample report' },
  { _id: 'u124', creditScore: 800, report: 'A sample report' },
  { _id: 'u125', creditScore: 650, report: 'A sample report' }
]
credit_report_db> 
```

## Setting up Kafka (Port 9092) and Kafka UI (Port 9091)

- Get the latest docker image for Apache Kafka
```shell
docker pull apache/kafka
```

- Run kafka container (at port 9092)
```shell
docker run -d \
  --name kafka \
  -p 9092:9092 \
  apache/kafka
```
- Run below command to verify that your cluster is up. Note that the default port for bootstrap server is 9092
```shell
docker exec -ti kafka /opt/kafka/bin/kafka-cluster.sh cluster-id --bootstrap-server :9092
```
- Now pull the Kafka-UI image and run it at port 9091
```shell
docker pull kafbat/kafka-ui:main
docker run -d \
  --name kafka-ui \
  -p 9091:8080 \
  -e DYNAMIC_CONFIG_ENABLED=true \
  -e KAFKA_CLUSTERS_0_NAME=local \
  -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=9093 \
  kafbat/kafka-ui:main
```
- Once the UI starts, you can access it from here: [http://localhost:9091](http://localhost:9091)

## Using Docker Compose
- A recommended approach is to use the docker-compose.yaml file, which sets all the above containers for you.
```shell
docker compose up
```
