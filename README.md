# ExpenseFlow API

## Sobre o projeto

O **ExpenseFlow** é uma API REST desenvolvida em **Java com Spring Boot**, pensada para simular um sistema empresarial de **controle de reembolso de despesas com fluxo de aprovação**.

A ideia do projeto é ir além de um CRUD simples. O objetivo é construir uma aplicação com contexto corporativo real, envolvendo cadastro de usuários, empresas, centros de resultado, categorias de despesa, relatórios de despesas, itens de despesa e um fluxo de aprovação estruturado.

Este projeto será desenvolvido de forma incremental, começando pela base da aplicação e evoluindo para autenticação, autorização, regras de negócio, testes automatizados, Docker, CI/CD e deploy em ambiente cloud.

---

## Objetivo

O principal objetivo do ExpenseFlow é servir como um projeto de estudo e portfólio, demonstrando domínio em desenvolvimento backend com Java e Spring Boot.

O sistema busca representar um cenário comum em empresas: colaboradores realizam despesas, registram essas despesas em um relatório e submetem esse relatório para aprovação de gestores, financeiro ou diretoria.

Com isso, o projeto permite praticar conceitos importantes como:

- Arquitetura em camadas.
- Organização por domínio.
- Modelagem relacional.
- Regras de negócio.
- Validação de dados.
- Integração com API externa.
- Autenticação e autorização.
- Testes automatizados.
- Versionamento de banco de dados.
- Dockerização.
- Pipeline CI/CD.
- Deploy em cloud.

---

## Contexto do sistema

Em um ambiente empresarial, é comum que colaboradores realizem gastos relacionados ao trabalho, como alimentação, hospedagem, combustível, pedágio, passagens, estacionamento ou materiais.

Esses gastos precisam ser registrados, categorizados e aprovados antes de serem reembolsados.

O ExpenseFlow simula esse processo.

Exemplo de uso:

> Um colaborador realiza uma viagem comercial e tem gastos com combustível, hotel e alimentação. Ele cria um relatório de despesas, adiciona cada item gasto, informa a empresa e o centro de resultado responsável, e envia o relatório para aprovação. O sistema identifica o fluxo correto e direciona a solicitação para os aprovadores responsáveis.

---

## Escopo principal

O sistema será composto pelos seguintes módulos principais:

- Usuários.
- Autenticação.
- Endereços.
- Empresas.
- Times.
- Centros de resultado.
- Categorias de despesa.
- Relatórios de despesa.
- Itens de despesa.
- Fluxos de aprovação.
- Etapas de aprovação.
- Decisões de aprovação.

---

## Domínios planejados

### Usuários

Responsável pelo cadastro e gerenciamento dos usuários do sistema.

Os usuários poderão ter diferentes perfis de acesso, como administrador, solicitante, gestor, financeiro e diretoria.

Perfis planejados:

- `ADMIN`
- `REQUESTER`
- `MANAGER`
- `FINANCE`
- `DIRECTOR`

### Empresas

Representam as empresas ou unidades do grupo empresarial.

Cada usuário, centro de resultado, time e relatório de despesa poderá estar vinculado a uma empresa.

### Times

Representam grupos internos, departamentos ou equipes.

Um usuário poderá pertencer a um time, e um time poderá ter um gestor responsável.

### Centros de resultado

Representam áreas, departamentos, unidades de negócio ou centros de custo utilizados para classificar despesas.

Exemplos:

- Administrativo.
- Comercial.
- Financeiro.
- Tecnologia.
- Produção.

### Categorias de despesa

Representam os tipos de gastos que podem ser cadastrados nos relatórios.

Exemplos:

- Alimentação.
- Hospedagem.
- Combustível.
- Pedágio.
- Passagem.
- Estacionamento.
- Material de escritório.
- Outros.

### Relatórios de despesa

O relatório de despesa é a entidade principal do sistema.

Ele representa uma solicitação de reembolso feita por um usuário.

Um relatório poderá conter:

- Título.
- Descrição.
- Solicitante.
- Empresa.
- Centro de resultado.
- Status.
- Valor total.
- Itens de despesa.
- Datas de criação, envio, aprovação, rejeição, cancelamento ou pagamento.

Status planejados:

- `DRAFT`
- `SUBMITTED`
- `PENDING_APPROVAL`
- `APPROVED`
- `REJECTED`
- `CANCELED`
- `PAID`

### Itens de despesa

Cada relatório poderá conter vários itens de despesa.

Cada item representa um gasto individual.

Exemplo:

- Almoço com cliente.
- Hospedagem.
- Combustível.
- Pedágio.
- Estacionamento.

Cada item terá uma categoria, descrição, valor, data da despesa e, futuramente, poderá ter um comprovante anexado.

### Fluxo de aprovação

O fluxo de aprovação define quais etapas um relatório deverá seguir antes de ser aprovado.

O fluxo poderá variar conforme:

- Empresa.
- Centro de resultado.
- Valor total do relatório.
- Perfil dos aprovadores.

Exemplo:

Relatórios até R$ 1.000,00:

1. Gestor.
2. Financeiro.

Relatórios acima de R$ 5.000,00:

1. Gestor.
2. Financeiro.
3. Diretoria.

### Decisões de aprovação

Cada aprovação ou reprovação será registrada.

O sistema deverá armazenar:

- Relatório aprovado/reprovado.
- Etapa do fluxo.
- Usuário aprovador.
- Decisão.
- Comentário.
- Data da decisão.

---

## Arquitetura planejada

O projeto seguirá uma arquitetura em camadas, organizada por domínio.

A ideia é manter o projeto simples o suficiente para evoluir com clareza, mas organizado o bastante para representar uma aplicação backend profissional.

