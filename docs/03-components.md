# Komponenten in der Web Application

Die Web Application besteht aus mehreren Spring-Komponenten, die jeweils klar getrennte Aufgaben erfüllen.

### Login-Komponenten

- **Login Controller**
  - Technologie: Spring MVC
  - Nimmt Login-Anfragen entgegen
  - Interagiert mit dem User Login Service

- **User Login Service**
  - Technologie: Java Service
  - Führt Authentifizierung durch
  - Fragt Daten über das Repository ab

- **User Repository**
  - Technologie: Spring Data JPA
  - Zugriff auf Benutzerdaten
  - Interagiert mit der Datenbank

### Registrierungskomponenten

- **Registration Controller**
  - Technologie: Spring MVC
  - Nimmt Registrierungsanfragen entgegen

- **Registration Service**
  - Technologie: Java Service
  - Verarbeitet die Registrierung und speichert Daten in der Datenbank

### Datenbank-Komponente

- **Database**
  - Technologie: PostgreSQL
  - Speichert alle Benutzerdaten
