# Desafio frontend

Feito o backend, será preciso criar uma aplicação em React e Typescript/Javascript, utilizando a biblioteca MUI, com duas rotas: /transacoes e /extrato.
Essas duas rotas estarão abertas em duas janelas/abas diferentes do navegador. O frontend deve rodar na porta 3000 e rodar junto com o backend no docker compose.

## /transacoes

Na tela de transações, só será preciso um botão que gere uma chamada ao endpoint
POST /clientes/[id]/transacoes com um usuário já cadastrado pelo sql
de inicialização (script.sql). Os valores podem ser aleatórios. Retornada a resposta da requisição, será preciso comunicar a outra aba/janela da requisição feita, passando o id do usuário utilizado. Há mais de uma forma de passar essa mensagem para outras abas/janelas, escolhar qualquer uma.

## /extrato

Recebendo a mensagem da janela/aba /transacoes, deverá ser feita uma chamada ao endpoint
GET /clientes/[id]/extrato com o id utilizado, mostrando em tela o resultado.