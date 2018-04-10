# xy-inc
Projeto desenvolvido utilizando spring boot, com a ideia de expor serviços via REST que possibilitam cadastrar, buscar, deletar POIs (Points od Interest).
Pelo fato de expor os serviços desta forma é muito fácil escalar a aplicação (basta utilizar balanceadores), além de facilitar a integração com qualquer aplicação de qualquer linguagem.

## Banco de Dados
Para o armazenamento e consulta dos dados incluídos o banco utilizado foi o MySQL.
As informações da conexão com o mesmo estão no arquivo "application.properties"
```
spring.datasource.url=jdbc:mysql://localhost:3306/GPSPlatform
spring.datasource.username=root
spring.datasource.password=password
```
* Neste caso foi usado um banco local, com um "schema" de nome "GPSPlatform", para casos em que o banco é remoto, ou será utilizada outra base, é possível alterar os valores das propriedades acima e executar a aplicação normalmente.
* Existe um arquivo similar "test.properties", mas o mesmo é utilizado apenas para os testes automatizados.
* Para criar a tabela (única) que armazena os dados dos POIs basta executar o script abaixo:
```sql
create table poi (
  id int(6) unsigned auto_increment primary key,
  name varchar(50) not null,
  xcoordinate int(6) not null,
  ycoordinate int(6) not null
)
```
## Frameworks e Pré-Requisitos
```
* Java 8
* Spring MVC
* Spring Boot
* Apache Maven
* JUnit
* MySQL
* H2
```
* Para compilar a aplicação, basta digitar o seguinte comando na pasta do projeto:
```
mvn clean install
```
* Finalmente para executá-la, além do banco de dados (MySQL) estar preparado, basta digitar o seguinte:
```
mvn spring-boot:run
```
## Testes/Utilização da aplicação
Seguem abaixo todos os serviços expostos, para cada um deles está definido o método e quais são as variáveis/objetos de entrada.
Todas as mensagens que possuem um corpo estão no formato JSON (Content-Type: application/json)

### Inserir/Cadastrar POI na base:
* Método: POST
* URL: http://localhost:8080/poi/
* Entrada
```json
{
  "name": "Nome",
  "x": 20,
  "y": 10
}
```
* Retorno: Este método retorna uma resposta com um status, descrição e ID (caso de sucesso). Para os casos de sucesso o status é 0.
```json
{
  "status": 0,
  "description": "Success saving POI.",
  "id": 1
}
```

### Atualizar POI:
* Método: PUT
* URL: http://localhost:8080/poi/1
* Entrada: Mesmo objeto utilizado para cadastrar o POI. A diferença está na URL onde é enviado o ID do mesmo.
```json
{
  "name": "Nome",
  "x": 20,
  "y": 10
}
```
* Saída: Também é utilizado o mesmo objeto.
```json
{
  "status": 0,
  "description": "Success updating POI.",
  "id": 1
}
```

### Deletar POI:
* Método: DELETE
* URL: http://localhost:8080/poi/1
* Entrada: Não há necessidade de enviar nenhum objeto, apenas o ID na URL
* Saída: No mesmo padrão das anteriores:
```json
{
  "status": 0,
  "description": "Success removing POI.",
  "id": 1
}
```