Estrutura conceitual:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Além disso, o projeto terá separação para DTOs, mappers, exceptions, configurações, clients externos e segurança.

---

## Organização por domínio

A estrutura será organizada por contexto de negócio, e não apenas por tipo de classe.

Exemplo:

```text
user
├── controller
├── service
├── repository
├── mapper
├── dto
├── model
└── enums
```

Essa abordagem facilita a manutenção e deixa cada módulo mais independente.

Domínios planejados:

```text
auth
user
address
company
team
resultcenter
expensecategory
expensereport
expenseitem
approval
common
config
```

---

## Stack planejada

Tecnologias previstas para o desenvolvimento:

- Java.
- Spring Boot.
- Spring Web.
- Spring Data JPA.
- Spring Validation.
- Spring Security.
- JWT.
- PostgreSQL.
- Flyway.
- Lombok.
- Swagger/OpenAPI.
- JUnit.
- Mockito.
- MockMvc.
- Testcontainers.
- Docker.
- Docker Compose.
- GitHub Actions.
- Docker Hub ou AWS.

---

## Integração externa

O sistema terá uma integração externa para consulta de CEP.

Essa integração será usada para preencher dados de endereço automaticamente durante o cadastro de usuários ou empresas.

A camada responsável por essa comunicação será separada da regra de negócio principal, mantendo o código mais organizado e fácil de testar.

---

## Testes

O projeto será desenvolvido com foco em testes automatizados.

Tipos de testes planejados:

- Testes unitários de service.
- Testes de controller.
- Testes de integração.
- Testes com banco real usando Testcontainers.

Exemplos de cenários que deverão ser testados:

- Cadastro de usuário.
- Validação de email duplicado.
- Criação de relatório de despesa.
- Cálculo do valor total.
- Envio de relatório para aprovação.
- Aprovação de uma etapa.
- Reprovação de um relatório.
- Validação de permissões.

---

## Segurança

A autenticação será feita com JWT usando Spring Security.

O sistema deverá proteger rotas de acordo com o perfil do usuário.

Exemplo de permissões planejadas:

- `ADMIN`: gerencia cadastros e configurações.
- `REQUESTER`: cria relatórios de despesa.
- `MANAGER`: aprova relatórios do time ou centro.
- `FINANCE`: valida relatórios aprovados e registra pagamento.
- `DIRECTOR`: aprova despesas de alto valor.

---

## Banco de dados

O banco principal será PostgreSQL.

As alterações estruturais do banco serão versionadas com Flyway.

Principais tabelas planejadas:

- `users`
- `addresses`
- `companies`
- `teams`
- `result_centers`
- `expense_categories`
- `expense_reports`
- `expense_items`
- `approval_flows`
- `approval_steps`
- `approval_decisions`

---

## Fluxo principal do sistema

Fluxo esperado para uma solicitação de reembolso:

1. Usuário cria um relatório de despesa.
2. Sistema cria o relatório com status `DRAFT`.
3. Usuário adiciona itens de despesa.
4. Sistema calcula o valor total.
5. Usuário envia o relatório para aprovação.
6. Sistema identifica o fluxo de aprovação aplicável.
7. Relatório fica com status `PENDING_APPROVAL`.
8. Aprovador responsável aprova ou rejeita.
9. Se aprovado e houver próxima etapa, o relatório segue no fluxo.
10. Se aprovado na última etapa, o relatório muda para `APPROVED`.
11. Se rejeitado, o relatório muda para `REJECTED`.
12. Financeiro poderá marcar o relatório como `PAID`.

---

## Roadmap de desenvolvimento

### Fase 1 — Estrutura inicial

- Criar projeto Spring Boot.
- Definir package base.
- Criar estrutura de pacotes.
- Configurar PostgreSQL.
- Configurar Flyway.
- Configurar Swagger.
- Criar tratamento global de exceções.

### Fase 2 — Usuários

- Criar entidade de usuário.
- Criar DTOs.
- Criar mapper.
- Criar repository.
- Criar service.
- Criar controller.
- Criar testes do módulo de usuários.

### Fase 3 — Endereço e CEP

- Criar entidade de endereço.
- Criar client para consulta externa de CEP.
- Integrar endereço ao cadastro de usuário.

### Fase 4 — Autenticação

- Implementar login.
- Gerar JWT.
- Configurar Spring Security.
- Proteger rotas.
- Aplicar autorização por roles.

### Fase 5 — Cadastros base

- Empresas.
- Times.
- Centros de resultado.
- Categorias de despesa.

### Fase 6 — Despesas

- Relatórios de despesa.
- Itens de despesa.
- Cálculo automático do total.
- Controle de status.

### Fase 7 — Aprovação

- Fluxos de aprovação.
- Etapas de aprovação.
- Aprovação de relatórios.
- Reprovação de relatórios.
- Consulta de pendências.

### Fase 8 — Qualidade e entrega

- Testes de integração.
- Dockerfile.
- Docker Compose.
- GitHub Actions.
- Publicação da imagem.
- Deploy em ambiente cloud.

---

## Intuito do projeto

O ExpenseFlow foi pensado para consolidar conhecimentos em backend Java, arquitetura de APIs e desenvolvimento de sistemas corporativos.

Mais do que implementar endpoints, o objetivo é praticar a construção de uma aplicação com regras reais de negócio, relacionamentos entre entidades, controle de permissões, integração externa e fluxo de aprovação.

Este projeto será evoluído aos poucos, mantendo o código organizado e documentado durante todo o desenvolvimento.

---

## Status

Projeto em fase inicial de planejamento e modelagem.

Primeiro foco:

- Definir arquitetura.
- Modelar banco de dados.
- Criar estrutura inicial.
- Implementar CRUD de usuários com testes.
