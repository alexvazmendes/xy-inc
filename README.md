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
```
create table poi (
id int(6) unsigned auto_increment primary key,
name varchar(50) not null,
xcoordinate int(6) not null,
ycoordinate int(6) not null
)
```
