# Login API | Backend

API REST desenvolvida com foco em autenticação segura, arquitetura em camadas e boas práticas de backend utilizando Java e Spring Boot.

[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-6DB33F?logo=springboot\&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?logo=springsecurity\&logoColor=white)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-Auth-black?logo=jsonwebtokens)](https://jwt.io/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker\&logoColor=white)](https://www.docker.com/)

---

## Visão do projeto

API REST para autenticação e gestão de usuários, utilizando JWT em cookies HttpOnly, controle de acesso por roles e arquitetura em camadas com Spring Boot.
* autenticação baseada em JWT armazenado em cookies
* controle de acesso por roles (USER, ADMIN, SUPER_ADMIN)
* arquitetura limpa e escalável
* separação de responsabilidades (controller, service, repository)
* tratamento global de exceções
* integração com frontend moderno

---

## Tecnologias e ferramentas

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Maven
* Docker (Docker Compose)

---

## Arquitetura

```text
src/main/java/com/rodrigo/login/
  auth/        -> autenticação, filtros e geração de token
  config/      -> configurações (security, seed de dados)
  exception/   -> tratamento global de erros
  user/
    controller/ -> endpoints REST
    dto/        -> contratos de entrada e saída
    mapper/     -> conversão entre DTO e entidade
    model/      -> entidades do domínio
    repository/ -> acesso a dados
    service/    -> regras de negócio
```

### Benefícios da abordagem

* separação clara de responsabilidades
* facilidade de manutenção
* escalabilidade para novos módulos
* código mais testável e organizado

---

## Funcionalidades implementadas

* autenticação com login
* geração de JWT
* armazenamento do token em cookies HTTP
* filtro de autenticação por requisição
* controle de acesso baseado em roles
* CRUD completo de usuários
* ativação e desativação de usuários
* contagem de usuários ativos
* tratamento global de exceções
* seed automático de dados iniciais

---

## Segurança

A aplicação utiliza:

* JWT para autenticação stateless
* armazenamento do token em cookies HttpOnly
* Spring Security para proteção de rotas
* controle de acesso baseado em roles

💡 Observação: recomenda-se o uso de cookies com HttpOnly, Secure e SameSite para produção.

---

## Pré-requisitos

* Java 21
* Maven
* Docker (opcional)

---

## Como rodar o projeto

### 🔹 Rodando localmente

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

### 🔹 Rodando com Docker

```bash
docker-compose up -d
```

---

## Endpoints principais

### 🔐 Autenticação

* POST /auth/login

### 👤 Usuários

* GET /users
* GET /users/{id}
* POST /users
* PUT /users/{id}
* DELETE /users/{id}

---

## Qualidade do código

* arquitetura em camadas
* uso de DTO
* separação de responsabilidades
* tratamento global de erros

---

## Roadmap técnico

* testes automatizados
* refresh token
* rate limiting
* observabilidade
* deploy em cloud

---

## Autor

Desenvolvido por Rodrigo Nascimento.

Se este projeto te ajudou, considere dar uma estrela ⭐
