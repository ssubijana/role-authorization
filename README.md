# Role Authorization
Spring Boot application with JWT & Spring Security for authentication. API authorization with user roles

## API endpoints
- **/login**: user authentication. Response returns authorization JWT in header.
- **/users/{id}**: gets the user info. Only admin users can access to users info. 