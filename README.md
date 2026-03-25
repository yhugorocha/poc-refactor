# Agenda Back-End

## Visão geral

Este repositório contém o backend da aplicação `Agenda`.

Além da aplicação em si, este repositório também foi usado como base para uma POC de modernização tecnológica com o objetivo de validar a migração do backend de uma stack legada para uma stack mais atual do ecossistema Java e Spring.

Nesta POC:

- `x` = estado original do projeto, preservado na branch `main`;
- `y` = estado migrado do projeto, implementado na branch `feature/ref-openrewrite`.

A decisão foi manter as branches separadas. Portanto, a `main` continua representando a baseline original do projeto, enquanto a `feature/ref-openrewrite` concentra o resultado da modernização.

## Mapa da POC

| Branch | Papel na POC | Stack principal |
| --- | --- | --- |
| `main` | baseline original (`x`) | Java 8, Spring Boot 2.2.4.RELEASE, JJWT 0.9.1 |
| `feature/ref-openrewrite` | versão migrada (`y`) | Java 17, Spring Boot 3.0.13, JJWT 0.12.7 |

## Objetivo da prova de conceito

A POC foi criada para validar a viabilidade técnica da migração do projeto de:

- Java 8 para Java 17;
- Spring Boot 2.2.x para Spring Boot 3.x;
- APIs baseadas em `javax.*` para `jakarta.*`;
- configuração legada de segurança para o modelo atual do Spring Security.

Além da atualização de versões, a POC também avaliou o uso do OpenRewrite para automatizar parte da refatoração e reduzir o esforço manual em mudanças mecânicas e repetitivas.

## Estado atual por branch

### `main` (`x`)

A branch `main` representa o estado original da aplicação antes da modernização. Nela, o projeto permanece com:

- Java 8;
- Spring Boot `2.2.4.RELEASE`;
- JJWT `0.9.1`;
- uso de `javax.persistence`, `javax.validation` e `javax.servlet`;
- configuração de segurança baseada em `WebSecurityConfigurerAdapter`.

Esse é o ponto de partida da POC e deve ser tratado como referência para comparação.

### `feature/ref-openrewrite` (`y`)

A branch `feature/ref-openrewrite` representa o estado migrado da aplicação. Nela, foram aplicadas mudanças para compatibilizar o projeto com uma stack mais moderna:

- Java 17;
- Spring Boot `3.0.13`;
- JJWT `0.12.7` com artefatos modularizados;
- migração de `javax.*` para `jakarta.*`;
- reestruturação da configuração de segurança com `SecurityFilterChain`, `AuthenticationManager` e `DaoAuthenticationProvider`.

## O que foi validado na POC

A análise do diff entre `main` e `feature/ref-openrewrite` mostra que a POC cobriu os seguintes pontos principais:

| Tema | Antes (`main`) | Depois (`feature/ref-openrewrite`) |
| --- | --- | --- |
| Java | 8 | 17 |
| Spring Boot | 2.2.4.RELEASE | 3.0.13 |
| Security | `WebSecurityConfigurerAdapter` | `SecurityFilterChain` |
| JWT | `jjwt:0.9.1` | `jjwt-api`, `jjwt-impl`, `jjwt-jackson` `0.12.7` |
| Jakarta EE | `javax.*` | `jakarta.*` |
| Lombok | sem versão explícita | `1.18.44` |

## Uso do OpenRewrite na POC

O OpenRewrite foi usado como acelerador da migração em duas frentes.

### Migração para Java 17

Foi utilizada a recipe:

- `org.openrewrite.java.migrate.UpgradeToJava17`

Com a dependência:

- `org.openrewrite.recipe:rewrite-migrate-java:3.20.0`

Plugin utilizado:

- `org.openrewrite.maven:rewrite-maven-plugin:6.22.1`

Comandos executados:

```bash
mvn rewrite:dryRun
mvn rewrite:run
```

### Migração para Spring Boot 3

Foi utilizada a recipe:

- `org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0`

Com a dependência:

- `org.openrewrite.recipe:rewrite-spring:6.17.0`

Plugin utilizado:

- `org.openrewrite.maven:rewrite-maven-plugin:6.22.1`

Comandos executados:

```bash
mvn rewrite:dryRun
mvn rewrite:run
```

Na prática, o OpenRewrite ajudou principalmente em mudanças mecânicas, como:

- substituição de imports `javax.*` por `jakarta.*`;
- limpeza de imports obsoletos;
- ajustes estruturais iniciais exigidos pela atualização do ecossistema Java/Spring.

Os pontos de maior impacto funcional ainda exigiram ajuste manual, especialmente na configuração de segurança, na reorganização do `pom.xml` e na adaptação da integração com JWT.

## Principais mudanças observadas na branch migrada

As mudanças mais relevantes da POC na `feature/ref-openrewrite` foram:

- atualização do parent do projeto para Spring Boot `3.0.13`;
- definição de `java.version`, `maven.compiler.source` e `maven.compiler.target` em `17`;
- substituição da dependência monolítica do JJWT por módulos separados;
- extração do bean `PasswordEncoder` para configuração dedicada;
- substituição do modelo baseado em `WebSecurityConfigurerAdapter` por configuração com `SecurityFilterChain`;
- migração dos imports de `javax.*` para `jakarta.*` em entidades, controllers, DTOs e filtro JWT;
- atualização da API de parsing de JWT para o modelo suportado pela linha `0.12.x`.

## Resultado da POC

Do ponto de vista técnico, a POC demonstrou que a migração é viável e que o uso do OpenRewrite reduz esforço em mudanças repetitivas. A branch migrada compila com:

```bash
mvn -q -DskipTests compile
```

Ao mesmo tempo, a POC também evidenciou que a modernização não é apenas uma troca de versão. Alguns pontos continuam exigindo revisão cuidadosa, em especial:

- consistência entre a geração e a validação da chave JWT;
- validação da mudança do algoritmo de assinatura de `HS512` para `HS256`;
- ausência de suíte de testes automatizados para medir regressão funcional com mais segurança.

## Como navegar entre as branches

### Ver o baseline original

```bash
git checkout main
```

### Ver a versão migrada da POC

```bash
git checkout feature/ref-openrewrite
```

### Comparar as duas versões

```bash
git diff main...feature/ref-openrewrite
```

### Ver o documento detalhado da migração

A documentação detalhada da migração foi criada na branch `feature/ref-openrewrite`, no arquivo `MIGRATION.md`.

Para acessar esse documento, faça checkout da branch migrada:

```bash
git checkout feature/ref-openrewrite
```

Depois abra:

```text
MIGRATION.md
```

## Execução local

### Requisitos da `main`

- Java 8
- Maven
- MariaDB
- variáveis de ambiente configuradas

### Requisitos da `feature/ref-openrewrite`

- Java 17
- Maven
- MariaDB
- variáveis de ambiente configuradas

### Variáveis de ambiente usadas pelo projeto

As propriedades abaixo são usadas nas duas branches:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `SHOW_SQL`

### Subir banco local com Docker

O repositório possui um `docker-compose.yml` com MariaDB:

```bash
docker-compose up -d
```

### Build

Na `main`:

```bash
mvn clean package
```

Na `feature/ref-openrewrite`:

```bash
mvn clean package
```

### Execução

```bash
mvn spring-boot:run
```

## Observação final

Este `README.md` documenta a POC a partir da branch `main`, que foi mantida como baseline original. A modernização propriamente dita não foi incorporada nesta branch e permanece isolada em `feature/ref-openrewrite` para fins de análise, comparação e documentação técnica.
