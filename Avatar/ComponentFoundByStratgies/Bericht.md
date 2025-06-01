## We Have Only the Following Strategy

- **Find all types that have been annotated with OSGi `@Component` annotation.**
- **Find all types where the name matches the `.*Whiteboard,result,info.*` regular expression.**
- **Find all types where the name ends with `Whiteboard`, `result`, or `info`.**

> → Implement my own strategy

> We can’t use interfaces (apparently they don’t exist as a strategy in the new component finder).

---

## Using Annotation Strategy

The idea now is to find all Models using `AnnotationStrategy` because every Model has (and will have) `@ProviderType` for future Models → this works.

![C4 Model Structure for Avatar](images/ModelContainer.png)

```java
    // Function to find all components in the model using   OSGi annotations
    // we can also do so we find only the results by using regex matcher
    // problem is that we also get models that we don't need like Rsa factory, etc. that belong to the EMF model
    // good side is that we find every model in that package
    // this strategy works because only the model has the @ProviderType annotation → so based on this every new component should have this annotation
    private static void tryScanningByOSGiFindAllModel(Container container, File path) {
        try {
            // Find components with @Component annotation (OSGi DS annotation)
            ComponentFinder finder = new ComponentFinderBuilder()
                .forContainer(container)
                .fromClasses(path)                       // • path → directory/JAR of compiled classes
                .withStrategy(
                    new ComponentFinderStrategyBuilder()
                        .matchedBy(
                            new AnnotationTypeMatcher(
                                "org.osgi.annotation.versioning.ProviderType"
                            )
                        )
                        .withTechnology("OSGi Component")
                        .forEach(component -> {
                            component.setTechnology("EMF Model");
                            if (component.getName().contains("ConnectorInfo")) {
                                component.addTags("Info");
                            } else if (component.getName().contains("EndpointRequest")) {
                                component.addTags("Request");
                            } else if (component.getName().contains("EndpointResponse")) {
                                component.addTags("Response");
                            } else if (component.getName().contains("DryRunResult") || component.getName().contains("ErrorResult")) {
                                component.addTags("Result");
                            } else if (component.getName().contains("ConnectorEndpoint")) {
                                component.addTags("Endpoint");
                            } else if (component.getName().contains("Package")) {
                                component.addTags("Package");
                            } else if (component.getName().contains("Serializer")) {
                                component.addTags("Infrastructure");
                            } else if (component.getName().contains("Connector")) {
                                component.addTags("Implementation");
                            } else if (component.getName().contains("Parameter")) {
                                component.addTags("Parameter");
                            } else if (component.getName().contains("Result")) {
                                component.addTags("Result");
                            } else if (component.getName().contains("Factory")) {
                                component.addTags("Factory");
                            } else if (component.getName().contains("Metric")) {
                                component.addTags("Metric");
                            } else if (component.getName().contains("Type")) {
                                component.addTags("Type");
                            } else if (component.getName().contains("Helper")) {
                                component.addTags("Helper");
                            } else {
                                System.out.println(
                                    "Component " + component.getName() + " does not match any known tags"
                                );
                            }

                            System.out.println(
                                "Found OSGi component: " + component.getName()
                            );
                        })
                        .build()
                )
                .build();

            finder.run();

            System.out.println("Successfully found OSGi components");
        } catch (Exception e) {
            System.out.println("No OSGi components found → " + e.getMessage());
        }
    }
```

---

## Identify `ConnectorImpl`

```java
// Idea: We can scan the bundle to get connectors. One way to make it automatic is to scan for a bundle and then:
//   • Find the OSGi components by the @Component annotation with the property "connector" set to true.
//   • This way, we can find all connectors in the bundle → don’t know if this is possible.
//   • A workaround is to just scan the bundle (not the whole src folder) and then find the OSGi components by @Component.

private static void tryScanningConnectorByOsgiComponentAnnotation(Container container, File path) {
    try {
        // Find components with @Component annotation (OSGi DS annotation)
        ComponentFinder finder = new ComponentFinderBuilder()
            .forContainer(container)
            .fromClasses(path)
            .withStrategy(
                new ComponentFinderStrategyBuilder()
                    .matchedBy(
                        new AnnotationTypeMatcher(
                            "org.osgi.service.component.annotations.Component"
                        )
                    )
                    .withTechnology("OSGi Component")
                    .forEach(component -> {
                        component.addTags("Implementation");
                        component.setTechnology("Java/OSGi");
                        if (component.getName().contains("ISMA")) {
                            component.setDescription("Implementation for the ISMA HIMSA protocol");
                        } else if (component.getName().contains("HL7")) {
                            component.setDescription("Implementation for the HL7 healthcare standard");
                        }
                        System.out.println("Found OSGi component: " + component.getName());
                    })
                    .build()
            )
            .build();

        finder.run();
        System.out.println("Successfully found OSGi components");
    } catch (Exception e) {
        System.out.println("No OSGi components found → " + e.getMessage());
    }
}
