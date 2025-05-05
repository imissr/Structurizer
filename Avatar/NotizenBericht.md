# Open Services Gateway initiative

It allows you to develop and deploy modular Java applications where components (called bundles) can be installed, started, stopped, updated, and uninstalled dynamically at runtime without restarting the whole system


## Avatar Project Structure and Components
The Avatar project is a comprehensive OSGi-based data integration platform focused on implementing connector services for data spaces. Here's a detailed breakdown of the project:

## Overall Architecture
The project is built on the OSGi (Open Services Gateway initiative) framework, which allows for developing modular Java applications with components that can be dynamically managed at runtime. The Avatar system specifically provides a framework for connecting to various data services using a standardized connector API.

## Core Components

1. Connector API (de.avatar.connector.api)
This module defines the core interfaces that all connectors must implement:

    AvatarConnectorInfo: Base interface that provides methods to:

    Get connector endpoints (getEndpoints())
    Get connector information (getInfo())
    AvatarConnector: Main interface that extends AvatarConnectorInfo and adds methods for:

    dryRequest(): Simulates a request without executing it (provides estimation)
    executeRequest(): Actually executes the request and returns results


2. Connector Model (de.avatar.connector.model) This module contains EMF-based data models used throughout the system:

    ConnectorInfo: Contains metadata about a connector (id, name, version)
    ConnectorEndpoint: Represents a communication endpoint with properties like id, name, URI, protocol
    EndpointRequest: A request object containing parameters and endpoint details
    EndpointResponse: Response containing results
    Various result types:
    DryRunResult: For simulation results
    ErrorResult: For error handling
    EcoreResult: For EMF-based data
    JavaResult: For Java objects
    SimpleResult: For simple return values
    The model provides helper utilities like ConnectorHelper for creating responses.

3. Connector Implementations
    The system includes multiple connector implementations:

    ### ISMA Connector (de.avatar.connector.isma)
    
    Implements AvatarConnector interface for ISMA HIMSA protocol
    Provides endpoints for MQTT (mqtt://isma/himsa) and REST (http://isma/himsa/rest)
    Registered as an OSGi component with remote service properties

    ### Other/HL7 Connector (de.avatar.connector.other)

    Implements AvatarConnector for HL7 healthcare standard
    Provides MQTT and REST endpoints
    Similar structure to the ISMA connector but for different protocol
    4. Connector Whiteboard (de.avatar.connector.whiteboard)
    Implements the whiteboard pattern for tracking connector services
    Acts as a central registry for available connectors
    Can be used to discover and utilize connectors
    5. EMF Serialization (de.avatar.connector.emf)
    EcoreSerializer: Handles serialization of requests and responses for remote service calls
    Supports serializing both Java objects and EMF objects
    Used for cross-bundle and cross-JVM communication
    6. Testing Components (de.avatar.connector.api.tck)
    Technology Compatibility Kit for testing connector implementations
    MockedRequestTest: Tests connector behavior with mocked requests
    ConnectorInfoTest: Tests connector info functionality
    Uses JUnit 5 with extensions for OSGi testing

    ### How Components Interact
    #### Request/Response Flow:

    A client creates an EndpointRequest object with parameters
    The client can perform a dry run to estimate execution metrics
    For actual execution, the client calls executeRequest() on the connector
    The connector processes the request and returns an EndpointResponse
    #### Service Discovery:

    Connector implementations are registered as OSGi services
    They can be discovered through the whiteboard or standard OSGi service lookup
    Each connector provides information about its capabilities via getInfo()
    #### Remote Communication:

    Connectors are exported as remote services using Paremus DOSGi
    The EMF serialization enables cross-JVM communication
    Properties like com.paremus.dosgi.target.clusters=DIMC define targeting

    ### Deployment
    The system can be deployed as OSGi bundles in an OSGi container, or using Docker as shown in the docker-compose.yml file:

    Use Cases
    The Avatar project appears designed for data integration scenarios where:

    Multiple data sources need to be accessed through a consistent API
    Requests might need simulation before actual execution
    The system needs to be modular and dynamically configurable
    Data connections might span multiple protocols (MQTT, REST, etc.)
    Healthcare-related standards (HL7, HIMSA) need to be supported
    The project demonstrates good OSGi practices with modular design, clear service interfaces, and comprehensive testing frameworks.


# C4 Model Structure for Avatar

## Level 1: System Context

**People:**
- Data Consumers
- Data Providers

**Systems:**
- Avatar System
- External Data Sources (HIMSA, HL7)

---

## Level 2: Container

- **OSGi Runtime** – Core container hosting all bundles  
- **Docker Container** – Optional deployment container  
- **Remote Services** – Exported service interfaces

---

## Level 3: Component

### Core API Components:
- **Connector API** (`de.avatar.connector.api`)
  - `AvatarConnectorInfo` interface
  - `AvatarConnector` interface

### Model Components:
- **Connector Model** (`de.avatar.connector.model`)
  - `ConnectorInfo`, `ConnectorEndpoint`
  - `EndpointRequest` and `EndpointResponse`
  - Various result types (`DryRunResult`, `EcoreResult`, etc.)

### Implementation Components:
- **ISMA Connector** (`de.avatar.connector.isma`)
- **HL7 Connector** (`de.avatar.connector.other`)

### Infrastructure Components:
- **Connector Whiteboard** (`de.avatar.connector.whiteboard`)
- **EMF Serialization** (`de.avatar.connector.emf`)

### Testing Components:
- **TCK** (`de.avatar.connector.api.tck`)
  - `MockedRequestTest`
  - `ConnectorInfoTest`

---

## Flow Representations to Add

- Request/Response flow  
- Service Discovery process  
- Remote Communication patterns  

---

This approach gives you a comprehensive **C4 model** that captures the modular **OSGi architecture** of the Avatar system while highlighting its key **functional components** and **relationships**.