# Desafio técnico Softplan - Full Stack - Gestão Pública - Ambiental - 2024

## Setup do projeto

Para subir o projeto, você precisa ter o docker instalado em sua maquina.

Abra o terminal e navegue até o diretório raiz do seu projeto.

Primeiro vamos subir o database separadamente para garantir que esteja 100% up antes de subirmos a aplicação.

```
docker compose up db
```

Após finalizado setup do database suba o restante do compose:

```
docker-compose up
```

Após isso a aplicaçao deverá estar rodando.

## FRONTEND
O front-end está disponivel na porta:

http://localhost:3000




## BACKEND
As requisiçoes ao backend deverão ser feitas diretamente para a porta do gateway disponível em:

### GET EXTRATO: 

o endpoint EXTRATO está disponível através do verbo GET na URL abaixo, onde o parametro necessário é o ID do usuário.
Por pré carregamento do database, estao disponiveis os clientes com IDs 1,2,3,4 e 5

http://localhost:9999/clientes/{id}/extrato

o retorno deverá ser um JSON com a seguinte formatação:
HTTP 200 OK
```
{
    "saldo": {
        "total": -9098,
        "data_extrato": "2024-01-17T02:34:41.217753Z",
        "limite": 100000
    },
    "ultimas_transacoes": [
        {
        "valor": 10,
        "tipo": "r",
        "descricao": "descricao",
        "realizada_em": "2024-01-17T02:34:38.543030Z"
        },
        {
        "valor": 90000,
        "tipo": "d",
        "descricao": "descricao",
        "realizada_em": "2024-01-17T02:34:38.543030Z"
        }
    ]
}
```

### POST TRANSAÇÃO:
O endpoint TRANSAÇÕES disponível através do verbo POST na URL abaixo, onde o parametro necessário é o ID do usuário e 
no body um JSON seguindo a seguinte formatação:

http://localhost:9999/clientes/{id}/transacoes

```
{
    "valor": 1000,
    "tipo" : "r",
    "descricao" : "descricao"
}
```

Onde:
- [id] (na URL) deve ser um número inteiro representando a identificação do cliente.
- valor deve ser um número inteiro positivo que representa centavos (não vamos trabalhar com frações de centavos). Por exemplo, R$ 10 são 1000 centavos.
- tipo deve ser apenas r para recebível ou d para débito.
- descricao deve ser uma string de 1 a 10 caracteres.

A API deverá retornar um JSON com as seguintes informações:
HTTP 200 OK
```
{
    "limite" : 100000,
    "saldo" : -9098
}
```


# RESULTADOS DO TESTE DE CARGA
O teste de cargas foi realizado conforme orientação do projeto porém não obteve resultados satisfatórios.
Acredito que o SpringBoot nao se mostrou eficiente o suficiente para lidar com a demanda, dessa forma, a grande maioria 
das requisições retornou status 502, mostrando que a API nao suportou a demanda. Para otimizar as consultas ao database 
utilizei JDBC ao invés de JPA mesmo assim o resultado não foi satisfatório. Acredito que talvez utilizando o Java em sua 
forma mais 'pura' sem nenhum framework esses resultados possam ser otimizados.