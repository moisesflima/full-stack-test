# Golden Raspberry Awards API

API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

## Requisitos do Desafio
O objetivo principal é ler um arquivo CSV de filmes e fornecer o produtor com o maior e o menor intervalo entre dois prêmios consecutivos, seguindo as especificações do desafio técnico para o cargo de desenvolvedor fullstack da Outsera.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **H2 Database**
- **SpringDoc OpenAPI (Swagger)**
- **Docker & Docker Compose**

## Como Executar o Projeto

### Opção 1: Via Docker (Recomendado)
Para rodar a aplicação usando Docker, basta executar o comando abaixo na raiz do projeto (onde está o arquivo `docker-compose.yml`):

```bash
docker-compose up --build -d
```

#### Alterando os Dados (CSV Externo)
A aplicação está configurada para mapear o arquivo CSV local para dentro do container. Para testar com novos dados:
1. Edite o arquivo `./src/main/resources/movielist.csv`.
2. Reinicie o container: `docker-compose restart api`.
3. A API carregará os novos dados automaticamente.

### Opção 2: Via Maven (Local)
Certifique-se de estar na raiz do projeto.

```bash
mvn spring-boot:run
```

## Documentação da API (Swagger)
Após iniciar a aplicação, você pode acessar a documentação interativa e testar os endpoints em:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Principais Endpoints

### Obter intervalos de prêmios de produtores
**GET** `/api/movies/maxMinWinIntervalForProducers`

Retorna os produtores com o maior e o menor intervalo entre vitórias consecutivas.

## Testes de Integração
Os testes foram ucodificados para garantir a conformidade com as respostas `200`, `400` e `500`

Para executar os testes:
```bash
mvn test
```

## Estrutura de Erros
A API segue o padrão de erro definido na especificação:
```json
{
  "status": "INTERNAL_SERVER_ERROR",
  "timestamp": "2026-03-17 13:30",
  "message": "Descrição amigável do erro"
}
```
