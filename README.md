# basicT Next Generation

WebApp Ponto de Venda desenvolvido com Angular, SpringBoot e MySQL.
Desenvolvido por [Gabriel Araújo](https://github.com/themyntt), [João Pedro Aveiro](https://github.com/jpaveiro) e [Vinicius de Jesus](https://github.com/viniciusdevjesus).

## Pré-requisitos
- NodeJS >= 20.5.X
- Java Development Kit >= 17
- MySQL

## Uso (Desenvolvimento)
- Clone este repositório ```git clone https://github.com/jpaveiro/BasicT-NG/```

### Front-end
- Entre na pasta client
- Instale as dependências com ```npm install```
- Inicie o servidor do Angular com ```npm run start```

### Database
- Inicialize o MySQL
- Execute [este](./server/database/schema.sql) arquivo SQL

### Back-end
- Entre na pasta server
- Instale as dependências do arquivo [pom.xml](./server/pom.xml)
- Inicie o servidor do SpringBoot
- As rotas já configuradas estão [aqui](./server/integration/README.md)

#### Observações
- Você precisa que o banco de dados MySQL esteja inicializado e com o banco de dados previamente configurado para iniciar o SpringBoot

## Finalizações
- Este projeto é um reboot de outra versão do basicT, [caso queira dar uma olhada](https://github.com/themyntt/basicT)
- O desenvolvimento do projeto está na branch "develop"; está branch (main) terá apenas as versões estáveis