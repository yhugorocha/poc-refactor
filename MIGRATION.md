# Migração para Java 17 e Spring Boot 3

## 1. Introdução

Este repositório contém o backend da aplicação `Agenda`, implementado com Spring Boot, Spring Data JPA, Spring Security, JWT, envio de e-mail e integração com MariaDB.

A branch atual (`feature/ref-openrewrite`) concentra a migração da base de execução e do framework principal, saindo de Java 8 e Spring Boot 2.2.4.RELEASE para Java 17 e Spring Boot 3.0.13. O objetivo da migração foi alinhar o projeto com versões suportadas do ecossistema Java/Spring, remover pontos de incompatibilidade com APIs descontinuadas e preparar a aplicação para evoluções futuras com menor custo de manutenção.

Os benefícios esperados desta migração são:

- uso de uma LTS moderna do Java, com melhor suporte de plataforma;
- alinhamento com Spring Boot 3 e Spring Security 6;
- migração para o namespace `jakarta.*`, exigido pelo stack atual do Spring;
- atualização de dependências sensíveis, em especial JWT e Lombok;
- redução de risco operacional por dependências antigas fora do ciclo principal de manutenção.

## 2. Tecnologias antes e depois

| Item | Antes | Depois |
| --- | --- | --- |
| Java | 8 | 17 |
| Spring Boot | 2.2.4.RELEASE | 3.0.13 |
| Spring Security | `WebSecurityConfigurerAdapter` | `SecurityFilterChain` + `AuthenticationManager` + `DaoAuthenticationProvider` |
| JWT | `jjwt:0.9.1` | `jjwt-api`, `jjwt-impl`, `jjwt-jackson` em `0.12.7` |
| Servlet API | `javax.servlet.*` | `jakarta.servlet.*` |
| JPA | `javax.persistence.*` | `jakarta.persistence.*` |
| Validation | `javax.validation.*` | `jakarta.validation.*` |
| Lombok | sem versão explícita | `1.18.44` |

## 3. Uso do OpenRewrite

Embora a configuração do plugin não esteja versionada nesta branch, o processo de migração foi executado com OpenRewrite em duas etapas distintas: uma para modernização para Java 17 e outra para upgrade para Spring Boot 3.0.

### Etapa 1: migração para Java 17

Foi utilizado o `rewrite-maven-plugin` com a recipe `org.openrewrite.java.migrate.UpgradeToJava17`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.openrewrite.maven</groupId>
            <artifactId>rewrite-maven-plugin</artifactId>
            <version>6.22.1</version>
            <configuration>
                <activeRecipes>
                    <recipe>org.openrewrite.java.migrate.UpgradeToJava17</recipe>
                </activeRecipes>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.openrewrite.recipe</groupId>
                    <artifactId>rewrite-migrate-java</artifactId>
                    <version>3.20.0</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

Comandos executados:

```bash
mvn rewrite:dryRun
mvn rewrite:run
```

### Etapa 2: migração para Spring Boot 3

Em seguida, foi usado o `rewrite-maven-plugin` com a recipe `org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0`:

```xml
<plugin>
    <groupId>org.openrewrite.maven</groupId>
    <artifactId>rewrite-maven-plugin</artifactId>
    <version>6.22.1</version>
    <configuration>
        <activeRecipes>
            <recipe>org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0</recipe>
        </activeRecipes>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.openrewrite.recipe</groupId>
            <artifactId>rewrite-spring</artifactId>
            <version>6.17.0</version>
        </dependency>
    </dependencies>
</plugin>
```

Comandos executados:

```bash
mvn rewrite:dryRun
mvn rewrite:run
```

Pelo resultado do diff da branch, o OpenRewrite foi usado principalmente para acelerar mudanças mecânicas e repetitivas, como:

- substituição em lote de imports `javax.*` por `jakarta.*`;
- limpeza de imports obsoletos em classes afetadas;
- atualização de trechos impactados pela mudança de stack;
- modernização inicial da base para versões mais recentes de Java e Spring.

