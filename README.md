[github-como-fazer-um-readme-md-bonitão](https://medium.com/@raullesteves/github-como-fazer-um-readme-md-bonit%C3%A3o-c85c8f154f8)

[formatação de readme - Markdown](https://github.com/luong-komorebi/Markdown-Tutorial/blob/master/README_pt-BR.md)

[link dos commits do curso](https://github.com/algaworks/curso-especialista-spring-rest)

# algafood-api
Projeto do curso Especialista REST - Algaworks

1. Introdução
2. Spring e Injeção de Dependências
3. Introdução ao JPA e Hibernate
4. REST com Spring
5. Super poderes do Spring Data JPA
6. Explorando mais do JPA e Hibernate
7. Pool de conexões e Flyway
8. Tratamento e modelagem de erros da API
9. Validações com Bean Validation
10. Testes de integração
11. Boas práticas e técnicas para APIs
12. Modelagem avançada e implementação da API
13. Modelagem de projeções, pesquisas e relatórios
14. Upload e download de arquivos
15. E-mails transacionais e Domain Events
16. CORS e consumo da API com JavaScript e Java
17. Cache de HTTP
18. Documentação da API com OpenAPI, Swagger UI e SpringFox
19. Discoverability e HATEOAS A Glória do REST
20. Evoluindo e versionando a API
21. Logging
22. Segurança com Spring Security e OAuth2
23. OAuth2 avançado com JWT e controle de acesso
24. Deploy em produção

**7. Pool de conexões e Flyway**
O flyway é usado para gerenciamento de criação e migração de tabelas e dados.

<dependency>  
	<groupId>org.flywaydb</groupId>  
	<artifactId>flyway-core</artifactId>  
</dependency>  



**6. Explorando mais do JPA e Hibernate**
join fetch no jpql serve para que apenas uma consulta seja feita retornando todos os relacionamentos de uma só vez, ao contrário da consulta sem o fetch que faz vários selects separadamente.

Exemplo de output sem o join fetch em get /pedidos
![](/img/getPedidosSemJoinFetch.png)


Exemplo de output com join fetch em jpql.
	@Query("from Pedido pedido "  
     + "join fetch pedido.usuarioCliente "
		 + "join fetch pedido.restaurante restaurante "
		 + "join fetch restaurante.cozinha ")
	List<Pedido> buscarTodosResumido();
![](/img/getPedidosComJoinFetch.png)


**8. Tratamento e modelagem de erros da API**

Podem ser criadas anotações personalizadas como por exemplo a anotação @Multiplo

**10. Testes de integração**

O ideal é testar somente o que agrega valor ao projeto, ou seja, ecrever apenas testes que garantam que o projeto não quebre.

Todo teste é dividido em 3 partes
1. Cenário
2. Açao
3. Validação

Cada teste deve testar apenas uma funcionalidade.

Rodar os testes pelo maven: Entrar no diretorio do projeto pelo terminal e digitar mvn test ou ./mvnw test
Fazer o build do projeto: mvn clean package - Sempre que fizer o build do projeto os testes serão executados.

maven-failsafe-plugin: plugin responsável por não deixar serem executados os testes de integração no momento do build do projeto, para executar os testes de integração caso seja necessário usar mvn verify.

Testes de API
São testes onde realmente é feita a chamada http ao serviço REST.

Ordem dos Testes.
Um teste não pode depender da execução ou não execução de outro teste, devem sempre funcionar de forma individual.

**11. Boas práticas e técnicas para APIs**

@Transactional: Essa anotação do sprint (org.springframework.transaction.annotation.Transactional) faz com que seja aberta uma transação na base de dados sempre que um método que manipula dados for chamado, a implementação do Spring Data JPA que é a classe SimpleJpaRepository já tem as operações como save, delete, update marcadas com @Transactional, porém como boa prática é interessante marcar os métodos dos nossos services que manipulam dados na base também, assim garantimos que não haja inconstência nos dados caso dê algúm problema e uma das operações e precise ser feito um rollback.
Com a anotação, é aberta uma transação no momento da chamada do método no service por exemplo, e não apenas quando for para o repository, o spring data JPA gerencia essas transações e executa na base de dados de acordo com a fila de gerenciamento de transações que ele cria.
Ao final do método é feito um commit na base de dados.
Caso queira forçar a execução pelo jpa é preciso usar um flush no método, não é um commit, é apenas a forma de fazer com que as operações sejam executadas na base.

Desserialização é de JSON para Objeto, serialização é de Objeto para JSON.

Mixim é uma classe que possui propriedades de uma outra classe original onde podem ser colocadas anotações que queremos deixar separadas da classe original para que fique mais coeso, como por exemplo anotações Jackson que são referêntes à API em classes de domínio, o ideal é colocar as anotações Jackson em uma classe mixim. (RestauranteMixim)  

TimeZone é o fuso horário utilizado em cada região do globo terrestre de acordo com o UTC, a diferença desses horários é chamada de offset.
UTC é a referência de horário principal de onde todas as demais regiões do mundo se baseiam, coincide com o GMT.
GMT é a TimeZone da linha principal do mundo (Prime Meridian ou Meridiano de Greenwich), é onde se inicia o TimeZone com 0, países que ficam à esqueda (Oeste) tem seus TimeZone's diminuídos em horas e países à direita (Leste) tem seus TimeZone's acrescidos em horas.
![](/img/WorldTimes.png)

1 - Usar ISO-8601 para formatar data/hora, é um padrão bem definido de representação de datas e horas, evitando problemas principalmente quando existem TimeZones diferentes envolvidos na API. 
ex: 2020-11-05T06:40:30Z, com o Z no final significa que está exatamente no UTC, sem nenhum offset, também pode ser especificado com o offset do UTC 2020-11-05T06:41:15-03:00 (nesse caso seria o horário seis e quarenta e um usando como referência o offset de Brasilia que é menos três horas).
2 - A API deve aceitar qualquer TimeZone de entrada e converter para o TimeZone que ela está usando.
3 - Armazene Data Hora sempre em UTC sem nenhum offset.
4 - Retone a Data Hora em UTC, caso o consumidor queira mostrar em um TimeZone específico ele retorna no front.
5 - Não inclua horário se não for necessário, é permitido armazenar apenas data.
O trabalho de calcular o TimeZone geralmente é do Frontend, a api deve salvar e retornar sempre em UTC.

DTO - Data Transfer Object, é o padrão usado para representação dos recursos, para que fique separado das classes de domínio.
Eu posso ter mais de um DTO para representar o mesmo recurso, por exemplo, caso nesse projeto eu queira retornar um Restaurante com dados resumidos, posso criar um DTO de retorno específico para isso e um completo.

@Bean - Quando é injetado algum componente que não faz parte do Spring, é preciso criar uma classe de configuração e uma instância com as anotações do spring, para que fiquem disponíveis sempre que o projeto inicia, como no caso do ModelMapper.

Funcionamento do ModelMapper - Ele transforma todas as propriedades das classes em tokens, depois compara os tokens de origem e destino, seguindo as regras 1 - Os nomes de tokens de origem precisam ser iguais aos tokens de destino, 2 - Não importa a ordem em que os tokens estejam. 3 - O nome da propriedade de origem, deve ter ao menos um token de correspondência.
Explicado na aula 11.15. Entendendo a estratégia padrão do ModelMapper para correspondência de propriedades

<!-- http://modelmapper.org/downloads/ -->  
<dependency>   
	<groupId>org.modelmapper</groupId>  
	<artifactId>modelmapper</artifactId>  
	<version>2.3.0</version> <!-- como o parent não tem o modelmapper, preciso especificar a versão  -->  
</dependency>  

Após importar as dependências é preciso criar a classe de configuração ModelMapperBean.


SnakeCase - Usa todas as palavras em minúsculo com separação por underline ex: valor_de_frete.
No spring o padrão é lowerCammelCase, caso queira ser alterado basta adicionar em application.properties
spring.jackson.property-naming-strategy=SNAKE_CASE, porém é recomendado manter o padrão do spring pois é 
o mais comun utilizado com JSON.


**12. Modelagem avançada e implementação da API**

Recurso de granularidade grossa é quando no retorno do JSON existem vários objetos aninnhados no mesmo recurso, como por exemplo, Restaurante e Endereços.
Recurso de granularidade fina é quando cada recurso representa uma parte, por exemplo um recurso /restaurates/1 retorna apenas os dados do restaurante e outro sub-recurso /restaurantes/1/endereco retorna apenas o endereco do restaurante, assim dividos cada um em um recurso.

Chatty API é uma API modelada com granularidade fina, seria Chatty (Tagarela) pelo fato de termos que fazer várias chamadas em recursos e sub-recursos nessa API.

Chunky API é uma API modelada com granulairdade grossa, Chunky (Pedaço Grande), nesse caso o consumidor da API faz todas as operações em uma única requisição, exemplo ao criar o restaurante já vai com o endereço junto no POST.

Os recursos da nossa API não necessáriamente precisam seguir os nomes dos domínios, podemos ter por exemplo um recurso chamado POST /notificacoes-restaurantes e nesse recurso passarmos um payload com um título e uma mensagem, não necessáriamente armazenando essas notificações na base, pode ser um envio de e-mail para todos os restaurantes por exemplo, e nesse caso não termos uma entidade Notificacao.

Toda vez que tem alguma alteração em uma transação com @Transactional, o JPA faz a sincronização com o banco de dados, mesmo antes do repository.save, caso não aconteça nenhuma exception na transação, ao final é feito o commit e as alterações salvas na base, mesmo sem o save do repository, se ocorrer exception é feito um rollback.
Para que o JPA não gerencie é preciso usar o método detach.

Singleton Resource é o recurso único, por exemplo quando é feita uma requisição REST em /pedidos/1

@OneToMany por default é Lazy
@ManyToOne por default é eager

cascade = CascadeType.ALL é usado para que o que for alterado em uma entidade se propague para a entidade relacionada, como por exemplo ao salvar um pedido, colocar o cascade nos itens para que os itens sejam salvos também.

Entidade Rica: Possui métodos de negócio com lógicas específicas também, como por exemplo a entidade Pedido que possui os métodos para os cálculos dos valores, transição de pedido, etc...
Entidade Anemica: Não tem nenhum método de negócio implementado.

Método de callback do JPA, é executado em alguns eventos do ciclo de vida das entidades um dos eventos é o @PrePersist, ele vai fazer com que antes de persistir a entidade esse método seja executado, como no exemplo usado em Pedido gerarCodigo().

**13. Modelagem de projeções, pesquisas e relatórios**

13.1 conforme o commit, pode ser verificado que apenas anotando as propriedades com o @JsonView já é possível gerar um retorno somente com as propriedades que foram anotadas.

O Uso de DTO ou JsonView vai de acordo com o que você preferir, de acordo com a necessidade do projeto, etc...

MappingJacksonValue é um Wrapper, ele possui vários métodos que nos permitem customizar o retorno de uma forma mais dinâmica, como por exemplo o setSerializationView, setFilters, etc...

SimpleBeanPropertyFilter é uma classe abstrata que possui vários métodos que podem ser utilizados para filtrar o retorno da requisição, como por exemplo o filterOutAllExcept() onde você pode apontar quais filtros precisa receber.

StringUtils.isNotBlank() valida se a string não está vazia.

OverEnginering, é quando tentamos resolver um problema que ainda nem aconteceu.

squiggly é um filtro para jackson que nos ajuda a limitar os retornos sem precisar implementar um filtro em cada elemento.
https://github.com/bohnman/squiggly
Commit 13.3. Limitando os campos retornados pela API com Squiggly
basta colocar no key o valor fields e filtrar os campos que quiser na requisição
se for colocado no campo sub* por exemplo, qualquer coisa que inicia com sub vai ser retornada.
No value da requisição nos parametros do fields codigo,valorTotal,sub*,usuarioCliente%5Bid,nome%5D
%5B é o encode de abre colchetes [ 
%5D é o encode de fecha colchetes ]
https://www.w3schools.com/tags/ref_urlencode.ASP 
Porém a classe TomcatCustomizer permite que usemos colchetes na url.
também pode ser negado codigo,valorTotal,sub*,usuarioCliente[-id], nesse caso vem todos os dados de cliente menos o id
É uma biblioteca pequena, open source, nesse caso corre-se o risco de parar de ser mantida, isso é um ponto a ser analisado.

PageJasonSerializer após criada essa classe, somente pelo fato de anotar com o @JsonComponent o spring boot já identifica e começa a utilizar as configurações do método serialize extedido de JsonSerializer quando uma Page é retornada.

Reports com Jaspersoft
https://community.jaspersoft.com/community-download
Baixar o Jasper Studio CE
Criar novo Projeto > new Jasper Report > Blank A4 > Renomear o report > One Empty Record > Next > Finish

Random Records - Clicar na aba Repository Explorer > Botão Direito > Create Data Adapter > Random Records
Selectionar no preview Random Records.

No Accept o consumidor da API precisa passar application/pdf,application/json no Headers para que caso exista
algum erro, o retorno possa ser devolvido em JSON.

Dependences japserreports https://search.maven.org/search?q=g:net.sf.jasperreports
Usar a mesma versão do jaspersoft studio que estiver usando para criar os reports  

Amazon S3 é o serviço de armazenamento de arquivos da amazon
IAM é o serviço de gerenciador de usuários e acessos.
Para acessar os arquivos no S3 é preciso associar às permissões de segurança e criar as chaves de acesso.
A Amazon disponbiliza uma SDK java para gerenciamento do serviço S3, para adicionar essa SDK basta incluir as dependências
dela no pom do projeto, ficam disponíveis no maven  

Serviço de e-mail usado para o desenvolvimento  
[sendgrid](https://app.sendgrid.com/login)  


Apache FreeMarker é a lib usada para renderizar o HTML dinamicamente.
[FreeMarker](https://freemarker.apache.org/)  

DDD (Domain Driven Development)  
Single Responsabiliti Principle - Cada componente deve ter apenas uma responsabilidade  
Aggregate Root - Classe agragada que ficará responsável por disparar o evento, só ela deve chamar os eventos, não deve ser disparado
pelo service.  
como o AbstractAggregateRoot que é extendido nas classes que vão registrar eventos é uma classe do 
spring data, só vai ser disparado o evendo se for chamado o método save() do repository.  
Para cada evento precisa-se de um Listener específico, assim o listener fica "ouvindo" os processos 
e quando for disparado um evento que interessa para ele o evento é capturado e executada uma ação específica.  
  

Comando para instalar o server http do node  
npm install http-server -g  
de dentro da pasta onde está o html iniciar o servidor  
http-server -p 8000  

CORS policy (Cors Origin Resource Sharing) 
Os navegadores usam uma politica de same origin, ou seja, só aceitam requisições da mesma origem, por motivos de segurança  
Origens cruzadas são restringidas  
Origem é (protocolo + dominio + porta) ex: http:// + localhost + 8080  
É preciso habilitar na aplicação para que funcionem as chamadas de origem cruzada  
essa habilitação é feita pelo header da requisição .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")  
nesse caso foi habilitada apenas para uma requisição que é o listar  
Quando é feita uma requisição de origens diferentes, os navegadores fazem um preflight e verificam quais os métodos  
permitdos e quais os origins permitidos, o método enviado no preflight é o OPTIONS, só é feito o preflight quando  
temos uma requisição simples.  
[Link com informações sobre o assunto](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)  

Aulas 16 (10, 11, 12) estão no projeto https://github.com/mucheniski/algafood-client

O que é Cache de HTTP?
É a habilidade que componentes de software tem de armazenar dados acessados frequentemente sem necessidade de acessar  
esses dados novamente.  
Freash - Representação fresca, ou seja, feito a pouco tempo.  
Stail - Representação que já foi feita a algúm tempo e precisa ser atualizada.  
isso é determinado por um tempo pré-determinado durante a requisição.  
Benefícios:  
Reduz o uso de banda  
Reduz a latência  
Reduz carga nos servidores  
Esconde problemas na rede  

O Postman não suporta Cache local, o correto é testar pelo navegador.  
Uma sugestão seria usar o Talend API Tester  


CACHE - Serve para evitar trafego e cosumo de rede desnecessários, quando é reito um request e o servidor envia o response, fica definido no  
Cache-Control: max-age=? um valor de tempo para que essas informações fiquem armazenadas em cache, assim durante esse tempo caso seja feito um
novo request, os dados são retornados diretamente do cache armazenado evitando que o servidor tenha que enviar novamente as mesmas informações  
após passado esse tempo, os dados em cache ficam com o status Stale, ou seja, caso seja feita um novo request o servidor vai precisar enviar 
novamente o response e os dados serem atualizados no cache para voltarem para Fresh.  
Porém existem as Etags que também auxilian para evitar consumo desnecessario mesmo que os dados de cache estejam es Stale.
Etag serve para validar se houve alguma alteração desde a última requisição feita no servidor quando o status do cache está stale  
ou seja, quando o max-age de cache já estourou e não está mais fresh, porém caso nada tenha sido alterado no servidor, a requisição  
vai com a Etag e o header If-None-Match, é feita uma comparação dessa Etag com a Etag que está no servidor, quando não teve alteração  
a do servidor vai estar igual a do request, assim não é necessário que o servidor envie um novo response, evitando tráfego e consumo  
de rede desnecessários.  
Para usar Etag é preciso habilitar o ShallowEtagHeaderFilter  
Deep Etag - é usado para evitar que todo o processamento seja feito do lado do servidor, caso não tenha acontecido alguma alteração já no servidor  
não é feito nada, vai ter o mesmo retorno 304 porém não é feito processamento no servidor, apenas validado se teve alguma alteração com um campo  
específico, como por exemplo a data de atualização, é feito um método pra buscar essa data e validar apenas ela se está igual, caso esteja o servidor  
já retorna sem processar nada, economizando processamento.  

Dosumentação com Open API  