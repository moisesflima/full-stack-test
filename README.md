# Golden Raspberry Awards - Full Stack Project

Este repositório contém a solução completa para o desafio técnico da **Outsera**, composto por uma API RESTful e uma interface administrativa para visualização de dados do **Golden Raspberry Awards** (Pior Filme).

O projeto está estruturado como um **monorepo**, facilitando a gestão do ecossistema de desenvolvimento e deploy.

---

## Estrutura do Repositório

* **golden-raspberry-awards-api/**: Backend desenvolvido em Java 21 e Spring Boot 3.2.5.
* **golden-raspberry-awards-ui/**: Frontend desenvolvido em Angular 19 com foco em performance.

---

## Backend: Golden Raspberry Awards API

O objetivo principal é processar um arquivo CSV de filmes e identificar os produtores com o maior e o menor intervalo entre dois prêmios consecutivos, conforme os requisitos do desafio.

### Tecnologias
* **Java 21** e **Spring Boot 3.2.5**.
* **H2 Database**: Banco de dados em memória para agilidade no desafio.
* **Spring Data JPA** e **SpringDoc OpenAPI (Swagger)**.

### Principais Endpoints
* **GET** `/api/movies/maxMinWinIntervalForProducers`: Retorna os intervalos de vitórias dos produtores.

### Execução Local (Maven)
```bash
cd golden-raspberry-awards-api
mvn spring-boot:run
```

---

## Frontend: Golden Raspberry Awards UI

Interface moderna para visualização das estatísticas e listagem detalhada dos indicados e vencedores.

### Tecnologias e Decisões Técnicas
* **Angular 19**: Utilização de **Standalone Components** e nova sintaxe de controle de fluxo (`@if`, `@for`).
* **Signals & Zoneless**: Gerenciamento de estado reativo de alta performance sem o overhead do Zone.js.
* **Lazy Loading**: Otimização do carregamento inicial da aplicação através de rotas sob demanda.

### Execução Local (Node.js)
```bash
cd golden-raspberry-awards-ui
npm install
npm start
```

---

## Testes e Qualidade

Ambos os projetos possuem suítes de testes automatizados para garantir a confiabilidade da solução.

| Projeto | Tecnologia de Teste | Comando |
| :--- | :--- | :--- |
| **Backend** | Testes de Integração (JUnit/Spring) | `mvn test` |
| **Frontend** | Vitest | `npm test` |

---

## Manipulação de Dados na API(CSV)

A aplicação está configurada para carregar os dados automaticamente a partir de um arquivo externo. Para testar com novos cenários:

1. Edite o arquivo `./golden-raspberry-awards-api/src/main/resources/movielist.csv`.
2. Reinicie o container Docker: Reinicie o container: `docker-compose restart api`.
3. A base de dados H2 será populada automaticamente no startup.