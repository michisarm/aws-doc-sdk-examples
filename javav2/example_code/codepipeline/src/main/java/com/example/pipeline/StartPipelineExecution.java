//snippet-sourcedescription:[StartPipelineExecution.java demonstrates how to execute a pipeline.]
//snippet-keyword:[SDK for Java 2.0]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS CodePipeline]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[05/17/2022]
/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.example.pipeline;

// snippet-start:[pipeline.java2.start_pipeline.import]
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.codepipeline.CodePipelineClient;
import software.amazon.awssdk.services.codepipeline.model.CodePipelineException;
import software.amazon.awssdk.services.codepipeline.model.StartPipelineExecutionRequest;
import software.amazon.awssdk.services.codepipeline.model.StartPipelineExecutionResponse;
// snippet-end:[pipeline.java2.start_pipeline.import]

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class StartPipelineExecution {

    public static void main(String[] args) {
        final String usage = "\n" +
                "Usage: " +
                "   <name>\n\n" +
                "Where:\n" +
                "   name - The name of the pipeline to execute \n\n" ;

        if (args.length != 1) {
             System.out.println(usage);
             System.exit(1);
        }

        String name = args[0];
        Region region = Region.US_EAST_1;
        CodePipelineClient pipelineClient = CodePipelineClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        executePipeline(pipelineClient, name);
        pipelineClient.close();
    }

    // snippet-start:[pipeline.java2.start_pipeline.main]
    public static void executePipeline(CodePipelineClient pipelineClient, String name) {

        try {
            StartPipelineExecutionRequest pipelineExecutionRequest = StartPipelineExecutionRequest.builder()
                .name(name)
                .build();

            StartPipelineExecutionResponse response = pipelineClient.startPipelineExecution(pipelineExecutionRequest);
            System.out.println("Piepline "+response.pipelineExecutionId() +" was successfully executed");

        } catch (CodePipelineException e) {
        System.err.println(e.getMessage());
        System.exit(1);
    }
  }
    // snippet-end:[pipeline.java2.start_pipeline.main]
}
