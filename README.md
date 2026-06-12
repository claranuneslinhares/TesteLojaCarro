# 🚗 LojaCarro - Implementação de Segurança com JWT


##  Aluno(A)

- Maria Clara Nunes



##  Funcionalidades implementadas

### ✅ Autenticação com JWT

Foi implementado um sistema de autenticação baseado em JSON Web Token.

O usuário realiza o login através do endpoint:

```http
POST /auth/login
```
![img.png](img.png)




## Controle de acesso por papéis

Foram definidos dois papéis de usuários:

- `GERENTE`
- `VENDEDOR`

### Permissões implementadas

| Endpoint | GERENTE | VENDEDOR |
|-----------|----------|-----------|
| GET /carro | ✅ | ✅ |
| POST /carro/salvar | ✅ | ✅ |
| PUT /carro/{id} | ✅ | ✅ |
| DELETE /carro/{id} | ✅ | ❌ |
| Cadastro de usuários | ✅ | ❌ |

---

# Testes realizados

## 1. Validação de Entrada (XSS)

### Entrada utilizada

![img_1.png](img_1.png)

### Resultado

O sistema armazenou a string como texto comum, sem executar o script.

**Conclusão:** não houve execução do código JavaScript no backend.

---

## 2. Acesso Não Autorizado

Foi realizada uma tentativa de acesso aos endpoints protegidos sem enviar o token JWT.

### Exemplo


![img_2.png](img_2.png)



**Conclusão:** o sistema bloqueou o acesso de usuários não autenticados.

---

## 3. Manipulação de Dados

### 3.1 Strings muito grandes e Campos obrigatórios vazios

Entrada utilizada:

```json
{
    "modelo": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
    "ano": 2022
}
```
```json
{
    "modelo": "Corolla",
    "ano": -1
}
```


Resultado:

![img_3.png](img_3.png)



## 4. Verificação contra SQL Injection

Foi realizado o teste utilizando entradas maliciosas.

Exemplo:


![img_4.png](img_4.png)

Resultado:

O sistema tratou o valor como texto simples.

**Conclusão:** a aplicação mostrou-se resistente a SQL Injection, pois utiliza Spring Data JPA, que emprega consultas parametrizadas.

---

## Como executar

### Clonar o projeto

```bash
git clone <https://github.com/claranuneslinhares/TesteLojaCarro.git>
```

### Entrar no projeto

```bash
cd TesteLojaCarro
```

### Executar

```bash
mvn spring-boot:run
```

---

## Exemplos de requisições

### Login

```http
POST /auth/login
```

Body:

```json
{
    "username": "gerente",
    "password": "123456"
}
```

---

### Cadastro de carro

```http
POST /carro/salvar
```

Header:

```text
Authorization: Bearer TOKEN
```

![img_8.png](img_8.png)


## Testando papel de vendedor

- Realizando login e obtendo token
![img_6.png](img_6.png)

- Cadastrando carro como vendedor.
![img_7.png](img_7.png)
- Exclusão de carro como vendedor
![img_9.png](img_9.png)

## Branch utilizada

```text
SEGURANCA
```


