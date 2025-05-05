package org.springframework.samples.petclinic.c4;

import com.structurizr.Workspace;
import com.structurizr.component.ComponentFinder;
import com.structurizr.component.ComponentFinderBuilder;
import com.structurizr.component.ComponentFinderStrategy;
import com.structurizr.component.ComponentFinderStrategyBuilder;
import com.structurizr.component.matcher.NameSuffixTypeMatcher;
import com.structurizr.component.matcher.RegexTypeMatcher;
import com.structurizr.io.json.JsonWriter;
import com.structurizr.model.*;
import com.structurizr.view.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class AvatarC4ModelGenerator {
	public static void main(String[] args) throws Exception {
		// Create workspace
		Workspace workspace = new Workspace("Avatar C4 Model", "Component analysis for Avatar Connector System");
		Model model = workspace.getModel();
		ViewSet views = workspace.getViews();

		// Define users and external systems
		Person clientUser = model.addPerson("Client User", "Uses the Avatar system to access healthcare data");

		// Define the main software system
		SoftwareSystem avatarSystem = model.addSoftwareSystem("Avatar System", "Connector-based system for healthcare data exchange");
		clientUser.uses(avatarSystem, "Makes data requests through");

		// Define containers within the system
		Container connectorApi = avatarSystem.addContainer("Connector API",
			"Defines the core contract for connectors", "Java/OSGi");

		Container connectorModel = avatarSystem.addContainer("Connector Model",
			"EMF-based data models", "Java/EMF");

		Container connectorImplementations = avatarSystem.addContainer("Connector Implementations",
			"Implementations of the API for different protocols", "Java/OSGi");

		Container connectorInfrastructure = avatarSystem.addContainer("Infrastructure",
			"Supporting components and services", "Java/OSGi");

		// Define relationships between containers
		connectorImplementations.uses(connectorApi, "Implements");
		connectorImplementations.uses(connectorModel, "Uses");
		connectorInfrastructure.uses(connectorApi, "Supports");
		connectorInfrastructure.uses(connectorModel, "Uses");
		clientUser.uses(connectorImplementations, "Sends requests to");

		// Define base path to your Avatar project code
		// Set this to your actual project path or a folder that exists
		String basePath = "/home/imissoldgaren/GIT_Project/structurizer/Structurizer/Avatar";

		// Since we may not have the actual source code, let's manually create
		// the components based on the documentation
		createModelComponents(connectorModel);
		createApiComponents(connectorApi);
		createImplComponents(connectorImplementations, connectorApi, connectorModel);
		createInfraComponents(connectorInfrastructure, connectorModel);

		// Optional: Try component finding if code exists
		tryScanningForComponents(connectorModel, basePath);

		// Create container view
		ContainerView containerView = views.createContainerView(avatarSystem, "containers", "Container View");
		containerView.addAllElements();
		containerView.add(clientUser);

		// Create component views for each container
		ComponentView modelComponentView = views.createComponentView(connectorModel,
			"model-components", "Connector Model Components");
		modelComponentView.addAllComponents();

		ComponentView apiComponentView = views.createComponentView(connectorApi,
			"api-components", "Connector API Components");
		apiComponentView.addAllComponents();

		ComponentView implComponentView = views.createComponentView(connectorImplementations,
			"implementation-components", "Connector Implementation Components");
		implComponentView.addAllComponents();

		ComponentView infraComponentView = views.createComponentView(connectorInfrastructure,
			"infrastructure-components", "Infrastructure Components");
		infraComponentView.addAllComponents();

		// Add styles
		Styles styles = views.getConfiguration().getStyles();
		styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
		styles.addElementStyle(Tags.CONTAINER).background("#438dd5").color("#ffffff");

		styles.addElementStyle("Info").background("#85bb65").color("#ffffff");
		styles.addElementStyle("Request").background("#7560ba").color("#ffffff");
		styles.addElementStyle("Response").background("#e6bd56").color("#000000");
		styles.addElementStyle("Result").background("#b86950").color("#ffffff");
		styles.addElementStyle("Endpoint").background("#60aa9f").color("#ffffff");
		styles.addElementStyle("API").background("#4b79cc").color("#ffffff");
		styles.addElementStyle("Implementation").background("#f5da55").color("#000000");
		styles.addElementStyle("Infrastructure").background("#b86950").color("#ffffff");

		// Export to JSON
		try (Writer writer = new FileWriter("avatar-c4-model.json")) {
			new JsonWriter(true).write(workspace, writer);
			System.out.println("Avatar C4 model exported to avatar-c4-model.json");
		}
	}

	// Method to manually create model components based on documentation
	private static void createModelComponents(Container container) {
		Component connectorInfo = container.addComponent("ConnectorInfo",
			"Contains metadata about a connector (ID, name, version)", "EMF Model");
		connectorInfo.addTags("Info");

		Component connectorEndpoint = container.addComponent("ConnectorEndpoint",
			"Represents a communication endpoint with properties like ID, name, URI, protocol", "EMF Model");
		connectorEndpoint.addTags("Endpoint");

		Component endpointRequest = container.addComponent("EndpointRequest",
			"Encapsulates parameters and endpoint details for a request", "EMF Model");
		endpointRequest.addTags("Request");

		Component endpointResponse = container.addComponent("EndpointResponse",
			"Contains the results of executing a request", "EMF Model");
		endpointResponse.addTags("Response");

		Component dryRunResult = container.addComponent("DryRunResult",
			"Contains execution estimates without actual execution", "EMF Model");
		dryRunResult.addTags("Result");

		Component errorResult = container.addComponent("ErrorResult",
			"Represents error conditions during request execution", "EMF Model");
		errorResult.addTags("Result");

		Component ecoreResult = container.addComponent("EcoreResult",
			"Contains EMF-based data responses", "EMF Model");
		ecoreResult.addTags("Result");

		Component javaResult = container.addComponent("JavaResult",
			"Contains Java object responses", "EMF Model");
		javaResult.addTags("Result");

		Component simpleResult = container.addComponent("SimpleResult",
			"Contains simple return values", "EMF Model");
		simpleResult.addTags("Result");

		Component connectorHelper = container.addComponent("ConnectorHelper",
			"Provides utility methods for creating responses", "EMF Model");
	}

	// Method to manually create API components based on documentation
	private static void createApiComponents(Container container) {
		Component avatarConnectorInfo = container.addComponent("AvatarConnectorInfo",
			"Base interface providing metadata about connectors", "Java Interface");
		avatarConnectorInfo.addTags("API");

		Component avatarConnector = container.addComponent("AvatarConnector",
			"Main service interface for connector implementations", "Java Interface");
		avatarConnector.addTags("API");

		avatarConnector.uses(avatarConnectorInfo, "extends");
	}

	// Method to manually create implementation components
	private static void createImplComponents(Container container, Container api, Container model) {
		Component ismaConnector = container.addComponent("ISMAConnector",
			"Implementation for the ISMA HIMSA protocol", "Java/OSGi");
		ismaConnector.addTags("Implementation");

		Component hl7Connector = container.addComponent("HL7Connector",
			"Implementation for the HL7 healthcare standard", "Java/OSGi");
		hl7Connector.addTags("Implementation");

		ismaConnector.uses(api.getComponentWithName("AvatarConnector"), "implements");
		hl7Connector.uses(api.getComponentWithName("AvatarConnector"), "implements");

		ismaConnector.uses(model.getComponentWithName("EndpointRequest"), "processes");
		ismaConnector.uses(model.getComponentWithName("EndpointResponse"), "creates");
		hl7Connector.uses(model.getComponentWithName("EndpointRequest"), "processes");
		hl7Connector.uses(model.getComponentWithName("EndpointResponse"), "creates");
	}

	// Method to manually create infrastructure components
	private static void createInfraComponents(Container container, Container model) {
		Component whiteboard = container.addComponent("ConnectorWhiteboard",
			"Implements the OSGi whiteboard pattern for tracking connector services", "Java/OSGi");
		whiteboard.addTags("Infrastructure");

		Component serializer = container.addComponent("EcoreSerializer",
			"Performs serialization/deserialization of EMF objects", "Java/OSGi");
		serializer.addTags("Infrastructure");

		serializer.uses(model.getComponentWithName("EcoreResult"), "serializes/deserializes");
	}

	// Attempt to find components using ComponentFinder but fail gracefully if it doesn't work
	private static void tryScanningForComponents(Container container, String basePath) {
		File path = new File(basePath);
		if (!path.exists()) {
			System.out.println("Warning: Path " + basePath + " doesn't exist. Skipping component scanning.");
			return;
		}

		try {
			System.out.println("Attempting to scan for components in: " + basePath);
			List<String> suffixes = List.of("Info", "Request", "Response", "Result", "Endpoint");

			for (String suffix : suffixes) {
				try {
					ComponentFinder finder = new ComponentFinderBuilder()
						.forContainer(container)
						.fromClasses(path)
						.withStrategy(
							new ComponentFinderStrategyBuilder()
								.matchedBy(new NameSuffixTypeMatcher(suffix))
								.withTechnology("EMF Model")
								.forEach(component -> {
									component.addTags(suffix);
									System.out.println("Found component: " + component.getName());
								})
								.build()
						)
						.build();

					finder.run();
					System.out.println("Successfully found components with suffix: " + suffix);
				} catch (Exception e) {
					System.out.println("No components found with suffix: " + suffix + " - " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("Component scanning failed: " + e.getMessage());
		}
	}
}
