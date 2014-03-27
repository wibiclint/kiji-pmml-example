/**
 * (c) Copyright 2013 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.pmml.bulkimport;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.kiji.mapreduce.KijiTableContext;
import org.kiji.mapreduce.bulkimport.KijiBulkImporter;
//import org.kiji.pmml.IrisData;
import org.kiji.schema.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bulk importer for R dataset for irises.
 *
 * Assumes that the input file does not have a header, and that the order of the fields is:
 * Id, sepal length, sepal width, petal length, petal width, species.
 *
 */
/*
public class IrisBulkImporter extends KijiBulkImporter<LongWritable, Text> {
  private static final Logger LOG =
      LoggerFactory.getLogger(KijiBulkImporter.class);

  final static int FIELD_ID = 0;
  final static int FIELD_SEPAL_LENGTH = 1;
  final static int FIELD_SEPAL_WIDTH = 2;
  final static int FIELD_PETAL_LENGTH = 3;
  final static int FIELD_PETAL_WIDTH = 4;
  final static int FIELD_SPECIES = 5;

  @Override
  public void setup(KijiTableContext context) throws IOException {
    // TODO: Perform any setup you need here.
    super.setup(context);
  }

  @Override
  public void produce(LongWritable filePos, Text value, KijiTableContext context)
      throws IOException {
    final String[] split = value.toString().split(",");
    if (split.length != 6) {
      LOG.error("Problem with row: " + value.toString());
      throw new IOException("Unable to parse header row: " + value.toString());
    }

    // Remove quotes
    long flowerId = Long.parseLong(split[FIELD_ID].substring(1, split[FIELD_ID].length() - 1));

    double sepalLength = Double.parseDouble(split[FIELD_SEPAL_LENGTH]);
    double sepalWidth = Double.parseDouble(split[FIELD_SEPAL_WIDTH]);
    double petalLength = Double.parseDouble(split[FIELD_PETAL_LENGTH]);
    double petalWidth = Double.parseDouble(split[FIELD_PETAL_WIDTH]);

    // Remove quotes
    String species = split[FIELD_SPECIES].substring(1, split[FIELD_SPECIES].length() - 1);

    final EntityId eid = context.getEntityId(Long.toString(flowerId));

    // Create Avro record with flower information.
    IrisData irisData = IrisData.newBuilder()
        .setId(flowerId)
        .setSepalLength(sepalLength)
        .setSepalWidth(sepalWidth)
        .setPetalLength(petalLength)
        .setPetalWidth(petalWidth)
        .setSpecies(species)
        .build();

    context.put(eid, "info", "flower_data", irisData);
  }

  @Override
  public void cleanup(KijiTableContext context) throws IOException {
    // TODO: Perform any cleanup you need here.
    super.cleanup(context);
  }
}
*/
