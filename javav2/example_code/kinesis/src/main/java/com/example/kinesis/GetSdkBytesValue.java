package com.example.kinesis;

import software.amazon.awssdk.core.SdkBytes;

import java.nio.charset.StandardCharsets;

import static software.amazon.awssdk.core.SdkBytes.fromString;

public class GetSdkBytesValue {
    public static void main(String[] args) {
        String data = "0x7b226964223a2231373638222c2276616c75655f30223a223135222c2276616c75655f31223a223432222c2276616c75655f36223a2232332e38222c2276616c75655f37223a2231372e31222c227369705f7468696e676e616d65223a227369705f6e6f616c65645f6e6f616c65645f31373638222c227369705f636c69656e746964223a227369702d696e6765737467772d636c69656e742d36333632373735342d313964632d346338642d396665352d323337666233343530336564227d";
        String data1 = "{\"a\":1}";
        SdkBytes a = fromString(data1, StandardCharsets.UTF_8);
        System.out.println(a);
        System.out.println(a.asUtf8String());
    }
}
