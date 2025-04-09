workspace {
    !identifiers hierarchical

    model {
        user = person "Client User" {
            description "A user of the system"
        }

        softwareSystem1 = softwareSystem "My Software System" {
            description "An example system to demonstrate C4 model using Structurizr"

            webapp = container "Web Application" {
                description "Allows users to interact with the system"
                technology "Java + Spring Boot"

                component1 = component "Login Controller" {
                    description "Handles login requests"
                    technology "Spring MVC"
                    tags "Controller"
                }
                component5 = component "Registration Controller" {
                    description "handles registration requests"
                    technology  "Spring MVC"
                }
                component6 = component "Registration Service" {
                    description "Handles user registration"
                    technology "Java Service"
                }

                component2 = component "User Login Service" {
                    description "Handles user authentication and management"
                    technology "Java Service"
                }

                component3 = component "User Repository" {
                    description "Handles user data storage and retrieval"
                    technology "Spring Data JPA"
                }

                component4 = component "database" {
                    description "Stores user data"
                    technology "PostgreSQL"
                }

                component1 -> component2 "Validates user credentials"
                component2 -> component3 "Fetches user data"
                component3 -> component4 "Reads data from"
                component1 -> component5 "Registers new users"
                component5 -> component6 "Handles registration logic"
                component6 -> component4 "Stores user data"

                
            }

        }

        // Relationships
        user -> softwareSystem1 "Uses"
        user -> softwareSystem1.webapp "Interacts with"
        user -> softwareSystem1.webapp.component1 "Logs in via"

    }



    views {
        systemContext softwareSystem1 {
            include *
            autolayout lr
        }

        container softwareSystem1 {
            include *
            autolayout lr
        }

        component softwareSystem1.webapp {
            include *
            autolayout lr
        }
        

            styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }

            element "Software System" {
                shape roundedbox
                background #1168bd
                color #ffffff
            }

            element "Container" {
                shape box
                background #438dd5
                color #ffffff
            }

            element "Component" {
                shape hexagon
                background #85bb65
                color #ffffff
            }

            element "Database" {
                shape cylinder
                background #f5da55
                color #000000
            }
        }

        theme default
    }
}