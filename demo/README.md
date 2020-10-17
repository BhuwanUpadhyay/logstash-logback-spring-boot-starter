# Getting Started

### ELK Stack

Before running your application you should start ELK Stack on your machine.

The best way to do that is through Docker containers.

- Create a Docker network to enable communication between containers via container name.

```shell script
 docker network create elk
```

- Run elasticsearch docker container.

```shell script
docker run -d --name elasticsearch --net elk -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.9.2
```

- Create logstash configuration file `logstash.conf`.

```shell script
cat <<EOF> ~/logstash.conf
input {
  tcp {
    port => 5044
    codec => json_lines
  }
}
output {
  elasticsearch {
    hosts => ["http://elasticsearch:9200"]
    index => "example-%{appname}"
  }
}
EOF
```

- Run logstash docker container.

```shell script
docker run -d --name logstash --net elk -p 5044:5044 -v ~/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.9.2
```

- Run kibana docker container.
```shell script
docker run -d --name kibana --net elk -e "ELASTICSEARCH_URL=http://elasticsearch:9200" -p 5601:5601 kibana:7.9.2
```