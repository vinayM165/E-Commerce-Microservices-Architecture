package org.vinayM;

import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

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