Na prática, a divisão entre automação e ajuste manual ficou assim:

### O que foi automatizado com apoio do OpenRewrite

- troca dos imports de `javax.persistence`, `javax.validation` e `javax.servlet`;
- remoção de imports não utilizados em alguns arquivos;
- parte do ajuste estrutural necessário para compatibilidade com Spring Boot 3.

### O que claramente exigiu ajuste manual

- reescrita da configuração de segurança para substituir `WebSecurityConfigurerAdapter`;
- criação de um bean separado para `PasswordEncoder`;
- reorganização das dependências do `pom.xml`;
- revisão do fluxo de autenticação JWT para a API nova do `jjwt 0.12.x`.

Em resumo: a migração foi assistida por OpenRewrite com recipes conhecidas para Java 17 e Spring Boot 3, mas essa configuração não foi mantida no estado versionado atual da branch.

## 4. Principais mudanças no código

### 4.1 Atualização do `pom.xml`

O `pom.xml` foi o ponto central da migração. As mudanças estruturais observadas foram:

- troca do parent do Spring Boot de `2.2.4.RELEASE` para `3.0.13`;
- definição explícita de `java.version`, `maven.compiler.source` e `maven.compiler.target` em `17`;
- atualização do JJWT de um artefato único (`0.9.1`) para três módulos (`0.12.7`);
- fixação do Lombok em `1.18.44`;
- inclusão explícita de `jakarta.servlet-api` e `jakarta.validation-api`.

Exemplo real da mudança:

```xml
<!-- Antes -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.4.RELEASE</version>
</parent>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
</properties>
```

```xml
<!-- Depois -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.0.13</version>
</parent>

<properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <jjwt.version>0.12.7</jjwt.version>
</properties>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>${jjwt.version}</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>${jjwt.version}</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>${jjwt.version}</version>
    <scope>runtime</scope>
</dependency>
```

### 4.2 Remoção do `WebSecurityConfigurerAdapter`

No estado anterior, a classe `SecurityConfig` herdava `WebSecurityConfigurerAdapter` e usava os métodos `configure(AuthenticationManagerBuilder)` e `configure(HttpSecurity)`. Esse modelo não é mais o padrão da stack atual.

Antes:

```java
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/agenda/usuarios/**").permitAll()
                .anyRequest().authenticated();
    }
}
```

Depois:

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/agenda/usuarios/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

Os impactos práticos dessa mudança foram:

- abandono da herança em favor de configuração por beans;
- substituição de `authorizeRequests()` por `authorizeHttpRequests(...)`;
- substituição de `antMatchers(...)` por `requestMatchers(...)`;
- registro explícito de `DaoAuthenticationProvider` e `AuthenticationManager`.

### 4.3 Extração do `PasswordEncoder` para configuração dedicada

O bean de `PasswordEncoder` deixou de ser criado dentro da classe de segurança e passou a existir em `PasswordEncoderConfig`.

```java
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Essa separação reduz acoplamento na classe de segurança e facilita a injeção do encoder em outros pontos, como `UsuarioController` e `UsuarioServiceImpl`.

### 4.4 Migração de `javax.*` para `jakarta.*`

O projeto passou a usar os namespaces exigidos pelo Spring Boot 3.

Exemplos reais:

```java
// Antes
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.Valid;
import javax.servlet.FilterChain;
```

```java
// Depois
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import jakarta.servlet.FilterChain;
```

Essa alteração aparece em entidades como `Usuario`, `Solicitante`, `Quadra`, `Reserva` e `Email`, além de controllers e do filtro JWT.

### 4.5 Mudanças na integração com JWT

No estado anterior, o projeto usava a API antiga do JJWT:

```java
return Jwts
        .builder()
        .setSubject(usuario.getLogin())
        .setExpiration(data)
        .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
        .compact();

