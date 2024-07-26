# Encurtaqui

## Projeto de Encurtador de URL

## Nesse projeto vai ser usado:

- [ ]  Spring Boot
- [ ]  MongoDB
- [ ]  Lombok

## Por que NoSQL (MongoDB)?

MongoDB é uma boa opção para nosso caso de uso porque não precisamos realmente de um relacionamento entre dados, mas precisamos de velocidade rápida de leitura e gravação.

## Funcionalidades:

- [x]  Encurtar URL.
- [x]  URLs curtas que não têm pelo menos um clique por mês são desativadas.
- [x]  Estatísticas de acesso às URLs encurtadas (cliques).

## Possíveis Seguranças:

- [ ]  Captcha Integration
- [ ]  Rate Limiting

## Endpoint:

- [ ]  /v1/encurtaqui

## Testes:

### Criar uma URL curta

**POST** `http://localhost:8080/v1/encurtaqui?originalUrl=https://www.exemple.com.br/`

**Status**: 201

**Retorno**:

```json
{
    "id": "66a425b3a6fec523beaf9724",
    "original_url": "https://www.exemple.com.br",
    "short_url": "03kJg5",
    "clicks": 0,
    "created_at": "2024-07-26T19:39:47.1431748",
    "last_clicked": "2024-07-26T19:39:47.1431748"
}

```

# Redirecionar para a URL original
**GET** `http://localhost:8080/v1/encurtaqui/03kJg5`

**Status**: 200 OK

Redireciona para o site original.
