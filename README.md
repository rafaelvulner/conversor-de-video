# Conversor de Vídeo

O **Conversor de Vídeo** é uma aplicação baseada em **Spring Boot** que permite a conversão de vídeos com a integração do **FFmpeg**.

## Características
- Aceita uploads de vídeo através de um controlador REST.
- Realiza o processamento de conversões de vídeo através de um serviço dedicado.
- A implementação Docker é facilitada com a inclusão de um arquivo `docker-compose.yml`.

## Configuração

Este projeto é construído usando **Maven**, como evidenciado pelo arquivo `pom.xml`. Algumas das principais dependências incluídas são:
- `spring-boot-starter-web` para o desenvolvimento de aplicações web usando o Spring MVC.
- `lombok` para reduzir a quantidade de código boilerplate em Java.
- `spring-boot-starter-test` para auxiliar na escrita de testes para a aplicação.
- `javacv` como uma ponte entre Java e FFmpeg, permitindo a manipulação e conversão de vídeos diretamente no Java.

A aplicação é construída com **Java 21** e empacotada como um projeto Maven.

## Detalhes Adicionais

Todos os arquivos essenciais para a execução e manutenção do aplicativo, como `.gitignore` e `docker-compose.yml`, estão incluídos. O **Git** é usado para controle de versão e um `.gitignore` adequado está presente para excluir arquivos não essenciais.

## Contribuições

Para quaisquer problemas, consultas ou sugestões, sinta-se à vontade para abrir uma **issue** ou enviar um **pull request**.
