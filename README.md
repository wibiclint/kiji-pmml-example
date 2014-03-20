How to run everything!

### Download latest Bento and link the modeling stuff:

Download and build kiji-modeling, kiji-model-repository, and kiji-scoring.  Sym link all of the
Bento Box JARs to these locally-built JARs, or download these build
[scripts](https://github.com/wibiclint/build-scripts) and run the Bento Box linker from a root
directory containing your Bento Box JAR file and the checkouts for the appropriate Kiji projects:

    $ /path/to/build-scripts/bento_reboot.py -b 1.4.3 -l model-repository,scoring -v


### Start the Bento

Source `kij-env.sh` and start up your Bento Box!!!!!!!!!


### Dump data from R

    > write.csv(iris, "iris.csv")

Manually remove the header from the CSV file.

Dump the PMML XML.  *TODO*: Remove manual hacks to XML file to rename fields to match field names in
Avro (R names have `.` in them, cannot do that in Avro)  Also rename the `modelName` to
`org.kiji.pmml.linear_regression`.



### Set up the demo project

Build this project:

    mvn clean package

Set up your classpath:

    export KIJI_CLASSPATH=/Users/clint/.m2/repository/org/jpmml/pmml-evaluator/1.0.22/pmml-evaluator-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-manager/1.0.22/pmml-manager-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-model/1.0.22/pmml-model-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-schema/1.0.22/pmml-schema-1.0.22.jar:/Users/clint/.m2/repository/org/apache/maven/shared/maven-invoker/2.1.1/maven-invoker-2.1.1.jar:/Users/clint/.m2/repository/org/codehaus/plexus/plexus-utils/3.0.8/plexus-utils-3.0.8.jar:/Users/clint/.m2/repository/org/codehaus/plexus/plexus-component-annotations/1.5.5/plexus-component-annotations-1.5.5.jar
    export KIJI_CLASSPATH=${KIJI_CLASSPATH}:target/kiji-pmml-1.0-SNAPSHOT.jar
    export KIJI_CLASSPATH=${KIJI_CLASSPATH}:~/work/kiji/kiji-modeling/kiji-modeling/target/kiji-modeling-0.8.0-SNAPSHOT.jar:~/work/kiji/kiji-model-repository/kiji-model-repository/target/kiji-model-repository-0.7.0-SNAPSHOT.jar

Install a Kiji instance:

    export KIJI=kiji://.env/iris
    kiji install $KIJI

Set up the model repo

    mkdir my_model_repo; kiji model-repo init my_model_repo --kiji=${KIJI}

### Run the demo project

Create the table:

    kiji-schema-shell --kiji=$KIJI --file=src/main/layout/table_desc.ddl

Copy the data to HDFS:

    hadoop fs -copyFromLocal iris.csv

Import the data

    kiji bulk-import --importer=org.kiji.pmml.bulkimport.IrisBulkImporter --lib=target --output="format=kiji table=${KIJI}/iris nsplits=1" --input="format=text file=iris.csv"

(This is not really necessary if you just want to try scoring out.)

Run the PMML command-line wizard!

    kiji model-repo pmml --table=$KIJI/iris --model-file=file:///Users/clint/play/kiji/kiji-pmml/iris.xml --model-name=org.kiji.pmml.linear_regression --model-version=0.0.1 --predictor-column=info:flower_data --result-column=info:predicted_sepal_length --result-record-name=IrisResult --model-container=iris.json


Deploy the model:
