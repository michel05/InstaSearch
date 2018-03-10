# InstaSearch
Aplicação para coleta de dados do Instagram

O InstaSearch é uma aplicação de monitoramento de publicações no Instagram. 

### Funcionalidades ###
- **Monitoramento em tempo real das publicações
- **Gera relatórios em excel para análise explícita de cada publicação monitorada.
- **Gráficos para análise das publicações (em desenvolvimento)

### Como funciona ###
O processo como um todo é realizado a partir de um monitoramento minuto a minuto que permite "escutar" quando um novo post é publicado. As informações são descritas como: quantidade e texto (OCR), identificação de objetos nas imagens (CLOUD VISION API), quantidade de "likes", quantidade de "menções", quantidade de comentários, descrição e entre outros. Essas informações são processadas, categorizadas e salvas em banco.

### Objetivos ###
Este projeto tem como objetivo:
- **Gerar previsões sobre futuras postagens baseando nas informações obtidas por postagens anteriores.
- **Apresentar sugestões de inclusão/remoção de informação para uma postagem em construção.

### Tecnologias utilizadas ###
- **Api
  - Java
  - Jersey
  - JPA-Hibernate
  - MongoDB/PostgreSQL

- **Interface
  - JQuery e HTML
