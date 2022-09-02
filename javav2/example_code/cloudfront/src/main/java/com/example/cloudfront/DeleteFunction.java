//snippet-sourcedescription:[DeleteFunction.java demonstrates how to delete a CloudFront function.]
//snippet-keyword:[AWS SDK for Java v2]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon CloudFront]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[05/17/2021]
/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.example.cloudfront;

// snippet-start:[cloudfront.java2.del_function.import]
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.CloudFrontException;
import software.amazon.awssdk.services.cloudfront.model.DeleteFunctionRequest;
// snippet-end:[cloudfront.java2.del_function.import]

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class DeleteFunction {

    public static void main(String[] args) {

        final String usage = "\n" +
                "Usage:\n" +
                "    <functionName> <ifMatchVal>\n\n" +
                "Where:\n" +
                "    functionName - The name of the function to delete. \n"+
                "    ifMatchVal - The current version (ETag value) of the function that you are deleting, which you can get using DescribeFunction. \n" ;

        if (args.length != 2) {
             System.out.println(usage);
             System.exit(1);
        }

        String functionName = args[0];
        String ifMatchVal = args[1];
        CloudFrontClient cloudFrontClient = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        deleteSpecificFunction(cloudFrontClient, functionName, ifMatchVal);
        cloudFrontClient.close();
    }

    // snippet-start:[cloudfront.java2.del_function.main]
    public static void deleteSpecificFunction( CloudFrontClient cloudFrontClient, String functionName,String ifMatchVal){

        try {
            DeleteFunctionRequest functionRequest = DeleteFunctionRequest.builder()
                .name(functionName)
                .ifMatch(ifMatchVal)
                .build();

            cloudFrontClient.deleteFunction(functionRequest);
            System.out.println(functionName +" was successfully deleted.");

        } catch (CloudFrontException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    // snippet-end:[cloudfront.java2.del_function.main]
}