return Jwts
        .parser()
        .setSigningKey(chaveAssinatura)
        .parseClaimsJws(token)
        .getBody();
```

Depois da migração:

```java
return Jwts
        .builder()
        .setSubject(usuario.getLogin())
        .setExpiration(data)
        .signWith(SignatureAlgorithm.HS256, chaveAssinatura)
        .compact();

SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
```

O código foi atualizado para a API nova do parser, mas essa migração também introduziu um ponto de atenção documentado na seção de problemas: a geração e a validação do token não derivam a chave da mesma forma.

## 5. Problemas encontrados durante a migração

Esta seção documenta apenas problemas que puderam ser inferidos diretamente do diff, do estado atual do código e da validação executada na branch.

### 5.1 Quebra do modelo antigo de configuração de segurança

**Erro / sintoma**

A implementação anterior dependia de `WebSecurityConfigurerAdapter`, `configure(AuthenticationManagerBuilder)` e `antMatchers(...)`, padrão que não é mais o caminho atual com Spring Boot 3 / Spring Security 6.

**Causa**

O projeto foi promovido para uma linha do Spring em que a configuração de segurança baseada em herança deixou de ser a abordagem suportada para novas configurações.

**Solução aplicada**

Reescrita completa da configuração para:

- `SecurityFilterChain`;
- `DaoAuthenticationProvider`;
- `AuthenticationManager(AuthenticationConfiguration)`;
- `requestMatchers(...)` no lugar de `antMatchers(...)`.

### 5.2 Migração obrigatória de `javax.*` para `jakarta.*`

**Erro / sintoma**

Imports antigos de JPA, Validation e Servlet ficariam incompatíveis com Spring Boot 3.

**Causa**

O Spring Boot 3 opera sobre Jakarta EE 9+, o que exige a troca de namespace de `javax` para `jakarta`.

**Solução aplicada**

Substituição dos imports em entidades, DTOs, controllers e filtro JWT. Esse ajuste aparece em arquivos como:

- `src/main/java/io/github/yhugorocha/domain/entity/Usuario.java`
- `src/main/java/io/github/yhugorocha/rest/controller/UsuarioController.java`
- `src/main/java/io/github/yhugorocha/security/jwt/JwtAuthFilter.java`

### 5.3 Mudança da API do JJWT

**Erro / sintoma**

O código anterior usava o parser legado:

```java
Jwts.parser()
    .setSigningKey(chaveAssinatura)
    .parseClaimsJws(token)
