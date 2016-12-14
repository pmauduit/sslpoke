# About

A prometheus exporter to check if TLSv1.0 is available on some hosts to be tested.

# Compile

```
$ mvn clean compile docker:build
```

# Use

Use the `TLSV1_HOSTS` environment variable to define which hosts / https url
should be tested as a comma-separated list, e.g.:

```
$ docker run --rm -e TLSV1_HOSTS="https://www.cigalsace.org/,https://www.pigma.org/,https://www.ppige-npdc.fr/" pmauduit/tlsv1tester-prometheus
```



