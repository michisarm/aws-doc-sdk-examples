package com.example.kinesis;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.util.concurrent.CompletableFuture;

public class KinesisStreamExOld {

    // private static final String CONSUMER_ARN = "arn:aws:kinesis:ap-northeast-2:852964532494:stream/ingest-dev-iot-capstec";
    private static final String CONSUMER_ARN = "arn:aws:kinesis:ap-northeast-2:852964532494:stream/ingest-prod-iot-senko-air-paju/consumer/KinesisIotSenkoPajuConsumerApplication:1651629472";
    // private static final String CONSUMER_ARN =  "arn:aws:kinesis:ap-northeast-2:852964532494:stream/ingest-dev-iot-capstec/consumer/KinesisConsumerApplication:1651591059";
    private static final String SHARD_ID = "shardId-000000000000";
    // private static final String SHARD_ID = "shardId-000000000001";

    public static void main(String[] args) {

        KinesisAsyncClient client = KinesisAsyncClient.create();

        SubscribeToShardRequest request = SubscribeToShardRequest.builder()
                .consumerARN(CONSUMER_ARN)
                .shardId(SHARD_ID)
                .startingPosition(s -> s.type(ShardIteratorType.LATEST)).build();

        // Call SubscribeToShard iteratively to renew the subscription periodically.
        while(true) {
            // Wait for the CompletableFuture to complete normally or exceptionally.
            callSubscribeToShardWithVisitor(client, request).join();
        }

        // Close the connection before exiting.
        // client.close();
    }


    /**
     * Subscribes to the stream of events by implementing the SubscribeToShardResponseHandler.Visitor interface.
     */
    private static CompletableFuture<Void> callSubscribeToShardWithVisitor(KinesisAsyncClient client, SubscribeToShardRequest request) {
        SubscribeToShardResponseHandler.Visitor visitor = new SubscribeToShardResponseHandler.Visitor() {
            @Override
            public void visit(SubscribeToShardEvent event) {
                System.out.println("Received subscribe to shard event " + event);
                if(!event.records().isEmpty()){
                    for (Record record:event.records()) {
                        System.out.println("data : "+ record.data().asUtf8String());
                    }
                }
            }
        };
        SubscribeToShardResponseHandler responseHandler = SubscribeToShardResponseHandler
                .builder()
                .onError(t -> System.err.println("Error during stream - " + t.getMessage()))
                .subscriber(visitor)
                .build();
        return client.subscribeToShard(request, responseHandler);
    }
}