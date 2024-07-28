
# ItauSeguros

O projeto consiste no desenvolvimento de uma API REST voltada para o cálculo, gerenciamento e consulta de preços tarifados de produtos de seguros. A API oferece a capacidade de calcular o preço tarifado com base no preço base fornecido e na categoria do seguro, bem como consultar os cálculos realizados anteriormente.




## Funcionalidades

- **POST**: Recebe informações sobre o produto de seguros, como Categoria, Nome e Preço Base. Utiliza esses dados para calcular o preço tarifado, armazena as informações no banco de dados e retorna o resultado na resposta.

  
Exemplo de Request
```
/system-insurance/products
```

Body:
```
  {
    "name": "Seguro Residencial",
    "category": "VIDA",
    "basePrice": 100.00
  }
```
Exemplo de Response

```
{
    "id": "6b35038b-3ac9-4b29-94d2-1955e1d41c86",
    "name": "SEGURO RESIDENCIAL",
    "category": "VIDA",
    "basePrice": 100.0,
    "tariffPrice": 103.2
}
```
----------------------------------------------------------------------
- **PUT**: Atualiza os dados armazenados no banco de dados e recalcula o preço tarifado com base no novo preço base informado.

Exemplo de Request
```
/system-insurance/products/{product_id}
```

 Body: 
```
  {
    "name": "Seguro de carro",
    "category": "AUTO",
    "basePrice": 4555.33
  }
```

Exemplo de Response:

```
{
    "id": "6b35038b-3ac9-4b29-94d2-1955e1d41c86",
    "name": "SEGURO DE CARRO",
    "category": "AUTO",
    "basePrice": 4555.33,
    "tariffPrice": 5033.63
}
```


| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `product_id`        | `string` | **Opcional**. Id do registro.         |
| `basePrice` | `double` | **Obrigatório**. Preço base.         |
| `category`  | `string` | **Obrigatório**. Categoria do produto.   |
| `name`      | `string` | **Obrigatório**. Nome do produto.        |

------------------------------------------------------------------------
  
- **GET**: Consulta as requisições armazenadas no banco de dados e permite filtragem com base nos seguintes critérios:
  
 

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id`        | `string` | **Opcional**. Id do registro.         |
| `category`  | `string` | **Opcional**. Categoria do produto.   |
| `name`      | `string` | **Opcional**. Nome do produto.        |
| `_sort`     | `string` | **Opcional**. Ordenação               |
| `_limit`    | `int`    | **Opcional**. Número maximo de itens  por resposta |
| `_offset`  | `string` | **Opcional**. Número de itens a serem ignorados antes de começar a retornar os resultados.     |


Exemplo de Request:      
```
 /system-insurance/products

 http://localhost:8080/system-insurance/products?category=VIDA&_sort=category
