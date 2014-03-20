USE JAR INFILE 'target/kiji-pmml-1.0-SNAPSHOT.jar';
CREATE TABLE 'iris' WITH DESCRIPTION 'Example table for PMML scoring with R iris dataset'
ROW KEY FORMAT HASHED
WITH LOCALITY GROUP default WITH DESCRIPTION 'main storage' (
  FAMILY info WITH DESCRIPTION 'information about a given flower' (
    name "string" WITH DESCRIPTION 'name of flower',
    flower_data CLASS org.kiji.pmml.IrisData WITH DESCRIPTION 'Information for scoring',
    predicted_sepal_length CLASS org.kiji.pmml.IrisResult WITH DESCRIPTION 'predicted sepal length'
  )
);
