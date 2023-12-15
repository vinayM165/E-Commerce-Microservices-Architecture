    package org.vinayM;

    import com.google.auth.oauth2.GoogleCredentials;
    import org.apache.beam.model.pipeline.v1.RunnerApi;

    import org.apache.beam.runners.dataflow.DataflowRunner;
    import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
    import org.apache.beam.sdk.Pipeline;
    import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
    import org.apache.beam.sdk.io.TextIO;
    import org.apache.beam.sdk.options.Default;
    import org.apache.beam.sdk.options.Description;
    import org.apache.beam.sdk.options.PipelineOptions;
    import org.apache.beam.sdk.options.PipelineOptionsFactory;
    import org.apache.beam.sdk.transforms.MapElements;
    import org.apache.beam.sdk.values.TypeDescriptor;
    import org.springframework.beans.factory.annotation.Value;

    import java.io.IOException;
    import java.nio.channels.Pipe;
    import java.util.Objects;

    public class Main {
        public interface MyOptions extends PipelineOptions {
            // Remove the @Default.String annotation
            @Description("Path of the file to read from")
            @Default.String("gs://mongodb-ecom-backup/backup/product.json")
            String getInputFile();
            void setInputFile(String value);

            // Keep either @Default.InstanceFactory or @Default.String
            @Default.InstanceFactory(GcpOptions.DefaultProjectFactory.class)
            String getProject();
            void setProject(String project);

            // Add projectId property without conflicting annotations
            @Default.String("projextx30794")
            String getProjectId();
            void setProjectId(String projectId);

            @Description("Path of the output file to write to")
            @Default.String("gs://mongodb-ecom-backup/backup/output.txt")
            String getOutputFile();
            void setOutputFile(String value);
        }
        public static void main(String[] args) throws IOException {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    Objects.requireNonNull(Main.class.getResourceAsStream("/key.json"))
            );
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

            // Run the pipeline
            pipeline.run();
        }
    }