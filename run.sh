#!/bin/bash
docker compose -f docker/docker-compose.yml down
docker compose -f docker/docker-compose.yml up -d
mvn spring-boot:run clean