```

Exemplo de Response:

```
{
    "_PageableProducts": {
        "_limit": 10,
        "_offset": 0,
        "_pageNumber": 0,
        "_pageElements": 1,
        "_totalPages": 1,
        "_totalElements": 1
    },
    "_content": [
        {
            "id": "6b35038b-3ac9-4b29-94d2-1955e1d41c86",
            "name": "SEGURO DE VIDA",
            "category": "VIDA",
            "basePrice": 100.0,
            "tariffPrice": 103.2
        }
    ]
}
```

------------------
    




## Documentação da API

### Estrutura do Projeto
O projeto é implementado em Spring Boot e utiliza os princípios SOLID em conjunto com OpenAPI para garantir uma implementação robusta e bem estruturada. A seguir estão descritos os principais componentes e princípios utilizados:

#### Estrutura de Pastas
O projeto está organizado nas seguintes pastas:

- **advice**: Contém a classe GlobalExceptionHandler, que trata exceções globais da aplicação.
- **config**: Inclui a classe DatabaseConfig, responsável pela configuração do HikariCP para gerenciamento de conexões com o banco de dados.
- **controller**: Contém a classe Controller, que implementa a interface ProductsApi, gerada pelo OpenAPI através de um contrato em Swegger. Essa classe lida com as requisições HTTP e mapeia os endpoints da ProductsApi.
- **domain**: Inclui a classe TariffCalculator, que encapsula a lógica de domínio específica do cálculo de tarifas.
- **mapper**: Contém a classe ProductMapper, responsável pela conversão entre entidades em DTOs e vice e versa.
- **metrics**: Inclui a classe MetricsService, que gerencia e coleta métricas de desempenho da aplicação.
- **model**: Contém a classe ProductEntity, que representa as entidades de dados no banco de dados.
- **repository**: Inclui a classe ProductRepository, que gerencia a persistência e acesso aos dados.
- **service**: Contém a interface ProductService e a implementação ProductServiceImpl, que encapsulam a lógica de negócios e interagem com o repositório.

#### **Princípios SOLID**:
O projeto segue os princípios SOLID para promover um design modular, flexível e fácil de manter. A seguir estão os detalhes de como cada princípio é aplicado:

  **Single Responsibility Principle (SRP)**:
  Cada componente do sistema tem uma responsabilidade única e bem definida, como    evidenciado pela estrutura de pastas:

- **controller**: Manipula as requisições HTTP.
- **service:** Contém a lógica de negócios.
- **repository**: Gerencia a persistência de dados.
- **domain**: Representa a lógica de domínio.
- **model**: Representa entidades de dados.
- **mapper**: Converte entre entidades e modelos.
- **advice**: Trata exceções globais.
- **metrics**: Coleta e gerencia métricas.


**Open/Closed Principle (OCP)**:
- O design permite a extensão de comportamentos sem modificar o código existente. Por exemplo, a interface ProductService pode ser implementada por várias classes, permitindo a adição de novos serviços sem alterar o código da classe principal.

**Liskov Substitution Principle (LSP)**:
- A classe ProductServiceImpl implementa a interface ProductService, garantindo que o código que utiliza ProductService funcionará corretamente com qualquer implementação dessa interface.

**Interface Segregation Principle (ISP)**:
- As interfaces são projetadas para serem coesas e específicas, evitando forçar clientes a implementar métodos que não utilizam. Isso promove um design mais limpo e focado.

**Dependency Inversion Principle (DIP)**:
O princípio da inversão de dependência é aplicado através da injeção de dependências via construtor, em vez de anotações diretamente nos campos. Isso promove um acoplamento fraco e permite uma melhor testabilidade e manutenção. No exemplo a seguir, os serviços são injetados por meio do construtor:

```
private final ProductService productService;
private final MetricsService metricsService;

@Autowired
public Controller(ProductService productService, MetricsService metricsService) {
    this.productService = productService;
    this.metricsService = metricsService;
}
```

#### **Integração com OpenAPI**
O projeto utiliza OpenAPI para gerar contratos de API e documentar as interfaces da API:

- **OpenAPI**: Gera classes com base no contrato definido, que são armazenadas no diretório target da aplicação. A interface API gerada é implementada no controlador (controller), garantindo que a implementação da API esteja alinhada com a documentação e o contrato definido.

### MetricsService
A classe MetricsService é responsável por definir e gerenciar as métricas para monitoramento da aplicação usando Micrometer. Ela contém:

- **Contadores**:
    - **productsGetCounter**: Conta o número de requisições para o endpoint productsGet.
    - **productsPostCounter**: Conta o número de requisições para o endpoint productsPost.
    - **productsProductIdPutCounter**: Conta o número de requisições para o endpoint productsProductIdPut.

- **Timers**:
  - **productsGetTimer**: Mede o tempo de execução das operações do endpoint productsGet.
  - **productsPostTimer**: Mede o tempo de execução das operações do endpoint productsPost.
  - **productsProductIdPutTimer**: Mede o tempo de execução das operações do endpoint productsProductIdPut.

- **Métodos Principais**:
  - **incrementProductsGetCounter**: Incrementa o contador de requisições para productsGet.
  - **recordProductsGetTime**: Mede o tempo de execução de um bloco de código para productsGet.
  - **incrementProductsPostCounter**: Incrementa o contador de requisições para productsPost.
  - **recordProductsPostTime**: Mede o tempo de execução de um bloco de código para productsPost.
  - **incrementProductsProductIdPutCounter**: Incrementa o contador de requisições para productsProductIdPut.
  - **recordProductsProductIdPutTime**: Mede o tempo de execução de um bloco de código para productsProductIdPut.

Essas métricas são integradas com o Prometheus e, posteriormente, podem ser visualizadas no Grafana. O Grafana permite a criação de dashboards personalizados para monitorar e analisar o desempenho e a saúde da aplicação, oferecendo uma visão gráfica e detalhada das métricas coletadas.


### Testes

Para os cenários de teste, foi utilizada a biblioteca Cucumber em conjunto com JUnit para definir e executar os testes de integração. Retrofit foi escolhido para realizar as chamadas HTTP, pois permite a definição de métodos via interface, o que resulta em um código mais limpo e organizado.


## Observação:
Collection anexada na raiz do projeto.












