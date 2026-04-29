# 🛒 Spring Boot E-commerce API

API REST de e-commerce construída com Spring Boot, implementando autenticação JWT, controle de acesso por roles, CRUD de produtos, carrinho de compras e fluxo de checkout com geração de pedidos.

---

## 🚀 Sobre o projeto

Este projeto simula a base de um sistema de e-commerce real, incluindo:

- Autenticação stateless com JWT
- Controle de acesso com roles (ADMIN e USER)
- CRUD de produtos
- Gerenciamento de carrinho vinculado ao usuário autenticado
- Estrutura em camadas seguindo boas práticas de arquitetura

---
## 🔐 Funcionalidades

- Cadastro e login de usuários
- Autenticação com JWT
- Controle de acesso por roles (`USER` e `ADMIN`)
- CRUD de produtos
- Carrinho de compras vinculado ao usuário autenticado
- Checkout do carrinho
- Criação de pedidos com múltiplos itens
- Cálculo automático do total do pedido
- Atualização de estoque após checkout
- Limpeza automática do carrinho após finalização do pedido

---

## 🧠 Conceitos aplicados

- Autenticação e autorização com Spring Security
- JWT (JSON Web Token)
- Filtro customizado (`OncePerRequestFilter`)
- DTOs para controle de entrada e saída de dados
- Arquitetura em camadas:
  - Controller
  - Service
  - Repository
- JPA/Hibernate para persistência
- Relacionamentos entre entidades (OneToMany, ManyToOne)
- Validação com Bean Validation (`@Valid`)

---

## 🛠️ Tecnologias utilizadas

- Java
- Spring Boot
- Spring Security
- JPA / Hibernate
- PostgreSQL
- Maven
- JWT (io.jsonwebtoken)

---

## 🔐 Autenticação e autorização

A autenticação é feita via JWT:

1. O usuário realiza login em `/auth/login`
2. Recebe um token JWT
3. Envia o token nas requisições protegidas:


Authorization: Bearer SEU_TOKEN


---

## 👤 Roles

- `USER`
  - Pode visualizar produtos
  - Pode gerenciar seu próprio carrinho

- `ADMIN`
  - Pode criar, atualizar e deletar produtos

---

## 📦 Endpoints

### 🔑 Auth

- `POST /auth/register` → cria usuário
- `POST /auth/login` → autentica e retorna JWT

---

### 👤 Users

- `POST /users` → cria usuário (público)

---

### 📦 Products

- `GET /products` → lista produtos (autenticado)
- `GET /products/{id}` → busca produto por ID

- `POST /products` → cria produto (**ADMIN**)
- `PUT /products/{id}` → atualiza produto (**ADMIN**)
- `DELETE /products/{id}` → remove produto (**ADMIN**)

---

### 🛒 Cart

- `POST /cart` → adiciona item ao carrinho
- `GET /cart` → lista itens do carrinho do usuário logado
- `DELETE /cart/{id}` → remove item do carrinho

---

### 🧾 Orders

- `POST /orders/checkout` → finaliza o carrinho e cria um pedido
- `GET /orders` → lista os pedidos do usuário autenticado

---

## 🧪 Como testar

### 1. Criar usuário


POST /auth/register


### 2. Fazer login


POST /auth/login


Copie o token retornado.

---

### 3. Usar o token

Adicione no header das requisições:


Authorization: Bearer SEU_TOKEN


---

### 4. Criar produto (ADMIN)


POST /products


---

### 5. Adicionar ao carrinho

POST /cart

Body:


```
{
 "productId": 1,
 "quantity": 2
}
```
---

### 6. Finalizar pedido

POST /orders/checkout

---
### 7. Listar pedidos

GET /orders

---

## 🗄️ Banco de dados

Configurado para PostgreSQL.

Exemplo de configuração:

spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=SEU_USER
spring.datasource.password=SUA_SENHA
⚙️ Variáveis de ambiente
JWT_SECRET=seu_segredo_base64
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

---

## 🧱 Estrutura do projeto
controller/   → camada HTTP
service/      → regras de negócio
repository/   → acesso ao banco
model/        → entidades JPA
dto/          → objetos de entrada/saída
config/       → segurança (JWT, filtros)

---

## 📌 Autor

David Bonardi
🔗 https://github.com/David-Bonardi
