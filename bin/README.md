[github-como-fazer-um-readme-md-bonitão] (https://medium.com/@raullesteves/github-como-fazer-um-readme-md-bonit%C3%A3o-c85c8f154f8)

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

**Testes - Modulo 10**

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

**Boas práticas para API - Modulo 11**

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

SnakeCase - Usa todas as palavras em minúsculo com separação por underline ex: valor_de_frete.
No spring o padrão é lowerCammelCase, caso queira ser alterado basta adicionar em application.properties
spring.jackson.property-naming-strategy=SNAKE_CASE, porém é recomendado manter o padrão do spring pois é 
o mais comun utilizado com JSON.


**Modelagem avançada e implementação da API - Módulo 12**

Recurso de granularidade grossa é quando no retorno do JSON existem vários objetos aninnhados no mesmo recurso, como por exemplo, Restaurante e Endereços.
Recurso de granularidade fina é quando cada recurso representa uma parte, por exemplo um recurso /restaurates/1 retorna apenas os dados do restaurante e outro sub-recurso /restaurantes/1/endereco retorna apenas o endereco do restaurante, assim dividos cada um em um recurso.

Chatty API é uma API modelada com granularidade fina, seria Chatty (Tagarela) pelo fato de termos que fazer várias chamadas em recursos e sub-recursos nessa API.

Chunky API é uma API modelada com granulairdade grossa, Chunky (Pedaço Grande), nesse caso o consumidor da API faz todas as operações em uma única requisição, exemplo ao criar o restaurante já vai com o endereço junto no POST.

Os recursos da nossa API não necessáriamente precisam seguir os nomes dos domínios, podemos ter por exemplo um recurso chamado POST /notificacoes-restaurantes e nesse recurso passarmos um payload com um título e uma mensagem, não necessáriamente armazenando essas notificações na base, pode ser um envio de e-mail para todos os restaurantes por exemplo, e nesse caso não termos uma entidade Notificacao.

Toda vez que tem alguma alteração em uma transação com @Transactional, o JPA faz a sincronização com o banco de dados, mesmo antes do repository.save, caso não aconteça nenhuma exception na transação, ao final é feito o commit e as alterações salvas na base, mesmo sem o save do repository, se ocorrer exception é feito um rollback.
Para que o JPA não gerencie é preciso usar o método detach