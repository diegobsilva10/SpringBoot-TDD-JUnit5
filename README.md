
# Controller

A camada de Controller é utilizada para centralizar todos as requisições HTTP GET, POST, PUT, DELETE
## O que é o projeto?

O projeto é uma API de biblioteca, nela será possível fazer cadastros de livros, quantos forem necessários. Poderá ser feito a recuperação dos dados, adição de livros, editar livros existentes, e até mesmo deletar por meio dos métodos HTTP existentes: GET, POST, PUT, DELETE

## Documentação da API

```http
  POST "/api/books/
```

| Parâmetro | Tipo       | Descrição                                             |
|:----------| :--------- |:------------------------------------------------------|
| `id`      | `string` | **Obrigatório**. O ID do item que você quer criar     |
| `title`   | `string` | **Obrigatório**. O title do item que você quer criar  |
| `author`  | `string` | **Obrigatório**. O author do item que você quer criar |
| `isb`     | `string` | **Obrigatório**. O isb do item que você quer criar    |



## Autores

- [@Diego Silva](https://www.linkedin.com/in/diego-silva-2479711a7/)

