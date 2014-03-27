USE JAR INFILE 'target/kiji-pmml-1.0-SNAPSHOT.jar';
CREATE TABLE 'ozone' WITH DESCRIPTION 'Example table for PMML scoring with R ozone dataset'
ROW KEY FORMAT HASHED
WITH LOCALITY GROUP default WITH DESCRIPTION 'main storage' (
  FAMILY info WITH DESCRIPTION 'Information to predict ozone business' (
    predictor CLASS org.kiji.pmml.OzonePredictor WITH DESCRIPTION 'Information for scoring',
    predicted CLASS org.kiji.pmml.OzonePredicted WITH DESCRIPTION 'Predicted ozone level'
  )
);
