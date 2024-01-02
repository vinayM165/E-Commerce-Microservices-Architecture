package org.vinayM;

import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;

import javax.swing.text.Document;

public class MigrateBatch {
        public static void main(String[] args) {
            MyOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);
            options.setRunner(DataflowRunner.class);
            options.setProject("projextx30794");
            options.setProjectId("projextx30794");
            Pipeline pipeline = Pipeline.create(options);
            pipeline
                    .apply("Reading data from MongoDB",MongoDbIO.read()
                            .withUri("mongodb://root:root@34.93.227.178:27017")
                            .withDatabase("productdb")
                            .withCollection("Product"))
                    .apply("Transform Data",
                            MapElements.into(TypeDescriptor.of(String.class))
                                    .via((org.bson.Document doc) -> {
                                        assert doc != null;
                                        doc.put("mongodb_id", doc.remove("_id"));
                                        return doc.toJson();
                    }))
                    .apply(ElasticsearchIO.write().withConnectionConfiguration(
                                    ElasticsearchIO.ConnectionConfiguration.create(new String[]{"http://34.124.161.75:9200"}, "product")
                            )
                    );
                    pipeline
                            .apply("Reading data from MongoDB",MongoDbIO.read()
                                    .withUri("mongodb://root:root@34.93.227.178:27017")
                                    .withDatabase("productdb")
                                    .withCollection("Product"))
                            .apply("Transform Data",
                                    MapElements.into(TypeDescriptor.of(String.class))
                                            .via((org.bson.Document doc) -> doc.toJson()))
                            .apply("Writing data to file",
                        TextIO.write().to("gs://mongodb-ecom-backup/backup/dbdata.txt")
                    );
            pipeline.run();
        }
}
