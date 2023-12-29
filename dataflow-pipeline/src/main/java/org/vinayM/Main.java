    package org.vinayM;

    import com.google.auth.oauth2.GoogleCredentials;
    import org.apache.beam.runners.dataflow.DataflowRunner;
    import org.apache.beam.sdk.Pipeline;
    import org.apache.beam.sdk.io.TextIO;
    import org.apache.beam.sdk.options.PipelineOptionsFactory;
    import org.apache.beam.sdk.transforms.MapElements;
    import org.apache.beam.sdk.values.TypeDescriptor;
    import java.io.IOException;
    import java.util.Objects;


    public class Main {
        public static void main(String[] args) throws IOException {
            MyOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);
            options.setRunner(DataflowRunner.class);
            options.setProject("projextx30794");
            options.setProjectId("projextx30794");
            options.setTempLocation("gs://mongodb-ecom-backup/backup/");
            Pipeline pipeline = Pipeline.create(options);
            pipeline
                    .apply("Read Mongo backup file", TextIO.read().from(options.getInputFile()))
                    .apply("Transform Data", MapElements
                            .into(TypeDescriptor.of(String.class))
                            .via((String line) -> line.toUpperCase()))
                    .apply("Write Output", TextIO.write().to(options.getOutputFile()));
            pipeline.run();
        }
    }