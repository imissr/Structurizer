
#Structurizr Component Finder in Java am Beispiel „BetClinic“

## Was ist der Structurizr Component Finder?

Der **Component Finder** ist ein Modul der [Structurizr for Java](https://docs.structurizr.com/java/component/introduction) Bibliothek, das automatisch Softwarekomponenten in einer Codebasis erkennt und daraus ein **C4-Komponentenmodell (Level 3)** generiert. Es analysiert bestehende Klassen, Interfaces und Abhängigkeiten anhand von Annotations, Namensmustern, Vererbung oder anderen Heuristiken.

---

## Beispiel: BetClinic

In einem Java-Projekt wie **BetClinic** kann man den Component Finder verwenden, um automatisch die wichtigsten Komponenten im Anwendungscode zu identifizieren.

### Wichtige Codeausschnitte(com.structurizr.component)

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

## Component finder strategies (structurizr.analysis.*)

>  **Kommentar:** Dieser Abschnitt gibt einen hilfreichen Überblick über die verfügbaren Component Finder Strategien in den Structurizr Java-Erweiterungen. Es wird klar zwischen Strategien unterschieden, die auf statischer Analyse des kompilierten Bytecodes basieren, und solchen, die den Quellcode analysieren. Besonders nützlich ist der Hinweis, dass man dank der Open-Source-Struktur auch eigene Strategien implementieren kann.

> **Kommentar:** Structurizr zwei verschiedene Pakete zur Komponentenerkennung bereitstellt: `com.structurizr.analysis.*` und `com.structurizr.component.*`. Letzteres wurde mit Version 4.0.0 eingeführt und stellt eine modernisierte und flexiblere Alternative dar. Obwohl in der Dokumentation hauptsächlich `com.structurizr.component.*` verwendet wird, kann auch das ältere `analysis`-Paket weiterhin für die Erstellung von C4-Modellen eingesetzt werden.
[source](https://github.com/structurizr/java/tree/master/structurizr-component)




| Name | Dependency | Description | Extracted from |
|------|------------|-------------|----------------|
| TypeMatcherComponentFinderStrategy | structurizr-core | A component finder strategy that uses type information to find components, based upon a number of pluggable TypeMatcher implementations (e.g. **NameSuffixTypeMatcher**, **ImplementsInterfaceTypeMatcher**, **RegexTypeMatcher** and **AnnotationTypeMatcher**). | Compiled bytecode |
| SpringComponentFinderStrategy | structurizr-spring | Finds types annotated `@Controller`, `@RestController`, `@Component`, `@Service` and `@Repository`, plus classes that extend `JpaRepository`. | Compiled bytecode |
| StructurizrAnnotationsComponentFinderStrategy | structurizr-core | Finds the Structurizr annotations `@Component`, `@UsedByPerson`, `@UsedBySoftwareSystem`, `@UsedByContainer`, `@UsesSoftwareSystem`, `@UsesContainer` and `@UsesComponent`. | Compiled bytecode |
| SourceCodeComponentFinderStrategy | structurizr-core | This component finder strategy doesn't really find components, it instead extracts the top-level Javadoc comment from the code so that this can be added to existing component definitions. It also calculates the size of components, based upon the number of lines of source code. | Source code |

Diese Strategien lassen sich kombinieren, um eine präzise Komponenten-Erkennung zu ermöglichen.

[source](https://github.com/structurizr/java-extensions/blob/master/docs/component-finder.md)

---

## Vorteile

- **Automatisierung** spart Zeit und Aufwand.
- **Flexible Kombination** der Strategien für genaues Matching.
- **Aktuelle Architektur-Dokumentation** direkt aus dem Code.
- **CI/CD-freundlich** und buildbar.
- **Granulare Visualisierung** der Komponentenebene (Level 3).

---

## Nachteile

- **Nur für Java/C#** geeignet.
- **Erfordert strukturierte Codebasis** (Namenskonventionen, Annotationen etc.).
- **Begrenzte Strategieauswahl**, da noch in Entwicklung.
- **Skalierbarkeit**: Bei großen Projekten kann Modell unübersichtlich werden.
- **Mögliche Fehlklassifikationen** bei ungenauer Konfiguration.

---
## Erfahrung mit dem Structurizr Component Finder (`com.structurizr.component.*`)

Ich habe den Structurizr Component Finder in zwei Projekten getestet:  
einmal im bekannten **SpringPetClinic-Projekt** und einmal in einem noch nicht vollständig entwickelten Projekt – einem **Webchat-Server** auf Basis von Spring Boot.

### PetClinic:

![Alt text](1.png)

### Chatserver:

![Alt text](2.png)

---

###  Meine Erfahrung

Structurizr ist ein **sehr hilfreiches Tool**, um ein C4-Modell automatisch zu generieren, Komponenten zu erkennen und **Beziehungen zwischen Komponenten** (z. B. Controller ↔ Repository) zu identifizieren.  
Allerdings erfordert es **manuelle Vorarbeit**, um die besten Regeln zur Komponentenerkennung zu definieren.

---

### Empfohlene Vorgehensweise

Bevor ein automatisiertes C4-Modell erstellt wird, sollte man folgende Schritte durchführen:

1. **Komponenten identifizieren**  
   - Die Codebasis analysieren und verstehen  
   - Eine erste Vorstellung vom Endmodell gewinnen (z. B. mithilfe eines UML-Diagramms)

2. **Komponenten kategorisieren**  
   - Ähnliche Komponenten gruppieren, z. B.:
     - `Web-Controller`
     - `Daten-Repositories`
     - `Services`

3. **Regeln zur Komponentenerkennung definieren**  
   - Für jede Kategorie passende Regeln festlegen:
     - z. B. Klassen mit `@Controller` → Web-Komponente
     - Klassen mit Suffix `Repository` → Datenzugriff
     - Oder per Annotation, Interface, Paketstruktur etc.

---

### Wichtiger Hinweis

Jede Codebasis ist **individuell**, daher ist es **nicht möglich**, ein vollständiges C4-Modell für alle Klassen vollautomatisch zu generieren.  
Structurizr kann viele Beziehungen erkennen – **aber nicht alle**.  

Das sieht man auch in meiner Modellierung des **Chatserver-Projekts**:  
Die Beziehung zwischen den `Repository`- und `Service`-Klassen wurde **nicht automatisch erkannt**.

**Das Petclinic-Beispiel wurde gut modelliert, da es sich um ein einfaches Projekt handelt**


**Manuelle Ergänzungen durch Entwickler sind notwendig**, besonders bei komplexen oder indirekten Abhängigkeiten.


---

### Was erkennt der `ComponentFinder`?

Der `ComponentFinder` erkennt **keine expliziten Beziehungen** (wie `uses(...)`) zwischen Komponenten.  
Stattdessen erkennt er **Verbindungen zwischen Klassen**, also welche Klassen andere verwenden – z. B. wenn ein `Controller` ein `Repository` über den Konstruktor oder ein Feld nutzt.

Diese Verbindungen werden intern als **referenzierte Typen (`referencedTypes`)** gespeichert.  
Wenn sowohl die Quell- als auch die Zielklasse als **Komponenten erkannt** wurden, kann man daraus **manuell `uses(...)`-Beziehungen** erzeugen.

Das bedeutet: Wenn der `ComponentFinder` eine Klasse als Komponente erkennt (z. B. einen Controller), analysiert er zusätzlich, **welche anderen Klassen von dieser Komponente verwendet werden**.




## Fazit

Der **Structurizr Component Finder** ist ein nützliches Tool zur teilautomatisierten Erstellung von C4-Komponentenmodellen aus Java-Code. Dank vielfältiger Strategien wie Annotation-, Regex- und Vererbungs-Matching lassen sich relevante Klassen effizient identifizieren. Für große und komplexe Projekte ist dennoch eine manuelle Ergänzung empfehlenswert.
