#!/bin/bash

# Função para imprimir resultados
print_result() {
    echo -e "\n$1"
    echo "Status: $2"
    echo "HTTP Status: $3"
    echo "Response: $4"
    echo "----------------------------------------"
}

# Teste de registro
echo "Testando registro de usuário..."
REGISTER_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST http://localhost:8080/api/users/register \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "password": "password123",
        "firstName": "Test",
        "lastName": "User",
        "email": "test@example.com",
        "securityId": "123456789",
        "phoneNumber": "1234567890"
    }')
REGISTER_HTTP_STATUS=$(echo "$REGISTER_RESPONSE" | tail -n1)
REGISTER_BODY=$(echo "$REGISTER_RESPONSE" | sed '$d')
REGISTER_STATUS=$?
print_result "Registro" "$REGISTER_STATUS" "$REGISTER_HTTP_STATUS" "$REGISTER_BODY"

# Extrair o ID do usuário da resposta
USER_ID=$(echo $REGISTER_BODY | grep -o '"id":[0-9]*' | cut -d':' -f2)

# Teste de login
echo "Testando login..."
LOGIN_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST http://localhost:8080/api/users/login \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "password": "password123"
    }')
LOGIN_HTTP_STATUS=$(echo "$LOGIN_RESPONSE" | tail -n1)
LOGIN_BODY=$(echo "$LOGIN_RESPONSE" | sed '$d')
LOGIN_STATUS=$?
print_result "Login" "$LOGIN_STATUS" "$LOGIN_HTTP_STATUS" "$LOGIN_BODY"

# Extrair o token da resposta
TOKEN=$(echo $LOGIN_BODY | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

# Teste de busca por ID
echo "Testando busca por ID..."
GET_USER_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET http://localhost:8080/api/users/3 \
    -H "Authorization: Bearer $TOKEN")
GET_USER_HTTP_STATUS=$(echo "$GET_USER_RESPONSE" | tail -n1)
GET_USER_BODY=$(echo "$GET_USER_RESPONSE" | sed '$d')
GET_USER_STATUS=$?
print_result "Busca por ID" "$GET_USER_STATUS" "$GET_USER_HTTP_STATUS" "$GET_USER_BODY"

# Teste de registro de animal perdido
echo "Testando registro de animal perdido..."
LOST_ANIMAL_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST http://localhost:8080/api/animals/lost \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d '{
        "name": "Rex",
        "location": "São Paulo, SP",
        "date": "2024-04-16T10:00:00",
        "characteristics": {
            "species": "Cachorro",
            "breed": "Labrador",
            "color": "Preto",
            "size": "Grande"
        }
    }')
LOST_ANIMAL_HTTP_STATUS=$(echo "$LOST_ANIMAL_RESPONSE" | tail -n1)
LOST_ANIMAL_BODY=$(echo "$LOST_ANIMAL_RESPONSE" | sed '$d')
LOST_ANIMAL_STATUS=$?
print_result "Registro de Animal Perdido" "$LOST_ANIMAL_STATUS" "$LOST_ANIMAL_HTTP_STATUS" "$LOST_ANIMAL_BODY"

# Teste de registro de animal encontrado
echo "Testando registro de animal encontrado..."
FOUND_ANIMAL_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST http://localhost:8080/api/animals/found \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d '{
        "location": "São Paulo, SP",
        "date": "2024-04-16T10:00:00",
        "characteristics": {
            "species": "Gato",
            "breed": "Siamês",
            "color": "Branco",
            "size": "Médio"
        }
    }')
FOUND_ANIMAL_HTTP_STATUS=$(echo "$FOUND_ANIMAL_RESPONSE" | tail -n1)
FOUND_ANIMAL_BODY=$(echo "$FOUND_ANIMAL_RESPONSE" | sed '$d')
FOUND_ANIMAL_STATUS=$?
print_result "Registro de Animal Encontrado" "$FOUND_ANIMAL_STATUS" "$FOUND_ANIMAL_HTTP_STATUS" "$FOUND_ANIMAL_BODY"

# Teste de listagem de animais perdidos
echo "Testando listagem de animais perdidos..."
LIST_LOST_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET http://localhost:8080/api/animals/lost \
    -H "Authorization: Bearer $TOKEN")
LIST_LOST_HTTP_STATUS=$(echo "$LIST_LOST_RESPONSE" | tail -n1)
LIST_LOST_BODY=$(echo "$LIST_LOST_RESPONSE" | sed '$d')
LIST_LOST_STATUS=$?
print_result "Listagem de Animais Perdidos" "$LIST_LOST_STATUS" "$LIST_LOST_HTTP_STATUS" "$LIST_LOST_BODY"

# Teste de listagem de animais encontrados
echo "Testando listagem de animais encontrados..."
LIST_FOUND_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET http://localhost:8080/api/animals/found \
    -H "Authorization: Bearer $TOKEN")
LIST_FOUND_HTTP_STATUS=$(echo "$LIST_FOUND_RESPONSE" | tail -n1)
LIST_FOUND_BODY=$(echo "$LIST_FOUND_RESPONSE" | sed '$d')
LIST_FOUND_STATUS=$?
print_result "Listagem de Animais Encontrados" "$LIST_FOUND_STATUS" "$LIST_FOUND_HTTP_STATUS" "$LIST_FOUND_BODY" 

# Teste de retorno de animal por id
echo "Testando retorno de animal por id..."
FOUND_ANIMAL_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET http://localhost:8080/api/animals/found/2 \
    -H "Authorization: Bearer $TOKEN")
FOUND_ANIMAL_HTTP_STATUS=$(echo "$FOUND_ANIMAL_RESPONSE" | tail -n1)
FOUND_ANIMAL_BODY=$(echo "$FOUND_ANIMAL_RESPONSE" | sed '$d')
FOUND_ANIMAL_STATUS=$?
print_result "Registro de Animal Encontrado" "$FOUND_ANIMAL_STATUS" "$FOUND_ANIMAL_HTTP_STATUS" "$FOUND_ANIMAL_BODY"

# Teste de endpoint autenticado /api/test
echo "Testando endpoint autenticado /api/test..."
TEST_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET http://localhost:8080/api/test \
    -H "Authorization: Bearer $TOKEN")
TEST_HTTP_STATUS=$(echo "$TEST_RESPONSE" | tail -n1)
TEST_BODY=$(echo "$TEST_RESPONSE" | sed '$d')
TEST_STATUS=$?
print_result "Endpoint /api/test" "$TEST_STATUS" "$TEST_HTTP_STATUS" "$TEST_BODY"