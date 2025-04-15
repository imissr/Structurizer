package com.example.springchatserver.c4;


import com.structurizr.Workspace;
import com.structurizr.component.ComponentFinder;
import com.structurizr.component.ComponentFinderBuilder;
import com.structurizr.component.ComponentFinderStrategyBuilder;
import com.structurizr.component.matcher.AnnotationTypeMatcher;
import com.structurizr.component.matcher.NameSuffixTypeMatcher;
import com.structurizr.io.json.JsonWriter;
import com.structurizr.model.*;
import com.structurizr.view.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;


public class GenerateC4ModelCompFinder {
    public static void main(String[] args) throws Exception {



        // Create workspace
        Workspace workspace = new Workspace("C4 Model", "Spring Boot + MySQL");
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        // Define user
        Person user = model.addPerson("User", "Uses the application");



        // Define software system and container
        SoftwareSystem system = model.addSoftwareSystem("Spring Boot App", "Generated C4 model");
        Container webApp = system.addContainer("Web Application", "Spring Boot App", "Java");
        user.uses(webApp, "Uses");



        ComponentFinder componentFinder = new ComponentFinderBuilder()
                .forContainer(webApp)
                .fromClasses(new File("C:\\Users\\khale\\IdeaProjects\\SpringChatServer"))
               /* .withStrategy(
                        new ComponentFinderStrategyBuilder()
                                .matchedBy(new AnnotationTypeMatcher("org.springframework.stereotype.Controller"))
                                .withTechnology("Spring MVC Controller")
                                .forEach(component -> {
                                    user.uses(component, "Uses");
                                    component.addTags(component.getTechnology());
                                })
                                .build()
                )*/

                .withStrategy(
                        new ComponentFinderStrategyBuilder()
                                .matchedBy(new NameSuffixTypeMatcher("Repository"))
                                .withTechnology("Spring Data Repository")
                                .forEach(component -> {
                                    component.addTags(component.getTechnology());
                                })
                                .build()
                )

                .withStrategy(
                        new ComponentFinderStrategyBuilder()
                                .matchedBy(new NameSuffixTypeMatcher("Service"))
                                .withTechnology("Spring Service")
                                .forEach(component -> {
                                    component.addTags(component.getTechnology());
                                })
                                .build()
                )

                .withStrategy(new ComponentFinderStrategyBuilder()
                        .matchedBy(new NameSuffixTypeMatcher("Controller"))
                        .withTechnology("Spring MVC Controller")
                        .forEach(component -> {
                            user.uses(component, "Uses");
                            component.addTags(component.getTechnology());
                        })
                        .build()
                )


                .build();
        componentFinder.run();





        //analysis from Structurzier
    /*  String sourceRoot = "/home/mohamad-khaled-minawe/IdeaProjects/demo";
      SourceCodeComponentFinderStrategy sourceCodeComponentFinderStrategy = new
                SourceCodeComponentFinderStrategy(new File(sourceRoot, "/src/main/java/"),150);

        ComponentFinder componentFinder = new ComponentFinder(
                webApp,
                "com.example.demo", // Change this to your actual root package
                sourceCodeComponentFinderStrategy);
        componentFinder.findComponents();*/


        // Create views
        ContainerView containerView = views.createContainerView(system, "containers", "Container view");
        containerView.addAllElements();

        ComponentView componentView = views.createComponentView(webApp, "components", "Component view");
        componentView.addAllComponents();
        componentView.add(user);




        // Style
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle("Component").background("#85bb65").color("#ffffff").shape(Shape.Hexagon);
        styles.addElementStyle("Database").background("#f5da55").color("#000000").shape(Shape.Cylinder);

        // Export the model to JSON
        try (Writer writer = new FileWriter("workspace.json")) {
            new JsonWriter(true).write(workspace, writer);
            System.out.println(" C4 model exported to workspace.json");
        }

        System.out.println("Components found: " + webApp.getComponents().stream().map(Component::getName).toList());
    }
}
