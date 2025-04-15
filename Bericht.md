
#Structurizr Component Finder in Java am Beispiel „BetClinic“

## Was ist der Structurizr Component Finder?

Der **Component Finder** ist ein Modul der [Structurizr for Java](https://docs.structurizr.com/java/component/introduction) Bibliothek, das automatisch Softwarekomponenten in einer Codebasis erkennt und daraus ein **C4-Komponentenmodell (Level 3)** generiert. Es analysiert bestehende Klassen, Interfaces und Abhängigkeiten anhand von Annotations, Namensmustern, Vererbung oder anderen Heuristiken.

---

## Beispiel: BetClinic

In einem Java-Projekt wie **BetClinic** kann man den Component Finder verwenden, um automatisch die wichtigsten Komponenten im Anwendungscode zu identifizieren.

### Wichtige Codeausschnitte

```java
ComponentFinder componentFinder = new ComponentFinderBuilder()
    .forContainer(webApp)
    .fromClasses(new File(".../spring-petclinic"))
    .withStrategy(
        new ComponentFinderStrategyBuilder()
            // just one matchby example just demonstration
            .matchedBy(new RegexTypeMatcher("^.*Controller$"))
            .matchedBy(new ExtendsTypeMatcher("org.springframework.web.servlet.mvc.Controller"))
            .matchedBy(new AnnotationTypeMatcher("org.springframework.stereotype.Controller"))
            .withTechnology("Spring MVC Controller")
            .forEach(component -> {
                user.uses(component, "Uses");
                component.addTags(component.getTechnology());
            })
            .build()
    )
    .withStrategy(
        new ComponentFinderStrategyBuilder()
            .matchedBy(new NameSuffixTypeMatcher("Repository"))
            .withTechnology("Spring Data Repository")
            .forEach(component -> {
                component.addTags(component.getTechnology());
            })
            .build()
    )
    .build();
componentFinder.run();
```

Diese Kombination erkennt Controller-Klassen, Repositorys sowie Spring-Komponenten über Annotationen, Namensmuster und Vererbung.

---

## Unterstützte Strategien

Der Component Finder unterstützt folgende Matching-Strategien:

| Strategie | Beschreibung |
|----------|--------------|
| **AnnotationTypeMatcher** | Findet Klassen mit bestimmter Annotation (z. B. `@Controller`). |
| **NameSuffixTypeMatcher** | Erkennt Klassen anhand ihres Namenssuffixes. |
| **RegexTypeMatcher** | Nutzt reguläre Ausdrücke, um Klassennamen zu matchen. |
| **ExtendsTypeMatcher** | Findet Klassen, die von einer bestimmten Superklasse erben. |
| **SourceCodeComponentFinderStrategy** | Analysiert Quellcode-Dateien direkt. |

Diese Strategien lassen sich kombinieren, um eine präzise Komponenten-Erkennung zu ermöglichen.

---

## Vorteile

- **Automatisierung** spart Zeit und Aufwand.
- **Flexible Kombination** der Strategien für genaues Matching.
- **Aktuelle Architektur-Dokumentation** direkt aus dem Code.
- **CI/CD-freundlich** und buildbar.
- **Granulare Visualisierung** der Komponentenebene (Level 3).

---

## Nachteile

- **Nur für Java** geeignet.
- **Erfordert strukturierte Codebasis** (Namenskonventionen, Annotationen etc.).
- **Begrenzte Strategieauswahl**, da noch in Entwicklung.
- **Skalierbarkeit**: Bei großen Projekten kann Modell unübersichtlich werden.
- **Mögliche Fehlklassifikationen** bei ungenauer Konfiguration.

---

## Fazit

Der **Structurizr Component Finder** ist ein nützliches Tool zur teilautomatisierten Erstellung von C4-Komponentenmodellen aus Java-Code. Dank vielfältiger Strategien wie Annotation-, Regex- und Vererbungs-Matching lassen sich relevante Klassen effizient identifizieren. Für große und komplexe Projekte ist dennoch eine manuelle Ergänzung empfehlenswert.
