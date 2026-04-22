# Especificação Spring Security + JWT (Baseado no PDF)

## 1. Dependências
- [x]Spring Boot Starter Security 
- [x]Spring Data JPA + Driver de Banco
- [x]JJWT (api, impl, jackson) v0.11.5

## 2. Fluxo de Autenticação
- [x]**Stateless**: Sem uso de sessão HTTP.
- [x]**BCrypt**: Senhas obrigatoriamente criptografadas. 
- [x]**JWT**: Validar token no header `Authorization: Bearer`.

## 3. Componentes
- [x]`User`: Entidade com username, password, role.
- [x]`JwtService`: Gerar/Extrair tokens (expiração 24h).
- [x]`JwtFilter`: Interceptar e validar via SecurityContextHolder.
- [x]`SecurityConfig`: Configurar filtros e permissões (/auth/** liberado).
