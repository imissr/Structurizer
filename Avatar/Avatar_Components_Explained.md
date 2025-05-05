# Avatar Project Components Explained

This document provides a detailed explanation of each component in the Avatar project architecture.

## Core API Components

### Connector API (`de.avatar.connector.api`)
- **Purpose**: Defines the core contract that all connectors must implement
- **Key Interfaces**:
  - `AvatarConnectorInfo`: Base interface providing metadata about connectors
    - `getEndpoints()`: Returns a list of available endpoints the connector provides
    - `getInfo()`: Returns metadata about the connector (ID, name, version)
  - `AvatarConnector`: Main service interface extending `AvatarConnectorInfo`
    - `dryRequest()`: Simulates execution without actually performing the operation, providing execution estimates
    - `executeRequest()`: Performs the actual data request and returns results
- **Importance**: Forms the foundation of the entire system, enabling a standardized way to connect to different data sources

### Connector Model (`de.avatar.connector.model`)
- **Purpose**: Provides EMF-based data models for representing connector data structures
- **Key Models**:
  - `ConnectorInfo`: Contains metadata about a connector (ID, name, version)
  - `ConnectorEndpoint`: Represents a communication endpoint with properties like ID, name, URI, protocol
  - `EndpointRequest`: Encapsulates parameters and endpoint details for a request
  - `EndpointResponse`: Contains the results of executing a request
  - Result Types:
    - `DryRunResult`: Contains execution estimates without actual execution
    - `ErrorResult`: Represents error conditions during request execution
    - `EcoreResult`: Contains EMF-based data responses
    - `JavaResult`: Contains Java object responses
    - `SimpleResult`: Contains simple return values
- **Helper Classes**: 
  - `ConnectorHelper`: Provides utility methods for creating responses
- **Importance**: Standardizes data exchange formats across the system

## Implementation Components

### ISMA Connector (`de.avatar.connector.isma`)
- **Purpose**: Implements the `AvatarConnector` interface for the ISMA HIMSA protocol
- **Endpoints Provided**:
  - MQTT Endpoint: `mqtt://isma/himsa` for message-based communication
  - REST Endpoint: `http://isma/himsa/rest` for HTTP-based communication
- **Registration**: Registered as an OSGi component with remote service properties
- **Importance**: Provides connectivity to ISMA-compliant systems in the healthcare/hearing aid domain

### HL7 Connector (`de.avatar.connector.other`)
- **Purpose**: Implements the `AvatarConnector` interface for the HL7 healthcare standard
- **Endpoints Provided**:
  - MQTT Endpoint: For message-based communication
  - REST Endpoint: For HTTP-based communication
- **Structure**: Similar to ISMA connector but adapted for HL7 protocol
- **Importance**: Provides connectivity to healthcare systems using the HL7 standard

## Infrastructure Components

### Connector Whiteboard (`de.avatar.connector.whiteboard`)
- **Purpose**: Implements the OSGi whiteboard pattern for tracking connector services
- **Functions**:
  - Acts as a central registry for available connectors
  - Dynamically tracks the addition and removal of connector services
  - Provides a single point of discovery for available connectors
- **Usage**: Can be used by clients to discover and utilize available connectors without needing to know implementation details
- **Importance**: Enables loose coupling between connector providers and consumers

### EMF Serialization (`de.avatar.connector.emf`)
- **Purpose**: Handles serialization of requests and responses for remote service calls
- **Key Classes**:
  - `EcoreSerializer`: Performs serialization/deserialization of EMF objects
- **Functions**:
  - Supports serializing both Java objects and EMF objects
  - Enables cross-bundle and cross-JVM communication
- **Importance**: Critical for enabling remote service communication in a distributed environment

## Testing Components

### Technology Compatibility Kit (`de.avatar.connector.api.tck`)
- **Purpose**: Provides standardized tests to ensure connector implementations comply with the API
- **Key Test Classes**:
  - `MockedRequestTest`: Tests connector behavior with mocked requests
  - `ConnectorInfoTest`: Tests connector info functionality
- **Testing Environment**: Uses JUnit 5 with extensions for OSGi testing
- **Importance**: Ensures consistent behavior across different connector implementations

## Communication Flows

### Request/Response Flow
1. A client creates an `EndpointRequest` object with parameters
2. The client optionally performs a dry run via `dryRequest()` to estimate execution metrics
3. For actual execution, the client calls `executeRequest()` on the connector
4. The connector processes the request and returns an `EndpointResponse`

### Service Discovery Flow
1. Connector implementations are registered as OSGi services
2. They can be discovered through the whiteboard or standard OSGi service lookup
3. Each connector provides information about its capabilities via `getInfo()`

### Remote Communication Flow
1. Connectors are exported as remote services using Paremus DOSGi
2. The EMF serialization enables cross-JVM communication
3. Properties like `com.paremus.dosgi.target.clusters=DIMC` define targeting for remote services

## Deployment Options

The Avatar system can be deployed in two primary ways:
1. As OSGi bundles in an OSGi container
2. Using Docker as shown in the `docker-compose.yml` file

This modular architecture demonstrates exemplary OSGi practices with clean separation of concerns, dynamic service management, and comprehensive testing frameworks.