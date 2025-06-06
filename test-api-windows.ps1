# Função para imprimir resultados
function Print-Result {
    param (
        [string]$TestName,
        [int]$Status,
        [int]$HttpStatus,
        [string]$Response
    )
    Write-Host "`n$TestName"
    Write-Host "Status: $Status"
    Write-Host "HTTP Status: $HttpStatus"
    Write-Host "Response: $Response"
    Write-Host "----------------------------------------"
}

# Teste de registro
Write-Host "Testando registro de usuário..."
try {
    $registerResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/users/register" -Method POST `
        -ContentType "application/json" `
        -Body '{
            "username": "testuser",
            "password": "password123",
            "userData": {
                "firstName": "Test",
                "lastName": "User",
                "email": "test1@example.com",
                "securityId": "123456789",
                "phoneNumber": "1234567891"
            }
        }' -ErrorAction Stop
    $registerHttpStatus = $registerResponse.StatusCode
    $registerBody = $registerResponse.Content
    $registerStatus = 0
}
catch {
    $registerHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $registerBody = $_.Exception.Message
    $registerStatus = 1
}
Print-Result -TestName "Registro" -Status $registerStatus -HttpStatus $registerHttpStatus -Response $registerBody

# Teste de login
Write-Host "Testando login..."
try {
    $loginResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/users/login" -Method POST `
        -ContentType "application/json" `
        -Body '{
            "username": "testuser",
            "password": "password123"
        }' -ErrorAction Stop
    $loginHttpStatus = $loginResponse.StatusCode
    $loginBody = $loginResponse.Content
    $loginStatus = 0
}
catch {
    $loginHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $loginBody = $_.Exception.Message
    $loginStatus = 1
}
Print-Result -TestName "Login" -Status $loginStatus -HttpStatus $loginHttpStatus -Response $loginBody

# Extrair o token
$token = if ($loginBody -match '"token":"([^"]+)"') { $matches[1] } else { $null }

if ($token -eq $null) {
    Write-Host "Erro: Não foi possível obter o token de autenticação. Testes subsequentes serão ignorados."
    exit 1
}

# Teste de registro de animal perdido
Write-Host "Testando registro de animal perdido..."
try {
    $lostAnimalResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/animals/lost" -Method POST `
        -ContentType "application/json" `
        -Headers @{ "Authorization" = "Bearer $token" } `
        -Body '{
            "name": "Rex",
            "location": "São Paulo, SP",
            "date": "2024-04-16T10:00:00",
            "characteristics": {
                "species": "Cachorro",
                "breed": "Labrador",
                "color": "Preto",
                "size": "Grande"
            }
        }' -ErrorAction Stop
    $lostAnimalHttpStatus = $lostAnimalResponse.StatusCode
    $lostAnimalBody = $lostAnimalResponse.Content
    $lostAnimalStatus = 0
}
catch {
    $lostAnimalHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $lostAnimalBody = $_.Exception.Message
    $lostAnimalStatus = 1
}
Print-Result -TestName "Registro de Animal Perdido" -Status $lostAnimalStatus -HttpStatus $lostAnimalHttpStatus -Response $lostAnimalBody

# Teste de registro de animal encontrado
Write-Host "Testando registro de animal encontrado..."
try {
    $foundAnimalResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/animals/found" -Method POST `
        -ContentType "application/json" `
        -Headers @{ "Authorization" = "Bearer $token" } `
        -Body '{
            "name": "Mimi",
            "location": "São Paulo, SP",
            "date": "2024-04-16T10:00:00",
            "characteristics": {
                "species": "Gato",
                "breed": "Siamês",
                "color": "Branco",
                "size": "Médio"
            }
        }' -ErrorAction Stop
    $foundAnimalHttpStatus = $foundAnimalResponse.StatusCode
    $foundAnimalBody = $foundAnimalResponse.Content
    $foundAnimalStatus = 0
}
catch {
    $foundAnimalHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $foundAnimalBody = $_.Exception.Message
    $foundAnimalStatus = 1
}
Print-Result -TestName "Registro de Animal Encontrado" -Status $foundAnimalStatus -HttpStatus $foundAnimalHttpStatus -Response $foundAnimalBody

# Teste de listagem de animais perdidos
Write-Host "Testando listagem de animais perdidos..."
try {
    $listLostResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/animals/lost" -Method GET `
        -Headers @{ "Authorization" = "Bearer $token" } -ErrorAction Stop
    $listLostHttpStatus = $listLostResponse.StatusCode
    $listLostBody = $listLostResponse.Content
    $listLostStatus = 0
}
catch {
    $listLostHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $listLostBody = $_.Exception.Message
    $listLostStatus = 1
}
Print-Result -TestName "Listagem de Animais Perdidos" -Status $listLostStatus -HttpStatus $listLostHttpStatus -Response $listLostBody

# Teste de listagem de animais encontrados
Write-Host "Testando listagem de animais encontrados..."
try {
    $listFoundResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/animals/found" -Method GET `
        -Headers @{ "Authorization" = "Bearer $token" } -ErrorAction Stop
    $listFoundHttpStatus = $listFoundResponse.StatusCode
    $listFoundBody = $listFoundResponse.Content
    $listFoundStatus = 0
}
catch {
    $listFoundHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $listFoundBody = $_.Exception.Message
    $listFoundStatus = 1
}
Print-Result -TestName "Listagem de Animais Encontrados" -Status $listFoundStatus -HttpStatus $listFoundHttpStatus -Response $listFoundBody

# Teste de retorno de animal por ID
Write-Host "Testando retorno de animal por ID..."
try {
    $foundAnimalResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/animals/found/2" -Method GET `
        -Headers @{ "Authorization" = "Bearer $token" } -ErrorAction Stop
    $foundAnimalHttpStatus = $foundAnimalResponse.StatusCode
    $foundAnimalBody = $foundAnimalResponse.Content
    $foundAnimalStatus = 0
}
catch {
    $foundAnimalHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $foundAnimalBody = $_.Exception.Message
    $foundAnimalStatus = 1
}
Print-Result -TestName "Retorno de Animal por ID" -Status $foundAnimalStatus -HttpStatus $foundAnimalHttpStatus -Response $foundAnimalBody

# Teste de endpoint autenticado /api/test
Write-Host "Testando endpoint autenticado /api/test..."
try {
    $testResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/test" -Method GET `
        -Headers @{ "Authorization" = "Bearer $token" } -ErrorAction Stop
    $testHttpStatus = $testResponse.StatusCode
    $testBody = $testResponse.Content
    $testStatus = 0
}
catch {
    $testHttpStatus = if ($_.Exception.Response) { $_.Exception.Response.StatusCode } else { 0 }
    $testBody = $_.Exception.Message
    $testStatus = 1
}
Print-Result -TestName "Endpoint /api/test" -Status $testStatus -HttpStatus $testHttpStatus -Response $testBody