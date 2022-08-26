
# Camada de Teste JUnit5

Os testes foram escritos em modelos de testes unitários, ou seja, de forma prévia, é definido todas as saídas esperadas, como também os erros que serão lançados. Os testes serão escritos para cada rota que o projeto conter.
## O que é o projeto?

O projeto é uma API de biblioteca, nela será possível fazer cadastros de livros, quantos forem necessários. Poderá ser feito a recuperação dos dados, adição de livros, editar livros existentes, e até mesmo deletar por meio dos métodos HTTP existentes: GET, POST, PUT, DELETE

## Documentação da API

#### Anotações e métodos

```http
  @ExtendWith(SpringExtension.class)
  CRIA UM MINI CONTEXTO PARA RODAR O TESTE, COM AS CLASSES DE TESTES DEFINIDAS
```
```http
@AutoConfigureMockMvc
 CONFIGURA OS OBJETOS DE TESTE PARA FAZER AS REQUISIÇÕES
```

```http
.andExpect
 Utilizando esse método, você "desenha" um cenário esperado, ou seja, os dados que o teste espera receber para ser um teste bem sucedido.
```


## Autores

- [@Diego Silva](https://www.linkedin.com/in/diego-silva-2479711a7/)