```

**Causa**

O projeto deixou de usar `jjwt 0.9.1` e passou para `0.12.7`, onde a API e a composição de dependências foram alteradas.

**Solução aplicada**

- substituição do artefato único por `jjwt-api`, `jjwt-impl` e `jjwt-jackson`;
- troca do parser antigo por `verifyWith(key).build().parseSignedClaims(token)`.

### 5.4 Compatibilidade do Lombok com JDK moderno

**Erro / sintoma**

Projetos migrados de Java 8 para Java 17 frequentemente sofrem com geração incorreta de código ou problemas de compilação quando o Lombok está desatualizado.

**Causa**

Versões antigas do Lombok não acompanham integralmente as mudanças de compilador e processamento de anotações dos JDKs mais recentes.

**Solução aplicada**

Fixação explícita do Lombok em `1.18.44` no `pom.xml`.

### 5.5 Inconsistência na chave usada para gerar e validar JWT

**Erro / sintoma**

O código atual gera o token com:

```java
.signWith(SignatureAlgorithm.HS256, chaveAssinatura)
```

e valida com:

```java
SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));
```

**Causa**

A geração usa diretamente a `String` configurada em `security.jwt.chave-assinatura`, enquanto a validação assume que a mesma chave está em Base64 e a converte para `SecretKey`.

**Impacto**

Dependendo do formato real de `JWT_SECRET`, a aplicação pode:

- gerar tokens que não são validados pela própria aplicação;
- aceitar apenas chaves em um formato não documentado;
- apresentar erro de autenticação intermitente ou falha total na validação.

**Status**

Esse problema está visível no estado atual da branch. Ele deve ser tratado como risco remanescente da migração, não como ponto resolvido.

### 5.6 Mudança de algoritmo de assinatura JWT

**Erro / sintoma**

O algoritmo mudou de `HS512` para `HS256`.

**Causa**

Durante a adaptação para a API nova do JJWT, a implementação atual trocou o algoritmo originalmente utilizado.

**Impacto**

- quebra de compatibilidade com tokens antigos;
- alteração dos requisitos de tamanho e derivação da chave;
- necessidade de validar se a mudança foi intencional do ponto de vista funcional e operacional.

**Status**

A alteração está presente no código, mas o histórico da branch não mostra justificativa versionada para a troca.

### 5.7 Rastreabilidade parcial do OpenRewrite

**Erro / sintoma**

O processo de migração com OpenRewrite é conhecido, mas a configuração do plugin e das recipes não ficou versionada no estado atual do repositório.

**Causa**

Os comandos e recipes usados na migração foram executados fora do `pom.xml` final que ficou nesta branch, então o histórico atual não reproduz a automação por si só.

**Solução aplicada**

Documentar explicitamente neste `MIGRATION.md` as recipes e comandos efetivamente usados:

```bash
mvn rewrite:dryRun
mvn rewrite:run
```

Recipes documentadas:

- `org.openrewrite.java.migrate.UpgradeToJava17`
- `org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0`

### 5.8 O que não foi possível confirmar

Durante a análise desta branch:

- não foi identificada evidência concreta de dependência circular entre `SecurityConfig` e `UsuarioServiceImpl`;
- não foi possível confirmar um erro real de tamanho de chave para HS512 porque o algoritmo antigo já não está em uso no estado atual;
- não há logs versionados nem testes automatizados em `src/test` que permitam documentar regressão funcional com maior profundidade.

## 6. Lições aprendidas

- migrações de Spring Boot 2.x para 3.x exigem tratar segurança e Jakarta como itens de primeira classe, não como detalhes secundários;
- atualizar apenas a versão do parent no `pom.xml` não resolve a migração; segurança, JWT e namespaces precisam ser revistos em conjunto;
- se OpenRewrite for usado, vale versionar plugin, recipes e relatório de execução para manter rastreabilidade;
- em migrações de autenticação, a forma de derivar a chave deve permanecer consistente entre emissão e validação do token;
- bibliotecas de suporte como Lombok devem ser atualizadas junto com o JDK, não apenas depois de erros de compilação;
- a ausência de testes automatizados reduz muito a confiança da migração, mesmo quando a aplicação compila.

## 7. Como rodar o projeto após a migração

### Requisitos

- Java 17
- Maven compatível com o build do projeto
- acesso a um banco MariaDB
- configuração de credenciais de e-mail e segredo JWT

### Variáveis de ambiente relevantes

As seguintes propriedades são consumidas por `src/main/resources/application.properties`:

| Variável | Uso |
| --- | --- |
| `DB_URL` | URL de conexão com o banco |
| `DB_USERNAME` | usuário do banco |
| `DB_PASSWORD` | senha do banco |
| `MAIL_HOST` | host SMTP |
| `MAIL_PORT` | porta SMTP |
| `MAIL_USERNAME` | usuário SMTP |
| `MAIL_PASSWORD` | senha SMTP |
| `JWT_SECRET` | segredo usado na assinatura do token |
| `JWT_EXPIRATION` | expiração em minutos; default `30` |
| `SHOW_SQL` | controle de log SQL; default `true` |

### Build

```bash
mvn clean package
```

### Execução

```bash
mvn spring-boot:run
```

### Validação executada nesta análise

Foi validado que a branch atual compila com:

```bash
mvn -q -DskipTests compile
```

Não foi executada validação funcional automatizada porque o repositório não possui suíte de testes versionada em `src/test`.
