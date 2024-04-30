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

## Compra/Venda
- [POST] - /api/purchase/v1/sell
```
{
    "userId": "35b325ec59ef499-35b325ec59ef499-35b325ec59ef499",
    "productId": "12345678",
    "quantity": 2,
    "totalAmount": "1",
    "purchaseCode": "84df44663bf94e1"
}
```
- purchaseCode é gerado automaticamento pelo front-end
- totalAmount é o preço final
- userId é o indentificador para quem vendeu o produto
- purchaseCode é o código da venda

- [GET] - /api/purchase/v1/get?page=
```
>>> {
    "content": [
        {
            "idPurchase": "3d652148d239438-3d652148d239438-3d652148d239438",
            "idUser": "f7b96c1b7099464-f7b96c1b7099464-f7b96c1b7099464",
            "idProduct": "12345678",
            "productQuantity": 2,
            "totalAmount": 44.64,
            "purchaseDate": "2024-04-29 23:13:37",
            "purchaseCode": "qumy5p4m0ar"
        },
        {
            "idPurchase": "b3beef38125f4e7-b3beef38125f4e7-b3beef38125f4e7",
            "idUser": "f7b96c1b7099464-f7b96c1b7099464-f7b96c1b7099464",
            "idProduct": "12345679",
            "productQuantity": 1,
            "totalAmount": 11.2,
            "purchaseDate": "2024-04-29 23:13:36",
            "purchaseCode": "qumy5p4m0ar"
        },
        {
            "idPurchase": "dab1b5918b85416-dab1b5918b85416-dab1b5918b85416",
            "idUser": "f7b96c1b7099464-f7b96c1b7099464-f7b96c1b7099464",
            "idProduct": "1",
            "productQuantity": 1,
            "totalAmount": 243.27,
            "purchaseDate": "2024-04-29 22:59:19",
            "purchaseCode": "d7b40zgr17g"
        },
        {
            "idPurchase": "9873936034fa40c-9873936034fa40c-9873936034fa40c",
            "idUser": "f7b96c1b7099464-f7b96c1b7099464-f7b96c1b7099464",
            "idProduct": "12345678",
            "productQuantity": 2,
            "totalAmount": 44.64,
            "purchaseDate": "2024-04-29 22:59:18",
            "purchaseCode": "d7b40zgr17g"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 7,
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 7,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    },
    "numberOfElements": 4,
    "first": true,
    "empty": false
}
```
- O parametro page é obrigatório, ele começa em 1
- Retorna as ultimas 7 vendas com base na pagina
- As ultimas vendas sempre são as primeiras (retorna em ordem decresente)