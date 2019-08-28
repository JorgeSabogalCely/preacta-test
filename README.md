# Simple Reports Engine

A simple reports engine that generates Daily Summary Transactions Report per Client and Product, for Processed Future Transactions.

# Version

* 1.0.1-SNAPSHOT

## Prerequisites

* JDK 1.8
* Maven 3 (for building jar artifact)

## Project Description

* **org.jsab.abnamro.txreporter.TxReportsApp** - Entry point to the Reports Engine (*ReportsEngine*) to generate Client Daily Summary Transactions Report.
* **org.jsab.abnamro.txreporter.engine.ReportsEngine** - Responsible for generating reports and converting report data into specified format. Currently only *CSV* is implemented.
* **org.jsab.abnamro.txreporter.engine.ReportOutputFormat** - Enumeration with iavailanble output data formats.
* **org.jsab.abnamro.txreporter.engine.FormattedDataDestination** - Defines output media to store formatted data from generated reports. Currently only standard system files are implemented.
* **org.jsab.abnamro.txreporter.engine.ReportFormatter** - Defines formatting operations available for report data.
* **org.jsab.abnamro.txreporter.engine.FormattableReport** - Represents reports that can be formatted using implemented data (text) formats.
* **org.jsab.abnamro.txreporter.formatters.CSVReportFormatter** - Implementation of *ReportFormatter* for CSV output format.
* **org.jsab.abnamro.txreporter.destinations.FileCharacterDestination** - Implementation of *FormattedDataDestination* for standard files (character-based).
* **org.jsab.abnamro.txreporter.transactions.ProcessedFutureTxDAO** - Responsible to control accees to transaction information, and loading data from specified input file.
* **org.jsab.abnamro.txreporter.transactions.ProcessedFutureTransacion** - POJO with information of available Processed Future Transactions (loaded from specified input file).
* **org.jsab.abnamro.txreporter.transactions.reports.ClientDailySummaryReport** - Implementation  of *FormattableReport* for Client Daily Summary Transactions Report.
* **org.jsab.abnamro.txreporter.model** - Contains POJOs to model entities used in 'processed futures transactions'.

* **pom.xml** - Maven project descriptor.

## Java Frameworks used

Project only uses standard frameworks available in JDK 8. For unit testing, Junit 4.12 is used.

## Definition of Expected Layout of Entries in Input File 

* **src/main/resources/abnamro/config/TXDataDefinitions.properties**

This property file defines estructure of each entry in the input files, and the positions of required fields.

This files also defines default directory path (./data) and default input file name (Input.txt).

## Building

Maven is used to build the project and running all included tests. TO buil, run maven from project home directory:

```
> mvn clean install
```

The generated artifact is:

* target/tx-reporter-1.0.1-SNAPSHOT.jar

## Distribution File

The file *tx-reporter-dist.zip* in the projects'root directory, contains binaries (jar), windows shell command (run-tx-reporte.cmd), sources, and sample input files.


## Execution

To run the TxReporterApp applicaton using the distribution file *tx-reporter-dist.zip*:

* Unzip de distribution file in selected directory ('project_home').

* Change to 'bin directory ín the selected directory.

```
> cd project_home_dir\bin
```

* Run the provided cmd script, specifying "input file" and "ouput directory". For example:

```
> run-tx-reporte.cmd data\Input2.txt data
```

Alternatively, execute the provided jar file:

```
> java -jar tx-reporter-1.0.1-SNAPSHOT.jar data\Input2.txt data
```

* To use default values for input file name and output directory, run the provided cmd script without parameters:

```
> run-tx-reporte.cmd
```

Alternatively, execute the provided jar file:

```
> java -jar tx-reporter-1.0.1-SNAPSHOT.jar
```
