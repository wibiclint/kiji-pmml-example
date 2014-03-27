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
`foo.iris_model`.



### Set up the demo project

Build this project:

    mvn clean package

Set up your classpath.  Add the JARs that are necessary for the new version of the model repo that
are not present in the dependencies for the Bento Box.  Also add the projec that we just built, as
well as the modeling and model repo JARs:

    export KIJI_CLASSPATH=/Users/clint/.m2/repository/org/jpmml/pmml-evaluator/1.0.22/pmml-evaluator-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-manager/1.0.22/pmml-manager-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-model/1.0.22/pmml-model-1.0.22.jar:/Users/clint/.m2/repository/org/jpmml/pmml-schema/1.0.22/pmml-schema-1.0.22.jar:/Users/clint/.m2/repository/org/apache/maven/shared/maven-invoker/2.1.1/maven-invoker-2.1.1.jar:/Users/clint/.m2/repository/org/codehaus/plexus/plexus-utils/3.0.8/plexus-utils-3.0.8.jar:/Users/clint/.m2/repository/org/codehaus/plexus/plexus-component-annotations/1.5.5/plexus-component-annotations-1.5.5.jar:/Users/clint/.m2/repository/joda-time/joda-time/2.2/joda-time-2.2.jar
    export KIJI_CLASSPATH=${KIJI_CLASSPATH}:target/kiji-pmml-1.0-SNAPSHOT.jar
    export KIJI_CLASSPATH=${KIJI_CLASSPATH}:~/work/kiji/kiji-modeling/kiji-modeling/target/kiji-modeling-0.8.0-SNAPSHOT.jar:~/work/kiji/kiji-model-repository/kiji-model-repository/target/kiji-model-repository-0.7.0-SNAPSHOT.jar

Install a Kiji instance:

    export KIJI=kiji://.env/default
    kiji install --kiji=${KIJI}

Set up the model repo

    mkdir my_model_repo;
    kiji model-repo init file:///Users/clint/play/kiji/kiji-pmml/my_model_repo --kiji=$KIJI

Fix the bug on line 24 of the bento box scoring server shell script.  Run the scoring server:

    <bentodir>/scoring-server/bin/kiji-scoring-server

### Run the demo project

Create the table:

    kiji-schema-shell --kiji=$KIJI --file=src/main/layout/table_desc.ddl

Run the PMML command-line wizard!

    kiji model-repo pmml --table=$KIJI/iris --model-file=file:///Users/clint/play/kiji/kiji-pmml/iris.xml --model-name=foo.iris_model --model-version=0.0.1 --predictor-column=info:flower_data --result-column=info:sepal_length --result-record-name=IrisResult --model-container=iris.json

Deploy the model:

    kiji model-repo deploy foo.iris_model empty-jar.jar --kiji=${KIJI} --deps-resolver=maven --production-ready=true --model-container=iris.json --message="Initial deployment of R iris model."

Attach the score function to the appropriate column (specified in the json file):

    kiji model-repo fresh-model $KIJI foo.iris_model-0.0.1 org.kiji.scoring.lib.AlwaysFreshen


Copy the data to HDFS:

    hadoop fs -copyFromLocal iris.csv

Import the data

    kiji bulk-import --importer=org.kiji.pmml.bulkimport.IrisBulkImporter --lib=target --output="format=kiji table=${KIJI}/iris nsplits=1" --input="format=text file=iris.csv"

(This is not really necessary if you just want to try scoring out.)
