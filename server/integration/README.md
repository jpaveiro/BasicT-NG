# Rotas para a integração

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
>>> {"message": "Sucesss"}
```
- Todas as informações são obrigatórias
- O id é gerado automaticamente de forma aleatória
- rg, cpf e telefone irão perder toda a formatação
- rg e cpf tem validadores
- a senha é criptografada antes de ser inserida no banco de dados
- apenas usuarios maiores de 18 anos podem ter conta


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
>>> null
```
- Edita um usuario com base no id e na senha