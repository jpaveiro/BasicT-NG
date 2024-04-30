# Rotas para a integração

## Usuário
- [POST] - /api/user/v1/set
```
{
    "name": "Admin",
    "cellphone": "(00) 00000-0000",
    "email": "admin@admin.org",
    "cpf": "000.000.000-00",
    "rg": "12.345.678-9",
    "stateRg": "SSP/SP",
    "birthDate": "2000-11-12",
    "password": "123"
}
>>> {"message": "Sucess"}
```
- Todas as informações são obrigatórias
- O id é gerado automaticamente de forma aleatória
- rg, cpf e telefone irão perder toda a formatação
- rg e cpf tem validadores
- a senha é criptografada antes de ser inserida no banco de dados
- apenas usuarios maiores de 18 anos podem ter conta

- [GET] - /api/user/v1/get
```
/api/user/v1/get?id=123456789
>>> {
    "name": "ADMIN",
    "token": null,
    "userId": null
}
```
- Espera o id do usuário
- Parametro id é obrigatório
- Retorna o nome do usuário

- [POST] - /api/user/v1/login
```
{
    "email": "admin@admin.org",
    "password": "123"
}
>>> {
  "name": "ADMIN",
  "token": "ccf74e405e8d480"
}
```
- O token é gerado automaticamente

- [PUT] - /api/user/v1/edit
```
{
    "name": "Admin",
    "cellphone": "(00) 00000-0000",
    "email": "admin@admin.org",
    "cpf": "000.000.000-00",
    "rg": "12.345.678-9",
    "stateRg": "SSP/SP",
    "birthDate": "2000-11-12",
    "password": "12345678",
    "id": "7317e83e9fc74e2-7317e83e9fc74e2-7317e83e9fc74e2"
}
>>> { "message": "Sucess" }
```
- Edita um usuario com base no id e na senha

## Produto
- [POST] - /api/product/v1/set
```
{
    "idProduct": "12345678",
    "name": "MAIONESE",
    "price": 8.47
}

>>> { "message": "Sucess: Product registered."}
```
- Todos os dados são obrigatórios
- idProduct é seu código de barras
- O nome do produto sera transformado para upperCase independente do jeito escrito

- [POST] - /api/product/v1/get
```
{
    "idProduct": "12345678",
}
>>> {
    "idProduct": "12345678",
    "name": "MAIONESE",
    "price": 8.47,
    "createdAt": "2024-04-27 19:45:18"
}
```
- idProduct é seu código de barras
- Retorna todos os dados do produto com base no código de barras